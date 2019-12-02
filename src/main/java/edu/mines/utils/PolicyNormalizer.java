package edu.mines.utils;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Statement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import edu.mines.dao.MergedServiceManager;
import edu.mines.model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matt on 10/8/2015.
 */
public class PolicyNormalizer {
    private NavigableMap<String, ExistingRole> existingRoleMap = new TreeMap<>();
    private NavigableMap<String, ExistingUser> existingUserMap = new TreeMap<>();
    private MergedServiceManager serviceManager = MergedServiceManager.getInstance();

    private static MyPolicyReader reader = new MyPolicyReader();
    private Map<String, Set<String>> missingServices = new ConcurrentHashMap<>();
    private MongoClient mongo = DatastoreFactory.getMongoClient();
    private Morphia morphia = new Morphia();
    private Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());

    public PolicyNormalizer() {
        this(true);
    }

    public PolicyNormalizer(boolean loadRoles) {
        if (loadRoles) {
            Query<ExistingRole> query = ds.createQuery(ExistingRole.class);
            List<ExistingRole> existingRolesList = query.asList();
            existingRolesList.forEach(existingRole -> existingRoleMap.put(existingRole.getName(), existingRole));

            Query<ExistingUser> userQuery = ds.createQuery(ExistingUser.class);
            List<ExistingUser> existingUserList = userQuery.asList();
            existingUserList.forEach(existingUser -> existingUserMap.put(existingUser.getName(), existingUser));
        }
    }

    public PolicyNormalizer(Map<String, ExistingUser> existingUserMap, Map<String, ExistingRole> existingRoleMap) {
        this.existingUserMap.putAll(existingUserMap);
        this.existingRoleMap.putAll(existingRoleMap);
    }

    public static void main(String... args) throws JsonProcessingException {
        PolicyNormalizer policyNormalizer = new PolicyNormalizer();
        policyNormalizer.process();
    }

    public void process() throws JsonProcessingException {
        NavigableMap<String, ? extends ExistingEntity> users = processExistingEntities(existingUserMap);
        users.values().parallelStream().forEach(o -> ds.save(o));
        NavigableMap<String, ? extends ExistingEntity> roles = processExistingEntities(existingRoleMap);
        roles.values().parallelStream().forEach(o -> ds.save(o));
    }

    public NavigableMap<String, ? extends ExistingEntity> processExistingEntities(NavigableMap<String, ? extends ExistingEntity> existingEntities) {
        for (Map.Entry<String, ? extends ExistingEntity> existingEntity : existingEntities.entrySet()) {
            deduplicateRawPolicies(existingEntity.getValue());
            List<String> normalizedPolicies = normalizePolicies(existingEntity.getValue());
            existingEntity.getValue().setNormalizedPolicies(normalizedPolicies);
        }
        return existingEntities;
    }

    /**
     * Simple ahshcode based check to eliminate policies that are EXACT duplicates.
     *
     * @param existingEntity
     */
    private void deduplicateRawPolicies(ExistingEntity existingEntity) {
        Set<String> policies = new HashSet<>();
        existingEntity.getRawPolicies().forEach(s -> policies.add(s));
        existingEntity.setRawPolicies(new ArrayList<>(policies));
    }

    /**
     * explodes wildcards for services and actions in statements so they contain all allowed acitons represented
     * by the wildcards
     */
    private List<String> normalizePolicies(ExistingEntity existingEntity) {
        List<String> returnPolicies = new ArrayList<>();
        for (String policyString : existingEntity.getRawPolicies()) {
            //for each role, explode the * using the service map and place the results in the statement list
            Policy policy = reader.createPolicyFromJsonString(policyString);
            List<Statement> clonedStatements = new ArrayList<>();
            for (Statement statement : policy.getStatements()) {
                Statement normalizedStatement = cloneStatement(statement);
                normalizedStatement.getActions().clear();
                for (com.amazonaws.auth.policy.Action action : statement.getActions()) {
                    enumerateActions(normalizedStatement, action, existingEntity.getName());
                }
                clonedStatements.add(normalizedStatement);
            }
            combineLikeActions(clonedStatements);
            policy.setStatements(clonedStatements);
            returnPolicies.add(policy.toJson());
        }
        return returnPolicies;
    }

    private List<GenericAction> getAllAuditableActions() {
        Set<GenericAction> allActions = new HashSet<>();
        for (MergedService mergedService : serviceManager.getLogNamedService().values()) {
            mergedService.getAuditedActions().forEach(s -> allActions.add(new GenericAction(mergedService.getCloudTrailName(), s)));
        }
        return new ArrayList<>(allActions);
    }


    private Set<GenericAction> enumerateActions(Statement normalizedStatement, com.amazonaws.auth.policy.Action action, String entityName) {
        Set<GenericAction> returnActions = new HashSet<>();
        if (action.getActionName().equals("*")) {
            returnActions.addAll(getAllAuditableActions());
            normalizedStatement.getActions().addAll(getAllAuditableActions());
        } else {
            GenericAction currentAction = new GenericAction(action.getActionName());
            MergedService mergedService = serviceManager.getIamNamedService().get(currentAction.getSplitServiceName());
            if (mergedService == null) {
                addOrCreateMissingService(currentAction.getSplitServiceName(), entityName);
                System.out.println("MISSING SERVICE: " + currentAction.getSplitServiceName());
            } else if (!mergedService.getAuditActionLevel().equals(Service.AuditActionLevel.NONE)) { //If not listed in logServices, ignore this action
                List<String> validActions = mergedService.getAuditedActions(); //AuditLevel.ALL
                if (currentAction.getSplitActionName().contains("*")) {
                    for (String serviceAction : validActions) {
                        GenericAction enumeratedAction = new GenericAction(currentAction.getSplitServiceName(), serviceAction);
                        if (currentAction.getSplitActionName().equals("*") || matchesPrefix(currentAction.getSplitActionName(), serviceAction)) {
                            returnActions.add(enumeratedAction);
                            normalizedStatement.getActions().add(enumeratedAction);
                        }
                    }
                } else {
                    returnActions.add(currentAction);
                    normalizedStatement.getActions().add(currentAction);
                }
            }
        }
        return returnActions;
    }

    private void addOrCreateMissingService(String service, String entity) {
        Set<String> missingEntities = null;
        if (missingServices.containsKey(service))
            missingEntities = missingServices.get(service);
        else {
            missingEntities = Collections.synchronizedSet(new HashSet<>());
            missingServices.put(service, missingEntities);
        }
        missingEntities.add(entity);
    }

    private boolean matchesPrefix(String iamGrant, String actionName) {
        int actionPrefixIndex = iamGrant.indexOf("*");
        String actionPrefix = iamGrant.substring(0, actionPrefixIndex).toLowerCase();
        if (actionName.toLowerCase().startsWith(actionPrefix))
            return true;
        else
            return false;
    }

    /**
     * If two statements in the list have the same resources, effect, conditions, and principals then combine their actions.
     * TODO refactor this to use Equator interface?
     * @param statementList
     * @return
     */
    public static List<Statement> combineLikeActions(List<Statement> statementList) {
        List<Statement> results = statementList;
        for (int i = 0; i < statementList.size(); i++) {
            Statement outerStatement = statementList.get(i);
            if (statementList.size() >= i + 1) {
                for (int j = i + 1; j < statementList.size(); j++) {
                    Statement innerStatement = statementList.get(j);
                    // if two statements use the same set of resources
                    if (CollectionUtils.isEqualCollection(outerStatement.getResources(), innerStatement.getResources(), new ResourceEquator())) {
                        // same effect
                        if (outerStatement.getEffect().equals(innerStatement.getEffect())) {
                            // same set of conditions
                            if (CollectionUtils.isEqualCollection(outerStatement.getConditions(), innerStatement.getConditions(), new ConditionEquator())) {
                                // if two statements use the same principals, add innerStatment actions to outerStatement actions and delete outerStatment
                                if (CollectionUtils.isEqualCollection(outerStatement.getPrincipals(), innerStatement.getPrincipals(), new PrincipalEquator())) {
                                    TreeSet<com.amazonaws.auth.policy.Action> combinedActions = new TreeSet<>(new ActionComparator());
                                    combinedActions.addAll(innerStatement.getActions());
                                    combinedActions.addAll(outerStatement.getActions());
                                    outerStatement.setActions(new ArrayList<>(combinedActions));
                                    statementList.remove(j);
                                    j = i;
                                }
                            }
                        }
                    }
                }
            }
        }

        return results;
    }

    public Map<String, Set<String>> getMissingServices() {
        return missingServices;
    }

    private Statement cloneStatement(Statement inputStatement) {
        try {
            Statement outputStatement = new Statement(inputStatement.getEffect());
            outputStatement.setActions(inputStatement.getActions());
            outputStatement.setConditions(inputStatement.getConditions());
            outputStatement.setId(inputStatement.getId());
            outputStatement.setPrincipals(inputStatement.getPrincipals());
            outputStatement.setResources(inputStatement.getResources());

            return outputStatement;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(1);
            return null;
        }
    }

}
