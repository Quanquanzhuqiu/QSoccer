package com.qqzq;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.qqzq.util.Constants;


/**
 * Created by jie.xiao on 8/25/2015.
 */
public class BaseApplication extends Application{

    public static Context applicationContext;
    private static BaseApplication instance;
    private String userName = null;
    private String password = null;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;
        instance = this;
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
     * @param user
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
}
