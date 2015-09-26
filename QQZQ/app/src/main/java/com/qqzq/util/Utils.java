package com.qqzq.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/5.
 */
public class Utils {

    private static SimpleDateFormat simpleDateFormatFull = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");
    private static SimpleDateFormat simpleDateFormatSimple = new SimpleDateFormat("yyyy年MM月dd日");

    public static String makeGetRequestUrl(String url, Map<String, Object> parameters) {
        StringBuilder makedUrl = new StringBuilder();
        makedUrl.append(url);
        makedUrl.append("?");
        for (String key : parameters.keySet()) {
            Object value = parameters.get(key);
            makedUrl.append("&");
            makedUrl.append(key);
            makedUrl.append("=");
            makedUrl.append(value);
        }

        makedUrl.deleteCharAt(makedUrl.indexOf("&"));

        return makedUrl.toString();
    }

    public static String getFormatedSimpleDate(Date date) {
        return simpleDateFormatSimple.format(date);
    }

    public static String getFormatedFullDate(Date date) {
        return simpleDateFormatFull.format(date);
    }
}
