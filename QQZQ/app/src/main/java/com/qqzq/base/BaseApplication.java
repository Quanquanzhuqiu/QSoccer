package com.qqzq.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.qqzq.common.dto.EntLocation;
import com.qqzq.config.Constants;
import com.qqzq.db.DbOpenHelper;
import com.qqzq.db.LocationDao;
import com.qqzq.entity.EntUserInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.RequestManager;
import com.qqzq.network.ResponseListener;

import cn.smssdk.SMSSDK;


/**
 * Created by jie.xiao on 8/25/2015.
 */
public class BaseApplication extends Application {

    public static Context applicationContext;
    private static BaseApplication instance;
    private String userName = null;
    private String password = null;


    public static EntUserInfo entUserInfo;
    public static String QQZQ_USER = "";
    public static String QQZQ_PASSWORD = "";
    public static String QQZQ_TOKENT = "";
    public static SharedPreferences spQQZQ;
    private final static String TAG = "BaseApplication";

    public static BaseApplication getInstance() {

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initBasicData();
    }

    private void init() {
        applicationContext = this;
        instance = this;
        startVolleyRequestManager();
        VolleyLog.setTag(Constants.APP_NAME);
        startSmsSDK();

        spQQZQ = getSharedPreferences(Constants.PREF_APP, Context.MODE_PRIVATE);
    }

    private void startVolleyRequestManager() {
        RequestManager.init(applicationContext);
    }

    private void startSmsSDK() {
        SMSSDK.initSDK(this, Constants.MOD_APP_KEY, Constants.MOD_APP_SECRET);
    }

    /**
     * 获取当前登陆用户名
     *
     * @return
     */
    public String getUserName() {
        if (userName == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
            userName = preferences.getString(Constants.PREF_USERNAME, null);
        }
        return userName;
    }

    /**
     * 获取密码
     *
     * @return
     */
    public String getPassword() {
        if (password == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
            password = preferences.getString(Constants.PREF_PWD, null);
        }
        return password;
    }

    /**
     * 设置用户名
     *
     * @param username
     */
    public void setUserName(String username) {
        if (username != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
            SharedPreferences.Editor editor = preferences.edit();
            if (editor.putString(Constants.PREF_USERNAME, username).commit()) {
                userName = username;
            }
        }
    }

    /**
     * 设置密码
     *
     * @param pwd
     */
    public void setPassword(String pwd) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor = preferences.edit();
        if (editor.putString(Constants.PREF_PWD, pwd).commit()) {
            password = pwd;
        }
    }

    /**
     * 退出登录,清空数据
     */
    public void logout() {
        try {
            System.out.println("Cancel all json request!!!");
            RequestManager.cancelAll(applicationContext);

            //主要所有SMSSDK监听事件
            SMSSDK.unregisterAllEventHandler();
            DbOpenHelper.getInstance(applicationContext).closeDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initBasicData() {

        LocationDao locationDao = new LocationDao(this);
        EntLocation location = locationDao.findLocationById("1");

        if (location != null) {
            Log.i(TAG, "本地已有省市信息，无需向后台请求！");
            return;
        }

        GsonRequest gsonRequest = new GsonRequest<EntLocation[]>(Constants.API_FIND_NATIONS_URL, EntLocation[].class,
                new ResponseListener<EntLocation[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.toString());
                    }

                    @Override
                    public void onResponse(final EntLocation[] entLocationInfos) {
                        Log.i(TAG, "找到地区->" + entLocationInfos.length);
                        final LocationDao locationDao = new LocationDao(applicationContext);
                        if (locationDao.findAll().size() != entLocationInfos.length) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    locationDao.saveLocaitonList(entLocationInfos);
                                }
                            }).start();

                        }
                    }
                }, true);
        RequestManager.getRequestQueue().add(gsonRequest);
    }

}
