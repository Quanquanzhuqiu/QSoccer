package com.qqzq.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.listener.TopBarListener;
import com.qqzq.widget.menu.TopBar;

/**
 * Created by jie.xiao on 15/12/2.
 */
public class MeWalletActivity extends BaseActivity {

    private final static String TAG = "MeWalletActivity";
    private Activity context = this;
    private TopBar mTopbar;
    private LinearLayout mMainLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me_wallet);
//        initData();
        initView();
        initListener();
    }

    private void initView() {
        mTopbar = (TopBar) findViewById(R.id.topbar);
        mMainLinearLayout = (LinearLayout) findViewById(R.id.ll_main);
    }

    private void initListener() {
        mTopbar.setListener(new TopBarListener() {

            @Override
            public void leftButtonClick() {
            }

            @Override
            public void rightButtonClick() {
                Intent intent = new Intent(context, MeWalletDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public int getButtonType() {
                return TopBarListener.RIGHT;
            }
        });
    }
}
