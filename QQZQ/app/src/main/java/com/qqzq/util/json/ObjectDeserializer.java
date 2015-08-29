package com.qqzq.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by jie.xiao on 15/8/29.
 */
public class ObjectDeserializer implements JsonDeserializer<Object> {

    private String _classNm;

    public ObjectDeserializer(String classNm) {
        _classNm = classNm;
    }

    @Override
    public Object deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context) throws JsonParseException {
        Class innerObj;
        try {
            innerObj = Class.forName(_classNm);
            Object obj = context.deserialize(json, innerObj);
            return obj;
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
