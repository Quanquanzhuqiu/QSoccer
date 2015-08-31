package com.qqzq.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareReferenceUtil {
    private static final String LOCATION_LATITUDE = "location_latitude";
    private static final String LOCATION_LONTITUDE = "location_lontitude";
    private static final String LOCATION_ADDRESS = "location_address";
    private static final String USER_ID = "user_id";
    private static final String USER_PUSH_ID = "user_push_id";
    private static final String IS_FIRST_RUN = "is_first_run";
    private static final String USER_AUTH_ID = "user_auth_id";
    private static final String USER_INFO_JSON = "user_info_json";
    private static final String APPLY_IDS = "student_apply_ids";
    private static final String PUSH_USER_INFO_KEY = "push_user_info";


    public static String getPushUserInfoJson(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        String account = settings.getString(PUSH_USER_INFO_KEY, null);
        return account;
    }

    public static void savePushUserInfoJson(Context context, String userInfo) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(PUSH_USER_INFO_KEY, userInfo);
        editor.commit();
    }

    public static int getApplyIds(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        int ids = settings.getInt(APPLY_IDS, -1);
        return ids;
    }

    public static void saveApplyIds(Context context, int ids) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putInt(APPLY_IDS, ids);
        editor.commit();
    }

    public static String getUserInfoJson(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        String account = settings.getString(USER_INFO_JSON, null);
        return account;
    }

    public static void saveUserInfoJson(Context context, String userInfo) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(USER_INFO_JSON, userInfo);
        editor.commit();
    }

    public static String getUserAuthId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        String account = settings.getString(USER_AUTH_ID, "");
        return account;
    }

    public static void saveUserAuthId(Context context, String userId) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(USER_AUTH_ID, userId);
        editor.commit();
    }

    public static boolean isFirstRun(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        return settings.getBoolean(IS_FIRST_RUN, true);
    }

    public static void setIsFirstRun(Context context, boolean isFirst) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putBoolean(IS_FIRST_RUN, isFirst);
        editor.commit();
    }

    public static String getUserId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        String account = settings.getString(USER_ID, "0");
        return account;
    }

    public static void saveUserId(Context context, String userId) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public static String getUserPushId(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        String account = settings.getString(USER_PUSH_ID, "0");
        return account;
    }

    public static void saveUserPushId(Context context, String userPushId) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(USER_PUSH_ID, userPushId);
        editor.commit();
    }

    public static String getLocationLatitude(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        String account = settings.getString(LOCATION_LATITUDE, "0");
        return account;
    }

    public static void saveLocationLatitude(Context context, String latitude) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(LOCATION_LATITUDE, latitude);
        editor.commit();
    }

    public static String getLocationLontitude(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        String account = settings.getString(LOCATION_LONTITUDE, "0");
        return account;
    }

    public static void saveLocationLontitude(Context context, String lontitude) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(LOCATION_LONTITUDE, lontitude);
        editor.commit();
    }

    public static String getLocationAddress(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        String account = settings.getString(LOCATION_ADDRESS, "");
        return account;
    }

    public static void saveLocationAddress(Context context, String address) {
        SharedPreferences settings = context.getSharedPreferences(Constants.SHARE_REFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(LOCATION_ADDRESS, address);
        editor.commit();
    }
}
