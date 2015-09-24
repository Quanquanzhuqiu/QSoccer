package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.activity.BaseActivity;

/**
 * Created by jie.xiao on 15/9/24.
 */
public class TeamDetailActivity extends BaseActivity implements View.OnClickListener {

    private Activity context = this;
    private TextView tv_titile;
    private ImageView iv_back;
    private ImageView iv_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        initView();
        initLinstener();
//        initData();

//        initTestData();
    }

    private void initView() {
        tv_titile = (TextView) findViewById(R.id.tv_title);
        tv_titile.setText("球队资料");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_share = (ImageView) findViewById(R.id.iv_share);
    }

    private void initLinstener() {

        // 初始化控件监听器
        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_share:
                break;

        }
    }
}
