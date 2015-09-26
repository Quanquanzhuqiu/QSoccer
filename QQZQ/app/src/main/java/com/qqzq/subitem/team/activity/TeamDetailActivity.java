package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/24.
 */
public class TeamDetailActivity extends BaseActivity implements View.OnClickListener {

    private Activity context = this;
    private TextView tv_titile;
    private ImageView iv_back;
    private ImageView iv_share;
    private TextView tv_team_name;
    private TextView tv_team_captain;
    private TextView tv_team_game_times;
    private TextView tv_team_points;
    private TextView tv_team_location;
    private TextView tv_team_establish_day;
    private TextView tv_team_description;

    private Bundle extras;
    private final String TAG = "TeamDetailActivity";
    private String selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        initView();
        initLinstener();
        initData();

//        initTestData();
    }

    private void initView() {
        tv_titile = (TextView) findViewById(R.id.tv_title);
        tv_titile.setText("球队资料");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        tv_team_name = (TextView) findViewById(R.id.tv_team_name);
        tv_team_captain = (TextView) findViewById(R.id.tv_team_captain);
        tv_team_game_times = (TextView) findViewById(R.id.tv_team_game_times);
        tv_team_points = (TextView) findViewById(R.id.tv_team_points);
        tv_team_location = (TextView) findViewById(R.id.tv_team_location);
        tv_team_establish_day = (TextView) findViewById(R.id.tv_team_establish_day);
        tv_team_description = (TextView) findViewById(R.id.tv_team_description);
    }

    private void initLinstener() {

        // 初始化控件监听器
        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    private void initData() {
        extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
            if (!TextUtils.isEmpty(selectedTeamId)) {
                loadTeamDetailFromBackend(selectedTeamId);
            }
        }
    }

    private void initForm(EntTeamInfo entTeamInfo) {
        tv_team_name.setText(entTeamInfo.getTeamname());
        tv_team_captain.setText(entTeamInfo.getTeamleadernm());
        tv_team_game_times.setText(entTeamInfo.getStat());
        tv_team_points.setText(entTeamInfo.getStat());
        tv_team_location.setText(String.valueOf(entTeamInfo.getOftencity()));
        tv_team_establish_day.setText(Utils.getFormatedSimpleDate(entTeamInfo.getEstablishdate()));
        tv_team_description.setText(entTeamInfo.getSumary());
    }

    public void loadTeamDetailFromBackend(String id) {

        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_BY_ID_URL, id);
        System.out.println(queryUrl);
        GsonRequest gsonRequest = new GsonRequest<EntTeamInfo>(queryUrl, EntTeamInfo.class,
                findTeamResponseListener);
        executeRequest(gsonRequest);
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

    ResponseListener findTeamResponseListener = new ResponseListener<EntTeamInfo>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            showLongToast(context, volleyError.toString());
        }

        @Override
        public void onResponse(EntTeamInfo entTeamInfo) {
            initForm(entTeamInfo);
        }
    };
}
