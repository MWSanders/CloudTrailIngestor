package edu.mines.model;

import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import java.util.ArrayList;


/**
 * Created by Matt on 4/24/2016.
 */
public class StatementConverter extends TypeConverter implements SimpleValueConverter {
    public StatementConverter() {
        super(Statement.class);
    }

    @Override
    public Object decode(Class<?> aClass, Object o, MappedField mappedField) {
        BasicDBObject basicDBObject = (BasicDBObject) o;
        String value = basicDBObject.getString("effect");
        Statement statement = new Statement(Statement.Effect.valueOf(value));
        statement.setResources(new ArrayList<>());

        ResourceConverter resourceConverter = new ResourceConverter();

        BasicDBList resources = (BasicDBList) basicDBObject.get("resources");
        resources.forEach(o1 -> {
            Resource resource = (Resource) resourceConverter.decode(Resource.class, o1, mappedField);
            statement.getResources().add(resource);
        });

        BasicDBList actions = (BasicDBList) basicDBObject.get("actions");
        if (actions != null) {
            actions.forEach(o1 -> {
                BasicDBObject actionObject = (BasicDBObject) o1;
                String actionName = actionObject.getString("actionServiceName");
                statement.getActions().add(new GenericAction(actionName));
            });
        }
        return statement;
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        return super.encode(value, optionalExtraInfo);
    }
}