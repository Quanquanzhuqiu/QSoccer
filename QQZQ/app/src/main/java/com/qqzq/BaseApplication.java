package com.qqzq;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.qqzq.entity.EntUserInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.util.Constants;


/**
 * Created by jie.xiao on 8/25/2015.
 */
public class BaseApplication extends Application {

    public static Context applicationContext;
    private static BaseApplication instance;
    private String userName = null;
    private String password = null;
    //请求队列
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(BaseApplication.applicationContext);

        applicationContext = this;
        instance = this;

        getJson();
    }

    public static BaseApplication getInstance() {

        return instance;
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
        // reset password to null
        setPassword(null);
//		setContactList(null);

    }


    public void getJson() {
        String url = "http://121.43.229.24:8080/qqzq/rest/user/users?offset=0&limit=10";

        GsonRequest<EntUserInfo[]> gsonRequest = new GsonRequest<>(
                url,
                EntUserInfo[].class,
                new Response.Listener<EntUserInfo[]>() {
                    @Override
                    public void onResponse(EntUserInfo[] response) {
                        System.out.println("+++++++++" + response[0].getUsername());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("sorry,Error");
                        error.printStackTrace();
                    }
                }
        );
        mRequestQueue.add(gsonRequest);
    }
}
