package com.qqzq.util.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by jie.xiao on 15/8/29.
 */
public class DateSerializer implements JsonSerializer<Date> {
/*
    @Override
    public JsonElement serialize(Date src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        String strDate = Constants.mFormat.format(src);
        return src == null ? null : new JsonPrimitive(strDate);
    }*/

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc,
                                 JsonSerializationContext context) {
        return src == null ? null : new JsonPrimitive(src.getTime());
    }
}
