package com.qqzq.util;

import com.android.volley.VolleyError;
import com.qqzq.entity.EntClientResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jie.xiao on 15/9/5.
 */
public class Utils {

    private static SimpleDateFormat simpleDateFormatFull = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");
    private static SimpleDateFormat simpleDateFormatSimple = new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:ss");

    public static String makeGetRequestUrl(String url, Map<String, Object> parameters) {

        //如果参数为空，直接返回URL
        if (parameters == null) {
            return url;
        }

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

    public static String getStrDateWithWeek(Date date) {
        String strDate = getFormatedSimpleDate(date);
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String dayOfWeek = dateFm.format(date);
        return strDate + " " + dayOfWeek;
    }

    public static String getStrTime(Date date) {
        return timeFormat.format(date);
    }

    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    public static EntClientResponse parseErrorResponse(VolleyError error){
        EntClientResponse entClientResponse = new EntClientResponse();
        try {
            JSONObject jsonObject = new JSONObject(error.getMessage());
            entClientResponse.setCode(jsonObject.optString("code"));
            entClientResponse.setInnerMessage(jsonObject.optString("innerMessage"));
            entClientResponse.setCustomMessage(jsonObject.optString("customMessage"));
            entClientResponse.setMoreInfo(jsonObject.optString("moreInfo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  entClientResponse;
    }
}
