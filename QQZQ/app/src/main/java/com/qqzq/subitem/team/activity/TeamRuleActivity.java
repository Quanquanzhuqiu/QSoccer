package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.listener.BackButtonListener;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

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
    private String selectedTeamRule;

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
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_RULE)) {

            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
            selectedTeamRule = extras.getString(Constants.EXTRA_SELECTED_TEAM_RULE);
            initForm();
        }
    }

    private void initForm() {
        if (!TextUtils.isEmpty(selectedTeamRule)) {
            edt_team_rule.setText(selectedTeamRule);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                commit();
                break;
            default:
                break;
        }
    }

    private String formCheck() {

        if (TextUtils.isEmpty(edt_team_rule.getText())) {
            return "请输入球队章程";
        }

        return null;
    }

    public Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();

        String teamRule = edt_team_rule.getText().toString();
        Log.i(TAG, "球队章程:" + teamRule);
        EntTeamInfo entTeamInfo = new EntTeamInfo();
        entTeamInfo.setId(Integer.valueOf(selectedTeamId));
        entTeamInfo.setTeamrule(teamRule);
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entTeamInfo);
        return mParameters;
    }


    private void commit() {
        String checkResult = formCheck();
        if (!TextUtils.isEmpty(checkResult)) {
            Toast.makeText(context, checkResult, Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> mParameters = prepareRequestJson();
        String url = MessageFormat.format(Constants.API_CREATE_TEAM_RULE_URL, selectedTeamId);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, url,
                EntClientResponse.class, null, mParameters, createTeamRuleResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener createTeamRuleResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.i(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            Intent intent = new Intent(context, TeamDetailActivity.class);
            intent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
            startActivity(intent);

        }
    };
}
