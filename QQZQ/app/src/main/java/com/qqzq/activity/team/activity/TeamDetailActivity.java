package com.qqzq.activity.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import com.qqzq.entity.EntTeamJoinInfo;
import com.qqzq.entity.EntTeamOperation;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.game.activity.GameAttendanceActivity;
import com.qqzq.subitem.game.activity.GameListActivity;
import com.qqzq.activity.team.adapter.TeamGalleryGridViewAdapter;
import com.qqzq.activity.team.adapter.TeamOperationGridViewAdapter;
import com.qqzq.util.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/24.
 */
public class TeamDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Activity context = this;
    private TextView tv_team_name;
    private TextView tv_team_captain;
    private TextView tv_team_play_times;
    private TextView tv_team_score;
    private TextView tv_team_location;
    private TextView tv_team_establish_day;
    private TextView tv_team_description;
    private LinearLayout ll_team_rule;
    private LinearLayout ll_team_gallery;
    private Button btn_commit;

    private GridView gv_team_operation;
    private TeamOperationGridViewAdapter teamOperationGridViewAdapter;
    private List<EntTeamOperation> operationList = new ArrayList<EntTeamOperation>();

    private GridView gv_team_gallery;
    private TeamGalleryGridViewAdapter teamGalleryGridViewAdapter;

    private final static String TAG = "TeamDetailActivity";
    private int selectedTeamId;
    private String selectedTeamRule;
    private float selectedTeamBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        initData();
        initView();
        initLinstener();

