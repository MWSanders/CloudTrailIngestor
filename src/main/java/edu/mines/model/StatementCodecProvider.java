package edu.mines.model;

import com.amazonaws.auth.policy.Statement;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Created by Matt on 4/26/2016.
 */
public class StatementCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == Statement.class) {
            return (Codec<T>) new StatementCodec(codecRegistry);
        }
        return null;
    }
}
