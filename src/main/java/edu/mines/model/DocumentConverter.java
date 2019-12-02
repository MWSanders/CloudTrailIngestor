package edu.mines.model;

import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import java.util.Map;


/**
 * Created by Matt on 4/24/2016.
 */
public class DocumentConverter extends TypeConverter implements SimpleValueConverter {
    public DocumentConverter() {
        super(Document.class);
    }

    @Override
    public Object decode(Class<?> aClass, Object o, MappedField mappedField) {
        BasicDBObject basicDBObject = (BasicDBObject) o;
        Document document = new Document();
        for (Map.Entry<String, Object> entry : basicDBObject.entrySet()) {
            document.append(entry.getKey(), entry.getValue());
        }
        return document;
    }

}