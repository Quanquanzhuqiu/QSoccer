package com.qqzq.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.qqzq.common.Constants;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by jie.xiao on 15/8/29.
 */
public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
        Date date = null;
        try {
            date = Constants.mFormat.parse(json.getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json == null ? null : date;
    }

}
