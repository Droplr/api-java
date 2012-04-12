package com.droplr.service.serialization;

import com.droplr.common.TextUtils;
import com.droplr.http.processor.AbstractAccumulatorProcessor;
import com.droplr.service.domain.Drop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jboss.netty.buffer.ChannelBuffer;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author <a href="http://biasedbit.com/">Bruno de Carvalho</a>
 */
public class JsonGenericDeserializer<T> extends AbstractAccumulatorProcessor<T> {

    // AbstractAccumulatorProcessor -----------------------------------------------------------------------------------

    @Override
    protected T convertBufferToResult(ChannelBuffer buffer) {
        // Convert the buffer to a string (UTF8, JSON)
        String content = buffer.toString(TextUtils.UTF_8);

        // Convert JSON string to list of objects
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<Drop>>(){}.getType();
        return gson.fromJson(content, collectionType); // May throw JsonParseException
    }
}
