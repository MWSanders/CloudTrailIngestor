package edu.mines.model;

import com.amazonaws.auth.policy.Statement;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.UUID;

/**
 * Created by Matt on 4/26/2016.
 */
public class StatementCodec implements CollectibleCodec<Statement> {
    //    private Codec<Document> documentCodec;
//
//    public StatementCodec(Codec<Document> codec) {
//        this.documentCodec = codec;
//    }
    private final CodecRegistry codecRegistry;

    public StatementCodec(final CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public Statement decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Statement statement, EncoderContext encoderContext) {
        System.out.println();
    }

    @Override
    public Class<Statement> getEncoderClass() {
        return Statement.class;
    }

    @Override
    public Statement generateIdIfAbsentFromDocument(Statement statement) {
        statement.setId(UUID.randomUUID().toString());
        return statement;
    }

    @Override
    public boolean documentHasId(Statement statement) {
        return statement.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(Statement statement) {
        return new BsonString(statement.getId());
    }
}
