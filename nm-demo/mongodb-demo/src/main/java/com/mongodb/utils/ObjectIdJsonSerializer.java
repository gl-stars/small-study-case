package com.mongodb.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * 生成id
 * @author: gl_stars
 * @data: 2020年 09月 09日 16:28
 **/
public class ObjectIdJsonSerializer extends JsonSerializer<ObjectId> {

    @Override
    public void serialize(ObjectId o, JsonGenerator j, SerializerProvider s) throws IOException {
        if (o == null) {
            j.writeNull();
        } else {
            j.writeString(o.toString());
        }
    }
}