//        initTestData();
    }

    private void initView() {
        tv_team_name = (TextView) findViewById(R.id.tv_team_name);
        tv_team_captain = (TextView) findViewById(R.id.tv_team_captain);
        tv_team_play_times = (TextView) findViewById(R.id.tv_team_play_times);
        tv_team_score = (TextView) findViewById(R.id.tv_team_score);
        tv_team_location = (TextView) findViewById(R.id.tv_team_location);
        tv_team_establish_day = (TextView) findViewById(R.id.tv_team_establish_day);
        tv_team_description = (TextView) findViewById(R.id.tv_team_description);
        ll_team_rule = (LinearLayout) findViewById(R.id.ll_team_rule);
        ll_team_gallery = (LinearLayout) findViewById(R.id.ll_team_gallery);
        gv_team_operation = (GridView) findViewById(R.id.gv_team_operation);
        gv_team_gallery = (GridView) findViewById(R.id.gv_game_gallery);
        btn_commit = (Button) findViewById(R.id.btn_commit);

        teamOperationGridViewAdapter = new TeamOperationGridViewAdapter(context, operationList);
        gv_team_operation.setAdapter(teamOperationGridViewAdapter);
        setListViewHeightBaseOnChildren(gv_team_operation);
        teamOperationGridViewAdapter.notifyDataSetChanged();

        teamGalleryGridViewAdapter = new TeamGalleryGridViewAdapter(context, operationList);
        gv_team_gallery.setAdapter(teamGalleryGridViewAdapter);
    }

    private void initLinstener() {

        // 初始化控件监听器
        ll_team_rule.setOnClickListener(this);
        ll_team_gallery.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        gv_team_operation.setOnItemClickListener(this);
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getInt(Constants.EXTRA_SELECTED_TEAM_ID);
            if (selectedTeamId > 0) {
                loadTeamDetailFromBackend(selectedTeamId);
            }
        }

        //================球队操作列表================
        //球队成员
        EntTeamOperation teamMemberOperation = new EntTeamOperation();
        teamMemberOperation.setId(EntTeamOperation.TEAM_MEMBER);
        teamMemberOperation.setOperation("球队成员");
        teamMemberOperation.setLogo(getResources().getDrawable(R.drawable.ic_team_member));
        operationList.add(teamMemberOperation);
        //球队活动列表
        EntTeamOperation teamGamesOperation = new EntTeamOperation();
        teamGamesOperation.setId(EntTeamOperation.TEAM_GAME_LIST);
        teamGamesOperation.setOperation("球队活动列表");
        teamGamesOperation.setLogo(getResources().getDrawable(R.drawable.ic_team_games));
        operationList.add(teamGamesOperation);
        //现金消费记录
        EntTeamOperation teamCashPayRecordOperation = new EntTeamOperation();
        teamCashPayRecordOperation.setId(EntTeamOperation.TEAM_CASH_PAY_RECORD);
        teamCashPayRecordOperation.setOperation("现金消费记录");
        teamCashPayRecordOperation.setLogo(getResources().getDrawable(R.drawable.ic_team_cash_pay));
        operationList.add(teamCashPayRecordOperation);
        //会员费管理
        EntTeamOperation teamMemberFeeOperation = new EntTeamOperation();
        teamMemberFeeOperation.setId(EntTeamOperation.TEAM_MEMBER_FEE_MANAGE);
        teamMemberFeeOperation.setOperation("会员费管理");
        teamMemberFeeOperation.setLogo(getResources().getDrawable(R.drawable.ic_team_member_fee_manage));
        operationList.add(teamMemberFeeOperation);
        //出勤管理
        EntTeamOperation teamAttendanceManageOperation = new EntTeamOperation();
        teamAttendanceManageOperation.setId(EntTeamOperation.TEAM_ATTENDANCE_MANAGE);
        teamAttendanceManageOperation.setOperation("出勤管理");
        teamAttendanceManageOperation.setLogo(getResources().getDrawable(R.drawable.ic_team_attendance));
        operationList.add(teamAttendanceManageOperation);
    }

    private void initForm(EntTeamInfo entTeamInfo) {
        selectedTeamRule = entTeamInfo.getTeamrule();
        tv_team_name.setText(entTeamInfo.getTeamname());
        tv_team_captain.setText(entTeamInfo.getTeamleadernm());
        tv_team_play_times.setText(entTeamInfo.getActcount() + "");
        tv_team_score.setText(entTeamInfo.getTeamscore() + "");
        tv_team_location.setText(entTeamInfo.getOftencity() + "");
        tv_team_establish_day.setText(Utils.getFormatedSimpleDate(entTeamInfo.getEstablishdate()));
        tv_team_description.setText(entTeamInfo.getSumary());

        LocationDao locationDao = new LocationDao(context);

        EntLocation cityInfo = locationDao.findLocationById(String.valueOf(entTeamInfo.getOftencity()));
        EntLocation distinctInfo = locationDao.findLocationById(String.valueOf(entTeamInfo.getOftendistinct()));
        if (cityInfo != null && distinctInfo != null) {
            tv_team_location.setText(String.valueOf(cityInfo.getLocation() + " " + distinctInfo.getLocation()));
        }

        selectedTeamBalance = Float.valueOf(entTeamInfo.getTeambalance());
    }

    private void setListViewHeightBaseOnChildren(GridView gridView) {
        // 获取listview的adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;
        int totalHeight = 40;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            Log.i(TAG, "listItem.getMeasuredHeight() = " + listItem.getMeasuredHeight());
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        Log.i(TAG, "totalHeight = " + totalHeight);
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 10, 0, 10);
        // 设置参数
        gridView.setLayoutParams(params);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_team_rule:
                Intent teamRuleIntent = new Intent(context, TeamRuleActivity.class);
                teamRuleIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                teamRuleIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_RULE, selectedTeamRule);
                startActivity(teamRuleIntent);
                break;
            case R.id.ll_team_gallery:
                Intent teamGalleryIntent = new Intent(context, TeamGalleryActivity.class);
                teamGalleryIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                startActivity(teamGalleryIntent);
                break;
            case R.id.btn_commit:
                joinTeam();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i(TAG, "选中的球队操作 = " + id);
        if (!operationList.isEmpty()) {
            switch ((int) id) {
                case EntTeamOperation.TEAM_MEMBER:
                    Intent teamMemberIntent = new Intent(context, TeamMemberActivity.class);
                    teamMemberIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                    startActivity(teamMemberIntent);
                    break;
                case EntTeamOperation.TEAM_GAME_LIST:
                    Intent gameListIntent = new Intent(context, GameListActivity.class);
                    gameListIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                    gameListIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_NAME, tv_team_name.getText().toString());
                    startActivity(gameListIntent);
                    break;
                case EntTeamOperation.TEAM_CASH_PAY_RECORD:
                    break;
                case EntTeamOperation.TEAM_MEMBER_FEE_MANAGE:
                    Intent memberFeeManageIntent = new Intent(context, MemberFeeManageActivity.class);
                    memberFeeManageIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                    memberFeeManageIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_NAME, tv_team_name.getText().toString());
                    memberFeeManageIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_BALANCE, selectedTeamBalance);
                    startActivity(memberFeeManageIntent);
                    break;
                case EntTeamOperation.TEAM_ATTENDANCE_MANAGE:
                    Intent teamAttendanceIntent = new Intent(context, GameAttendanceActivity.class);
                    teamAttendanceIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                    teamAttendanceIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_NAME, tv_team_name.getText().toString());
                    startActivity(teamAttendanceIntent);
                    break;
            }
        }
    }

    private void loadTeamDetailFromBackend(int id) {

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
        if (!TextUtils.isEmpty(tv_team_score.getText())) {
            personalScore = Integer.valueOf(tv_team_score.getText().toString());
        }

        int attendanceCount = 0;
        if (!TextUtils.isEmpty(tv_team_play_times.getText())) {
            attendanceCount = Integer.valueOf(tv_team_play_times.getText().toString());
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
