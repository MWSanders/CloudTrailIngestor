package edu.mines.model;

import com.amazonaws.auth.policy.Resource;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * Created by Matt on 4/26/2016.
 */
public class ResourceCodec implements Codec<Resource> {

    @Override
    public Resource decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Resource resource, EncoderContext encoderContext) {
        System.out.println();
    }

    @Override
    public Class<Resource> getEncoderClass() {
        return Resource.class;
    }
//
//    @Override
//    public Resource generateIdIfAbsentFromDocument(Resource resource) {
//        return null;
//    }
//
//    @Override
//    public boolean documentHasId(Resource resource) {
//        return resource.getId() != null;
//    }
//
//    @Override
//    public BsonValue getDocumentId(Resource resource) {
//        return new BsonString(resource.getId());
//    }
}
