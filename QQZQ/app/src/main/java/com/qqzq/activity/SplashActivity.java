package com.qqzq.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.util.NetUtil;

/**
 * Created by jie.xiao on 15/9/26.
 */
public class SplashActivity extends BaseActivity {

    private LinearLayout ll_splash_root;
    private TextView tv_version;
    // 提示登录是否成功标志
    private boolean flagLoginSuccess;

    private static final int sleepTime = 2000;
    private static final String TAG = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        initView();
//        initListener();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!NetUtil.detect(SplashActivity.this)) {
            BaseActivity.showShortToast(getApplicationContext(), Constants.NET_BAD);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {

                }

                //进入主页面
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }

    private void initView() {
        ll_splash_root = (LinearLayout) findViewById(R.id.ll_splash_root);
        tv_version = (TextView) findViewById(R.id.tv_version);
    }

    private void initData() {
        tv_version.setText(getVersion());
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        ll_splash_root.startAnimation(animation);
    }


    private String getVersion() {
        String version = getResources().getString(R.string.error_version_number_wrong);
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }
}
