package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.listener.BackButtonListener;

/**
 * Created by jie.xiao on 15/9/20.
 */
public class TeamRuleActivity extends BaseActivity implements View.OnClickListener {

    private Activity context = this;
    private EditText edt_team_rule;
    private TextView tv_title;
    private TextView tv_commit;
    private LinearLayout ll_back;

    private final String TAG = "TeamRuleActivity";
    private String selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_rule);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        edt_team_rule = (EditText) findViewById(R.id.edt_team_rule);

        tv_title.setText("球队章程");
        tv_commit.setText("发布");


    }

    private void initListener() {
        tv_commit.setOnClickListener(this);
        ll_back.setOnClickListener(new BackButtonListener(this));
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                break;
            default:
                break;
        }
    }

    private void prepareRequestJson() {

    }
}
