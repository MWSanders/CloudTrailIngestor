package edu.mines.model;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.Decoder;
import org.bson.codecs.Encoder;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Matt on 7/3/2016.
 */
public class DocumentProxy {
    public Document document = new Document();

    public DocumentProxy() {}

    public DocumentProxy(Document document) {
        this.document = document;
    }

    public void setDelegate(Document value) {
        document = value;
    }

    public Document getDelegate() {
        return document;
    }

    public static Document parse(String json) {
        return Document.parse(json);
    }

    public Object put(String key, Object value) {
        return document.put(key, value);
    }

    public Object get(Object key) {
        return document.get(key);
    }

    public String toJson() {
        return document.toJson();
    }

    public String toJson(JsonWriterSettings writerSettings) {
        return document.toJson(writerSettings);
    }

    public int getInteger(Object key, int defaultValue) {
        return document.getInteger(key, defaultValue);
    }

    public boolean containsKey(Object key) {
        return document.containsKey(key);
    }

    public void replaceAll(BiFunction<? super String, ? super Object, ?> function) {
        document.replaceAll(function);
    }

    public Object computeIfPresent(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return document.computeIfPresent(key, remappingFunction);
    }

    public boolean replace(String key, Object oldValue, Object newValue) {
        return document.replace(key, oldValue, newValue);
    }

    public Object compute(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return document.compute(key, remappingFunction);
    }

    public Document append(String key, Object value) {
        return document.append(key, value);
    }

    public boolean isEmpty() {
        return document.isEmpty();
    }

    public boolean remove(Object key, Object value) {
        return document.remove(key, value);
    }

    public boolean getBoolean(Object key, boolean defaultValue) {
        return document.getBoolean(key, defaultValue);
    }

    public boolean containsValue(Object value) {
        return document.containsValue(value);
    }

    public <C> BsonDocument toBsonDocument(Class<C> documentClass, CodecRegistry codecRegistry) {
        return document.toBsonDocument(documentClass, codecRegistry);
    }

    public Set<String> keySet() {
        return document.keySet();
    }

    public Date getDate(Object key) {
        return document.getDate(key);
    }

    public Object remove(Object key) {
        return document.remove(key);
    }

    public Double getDouble(Object key) {
        return document.getDouble(key);
    }

    public Object computeIfAbsent(String key, Function<? super String, ?> mappingFunction) {
        return document.computeIfAbsent(key, mappingFunction);
    }

    public Integer getInteger(Object key) {
        return document.getInteger(key);
    }

    public ObjectId getObjectId(Object key) {
        return document.getObjectId(key);
    }

    public String toJson(Encoder<Document> encoder) {
        return document.toJson(encoder);
    }

    public String getString(Object key) {
        return document.getString(key);
    }

    public Boolean getBoolean(Object key) {
        return document.getBoolean(key);
    }

    public Object putIfAbsent(String key, Object value) {
        return document.putIfAbsent(key, value);
    }

    public int size() {
        return document.size();
    }

    public Object merge(String key, Object value, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return document.merge(key, value, remappingFunction);
    }

    public Object replace(String key, Object value) {
        return document.replace(key, value);
    }

    public void clear() {
        document.clear();
    }

    public void putAll(Map<? extends String, ?> map) {
        document.putAll(map);
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        return document.getOrDefault(key, defaultValue);
    }

    public static Document parse(String json, Decoder<Document> decoder) {
        return Document.parse(json, decoder);
    }

    public String toJson(JsonWriterSettings writerSettings, Encoder<Document> encoder) {
        return document.toJson(writerSettings, encoder);
    }

    public Collection<Object> values() {
        return document.values();
    }

    public Long getLong(Object key) {
        return document.getLong(key);
    }

    public <T> T get(Object key, Class<T> clazz) {
        return document.get(key, clazz);
    }

    public void forEach(BiConsumer<? super String, ? super Object> action) {
        document.forEach(action);
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return document.entrySet();
    }

    @Override
    public String toString() {
        return document.toString();
    }

    @Override
    public int hashCode() {
        return document.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return document.equals(o);
    }
}
