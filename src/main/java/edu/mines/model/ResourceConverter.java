package edu.mines.model;

import com.amazonaws.auth.policy.Resource;
import com.mongodb.BasicDBObject;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

/**
 * Created by Matt on 4/24/2016.
 */
public class ResourceConverter extends TypeConverter {
    public ResourceConverter() {
        super(Resource.class);
    }

    @Override
    public Object decode(Class<?> aClass, Object o, MappedField mappedField) {
        BasicDBObject basicDBObject = (BasicDBObject) o;
        String value = basicDBObject.getString("resource");
        return new Resource(value);
    }
}
