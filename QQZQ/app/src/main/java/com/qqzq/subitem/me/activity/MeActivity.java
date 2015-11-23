package com.qqzq.subitem.me.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.qqzq.R;
import com.qqzq.activity.BaseActivity;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class MeActivity extends BaseActivity {

    private final static String TAG = "MeActivity";

    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me);

//        initDate();
//        initView();
//        initListener();
    }
}
