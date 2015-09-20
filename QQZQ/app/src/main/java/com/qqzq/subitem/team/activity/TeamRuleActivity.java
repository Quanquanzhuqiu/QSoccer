package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;

/**
 * Created by jie.xiao on 15/9/20.
 */
public class TeamRuleActivity extends BaseActivity implements View.OnClickListener {

    private Activity context = this;
    private EditText edt_team_rule;
    private TextView tv_title;
    private TextView tv_commit;

    private String selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);

        init();
    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        edt_team_rule = (EditText) findViewById(R.id.edt_team_rule);

        tv_title.setText("球队章程");
        tv_commit.setText("发布");

        Bundle extras = context.getIntent().getExtras();
        if (extras != null && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {
            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
        }

        tv_commit.setOnClickListener(this);
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

    private void prepareRequestJson(){

    }
}
