package com.qqzq;

import android.app.Application;
import android.content.Context;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class BaseApplication extends Application{

    public static Context applicationContext;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
