package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.activity.BaseApplication;
import com.qqzq.activity.MainActivity;
import com.qqzq.config.Constants;
import com.qqzq.db.LocationDao;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntLocation;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.EntTeamJoinInfo;
import com.qqzq.listener.BackButtonListener;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/24.
 */
public class TeamDetailActivity extends BaseActivity implements View.OnClickListener {

    private Activity context = this;
    private TextView tv_team_name;
    private TextView tv_team_captain;
    private TextView tv_attendance_count;
    private TextView tv_personal_score;
    private TextView tv_team_location;
    private TextView tv_team_establish_day;
    private TextView tv_team_description;
    private LinearLayout ll_team_rule;
    private LinearLayout ll_team_gallery;
    private LinearLayout ll_team_member;
    private LinearLayout ll_game_list;
    private Button btn_commit;

    private final static String TAG = "TeamDetailActivity";
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
        tv_team_name = (TextView) findViewById(R.id.tv_team_name);
        tv_team_captain = (TextView) findViewById(R.id.tv_team_captain);
        tv_attendance_count = (TextView) findViewById(R.id.tv_attendance_count);
        tv_personal_score = (TextView) findViewById(R.id.tv_personal_score);
        tv_team_location = (TextView) findViewById(R.id.tv_team_location);
        tv_team_establish_day = (TextView) findViewById(R.id.tv_team_establish_day);
        tv_team_description = (TextView) findViewById(R.id.tv_team_description);
        ll_team_rule = (LinearLayout) findViewById(R.id.ll_team_rule);
        ll_team_gallery = (LinearLayout) findViewById(R.id.ll_team_gallery);
        ll_game_list = (LinearLayout) findViewById(R.id.ll_game_list);
        ll_team_member = (LinearLayout) findViewById(R.id.ll_team_member);
        btn_commit = (Button) findViewById(R.id.btn_commit);
    }

    private void initLinstener() {

        // 初始化控件监听器
        ll_team_rule.setOnClickListener(this);
        ll_team_gallery.setOnClickListener(this);
        ll_game_list.setOnClickListener(this);
        ll_team_member.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
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
        tv_attendance_count.setText(entTeamInfo.getStat());
        tv_personal_score.setText(entTeamInfo.getStat());
        tv_team_establish_day.setText(Utils.getFormatedSimpleDate(entTeamInfo.getEstablishdate()));
        tv_team_description.setText(entTeamInfo.getSumary());

        LocationDao locationDao = new LocationDao(context);

        EntLocation cityInfo = locationDao.findLocationById(String.valueOf(entTeamInfo.getOftencity()));
        EntLocation distinctInfo = locationDao.findLocationById(String.valueOf(entTeamInfo.getOftendistinct()));
        if (cityInfo != null && distinctInfo != null) {
            tv_team_location.setText(String.valueOf(cityInfo.getLocation() + " " + distinctInfo.getLocation()));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.iv_share:
//                break;
            case R.id.ll_team_rule:
                Intent teamRuleIntent = new Intent(context, TeamRuleActivity.class);
                teamRuleIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                startActivity(teamRuleIntent);
                break;
            case R.id.ll_team_gallery:
                Intent teamGalleryIntent = new Intent(context, TeamGalleryActivity.class);
                teamGalleryIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                startActivity(teamGalleryIntent);
                break;
            case R.id.ll_team_member:
                Intent teamMemberIntent = new Intent(context, TeamMemberActivity.class);
                teamMemberIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                startActivity(teamMemberIntent);
                break;
            case R.id.ll_game_list:
                break;
            case R.id.btn_commit:
                joinTeam();
                break;
        }
    }

    private void loadTeamDetailFromBackend(String id) {

        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_BY_ID_URL, id);
        GsonRequest gsonRequest = new GsonRequest<EntTeamInfo>(queryUrl, EntTeamInfo.class,
                findTeamResponseListener);
        executeRequest(gsonRequest);
    }

    private void joinTeam() {
        String url = MessageFormat.format(Constants.API_JOIN_TEAM_URL, selectedTeamId);

        Map<String, Object> mParameters = prepareRequestJson();

        if (mParameters == null || mParameters.isEmpty()) {
            return;
        }

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, url,
                EntClientResponse.class, null, mParameters, joinTeamResponseListener);

        executeRequest(gsonRequest);
    }

    public Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();

        String teamName = tv_team_name.getText().toString();

        int personalScore = 0;
        if (!TextUtils.isEmpty(tv_personal_score.getText())) {
            personalScore = Integer.valueOf(tv_personal_score.getText().toString());
        }

        int attendanceCount = 0;
        if (!TextUtils.isEmpty(tv_attendance_count.getText())) {
            attendanceCount = Integer.valueOf(tv_attendance_count.getText().toString());
        }

        String userId = BaseApplication.QQZQ_USER;
        int teamId = Integer.valueOf(selectedTeamId);
        Date joinTime = new Date();

        EntTeamJoinInfo entTeamJoinInfo = new EntTeamJoinInfo();
//        entTeamJoinInfo.setUserid("2");
        entTeamJoinInfo.setUsername(userId);
        entTeamJoinInfo.setAttendancecount(attendanceCount);
        entTeamJoinInfo.setPersonalscore(personalScore);
        entTeamJoinInfo.setJointime(joinTime);
        entTeamJoinInfo.setCreatedate(joinTime);
        entTeamJoinInfo.setUpdatedate(joinTime);
        entTeamJoinInfo.setTeamname(teamName);
        entTeamJoinInfo.setTeamid(teamId);

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entTeamJoinInfo);
        return mParameters;
    }


    ResponseListener findTeamResponseListener = new ResponseListener<EntTeamInfo>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntTeamInfo entTeamInfo) {
            initForm(entTeamInfo);
        }
    };

    ResponseListener joinTeamResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse entClientResponse) {
            Log.i(TAG, "加入球队成功！");
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    };
}
