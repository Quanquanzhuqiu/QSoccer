package com.qqzq.subitem.game.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.activity.BaseActivity;

/**
 * Created by jie.xiao on 15/9/15.
 */
public class GamePublishActivity extends BaseActivity {

    private TextView tv_titile;
    private TextView tv_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_publish);
        init();
    }

    private void init(){
        tv_titile = (TextView) findViewById(R.id.tv_title);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_titile.setText("发起活动");
        tv_commit.setText("发布");
    }
}
