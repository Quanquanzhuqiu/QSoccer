package com.qqzq.me.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.qqzq.R;
import com.qqzq.base.BaseActivity;

/**
 * Created by jie.xiao on 15/12/2.
 */
public class MeWalletDetailActivity extends BaseActivity {

    private final static String TAG = "MeWalletDetailActivity";
    private Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me_wallet_detail);
    }
}
