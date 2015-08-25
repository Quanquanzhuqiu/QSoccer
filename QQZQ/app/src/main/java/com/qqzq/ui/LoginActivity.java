package com.qqzq.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.qqzq.BaseActivity;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class LoginActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        startActivity(new Intent(getApplicationContext(), MainTabBar.class));
    }
}
