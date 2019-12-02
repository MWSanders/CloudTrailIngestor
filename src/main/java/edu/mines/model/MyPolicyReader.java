package edu.mines.model;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.auth.policy.internal.JsonPolicyReader;
import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Matt on 8/4/2017.
 */
public class MyPolicyReader extends JsonPolicyReader {
    JsonPolicyReader reader;
    ObjectMapper mapper = new ObjectMapper();

    public MyPolicyReader() {
        reader = new JsonPolicyReader();
    }

    /**
     * Horrible, dirty hack because the AWS JsonPolicyReader cannot properly read policies that do not enclose statements
     * inside of an array which is usually the case for AssumeRole statements.
     *
     * @param jsonString the specified JSON string representation of this AWS access
     *                   control policy.
     * @return An AWS policy object.
     * @throws IllegalArgumentException If the specified JSON string is null or invalid and cannot be
     *                                  converted to an AWS policy object.
     */
    @Override
    public Policy createPolicyFromJsonString(String jsonString) {
        if (jsonString == null) {
            throw new IllegalArgumentException("JSON string cannot be null");
        }

        JsonNode policyNode;
        JsonNode statementNodes;
        Policy policy = new Policy();
        List<Statement> statements = new LinkedList<Statement>();

        try {
            policyNode = Jackson.jsonNodeOf(jsonString);
            statementNodes = policyNode.get(JsonDocumentFields.STATEMENT);
            if (statementNodes instanceof ArrayNode)
                return super.createPolicyFromJsonString(jsonString);
            else if (statementNodes instanceof ObjectNode) {
                ArrayNode arrayNode = mapper.createArrayNode();
                arrayNode.add(statementNodes);
                ((ObjectNode) policyNode).put(JsonDocumentFields.STATEMENT, arrayNode);
                return super.createPolicyFromJsonString(policyNode.toString());
            }
            System.out.println(statementNodes);


        } catch (Exception e) {
            String message = "Unable to generate policy object fron JSON string "
                    + e.getMessage();
            throw new IllegalArgumentException(message, e);
        }
        policy.setStatements(statements);
        return policy;
    }
}
