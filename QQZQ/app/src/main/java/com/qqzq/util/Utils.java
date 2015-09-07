package com.qqzq.util;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/5.
 */
public class Utils {

    public static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static String makeGetRequestUrl(String url, Map<String, Object> parameters) {
        StringBuilder makedUrl = new StringBuilder();
        makedUrl.append(url);
        makedUrl.append("?");
        for (String key : parameters.keySet()) {
            Object value = parameters.get(key);
            if (makedUrl.indexOf("&") <= 0) {
                makedUrl.append("&");
            }
            makedUrl.append(key);
            makedUrl.append("=");
            makedUrl.append(value);
        }

        return makedUrl.toString();
    }
}
