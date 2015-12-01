package com.qqzq.game.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntAttendanceInfo;
import com.qqzq.entity.EntAttendanceUser;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntSignupUser;
import com.qqzq.game.adapter.GameAttendanceManagerListViewAdapter;
import com.qqzq.listener.TopBarListener;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;
import com.qqzq.widget.menu.TopBar;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by jie.xiao on 15/10/25.
 */
public class GameAttendanceDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final static String TAG = "GameAttendanceDetail";
    private Activity context = this;
    private int selectedTeamId, selectedGameId;
    private String selectedGameName, selectedGameDate, selectedTeamName;

    private TopBar topBar;
    private TextView tv_game_name;
    private TextView tv_game_date;
    private ListView lv_game_attendee;
    private List<EntSignupUser> list = new ArrayList<EntSignupUser>();
    private GameAttendanceManagerListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_attendance_detail);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_GAME_ID)
                && extras.containsKey(Constants.EXTRA_SELECTED_GAME_NAME)
                && extras.containsKey(Constants.EXTRA_SELECTED_GAME_DATE)
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_NAME)) {

            selectedGameId = extras.getInt(Constants.EXTRA_SELECTED_GAME_ID);
            selectedGameName = extras.getString(Constants.EXTRA_SELECTED_GAME_NAME);
            selectedGameDate = extras.getString(Constants.EXTRA_SELECTED_GAME_DATE);
            selectedTeamId = extras.getInt(Constants.EXTRA_SELECTED_TEAM_ID);
            selectedTeamName = extras.getString(Constants.EXTRA_SELECTED_TEAM_NAME);

            Log.i(TAG, "selectedGameId = " + selectedGameId);
            Log.i(TAG, "selectedGameName = " + selectedGameName);
            Log.i(TAG, "selectedGameDate = " + selectedGameDate);
            Log.i(TAG, "selectedTeamId = " + selectedTeamId);
            Log.i(TAG, "selectedTeamName = " + selectedTeamName);

            loadSignupUser();
        }
    }

    private void initView() {
        topBar = (TopBar) findViewById(R.id.topbar);
        tv_game_name = (TextView) findViewById(R.id.tv_game_name);
        tv_game_date = (TextView) findViewById(R.id.tv_game_date);
        lv_game_attendee = (ListView) findViewById(R.id.lv_game_attendee);

        tv_game_name.setText(selectedGameName);
        tv_game_date.setText(selectedGameDate);

        adapter = new GameAttendanceManagerListViewAdapter(context, list);
        lv_game_attendee.setAdapter(adapter);
    }

    private void initListener() {
        lv_game_attendee.setOnItemClickListener(this);
        topBar.setListener(new TopBarListener() {

            @Override
            public void leftButtonClick() {
            }

            @Override
            public void rightButtonClick() {
                commitAttendanceInfo();
            }

            @Override
            public int getButtonType() {
                return TopBarListener.RIGHT;
            }
        });
    }

    private void loadSignupUser() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", 10);
        mParameters.put("id", selectedGameId);
        String queryUrl = MessageFormat.format(Constants.API_GAME_SIGNUP_LIST_URL, selectedGameId);
        queryUrl = Utils.makeGetRequestUrl(queryUrl, mParameters);
        GsonRequest gsonRequest = new GsonRequest<EntSignupUser[]>(queryUrl, EntSignupUser[].class,
                findSignupListResponseListener);
        executeRequest(gsonRequest);
    }

    private void commitAttendanceInfo() {
        Map<String, Object> mParameters = prepareRequestJson();
        String url = MessageFormat.format(Constants.API_ATTENDANCE_URL, selectedGameId);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, url,
                EntClientResponse.class, null, mParameters, attendanceResponseListener);

        executeRequest(gsonRequest);
    }

    public Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();

        EntAttendanceInfo entAttendanceInfo = new EntAttendanceInfo();
        Map<Integer, Boolean> selectedMap = adapter.getIsSelected();
        Iterator<Integer> keyIterator = selectedMap.keySet().iterator();
        while (keyIterator.hasNext()) {
            int position = keyIterator.next();
            EntSignupUser entSignupUser = list.get(position);
            EntAttendanceUser entAttendanceUser = new EntAttendanceUser();
            entAttendanceUser.setId(entSignupUser.getId());
            entAttendanceUser.setUserid(entSignupUser.getUserid());
            entAttendanceUser.setUsername(entSignupUser.getUsername());
            entAttendanceUser.setHasattendance(selectedMap.get(position));
            entAttendanceInfo.getAttendances().add(entAttendanceUser);
        }

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entAttendanceInfo);
        return mParameters;
    }

    ResponseListener findSignupListResponseListener = new ResponseListener<EntSignupUser[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntSignupUser[] signupUsers) {
            Log.i(TAG, "获取到报名者" + signupUsers.length);
            list.clear();
            for (EntSignupUser entSignupUser : signupUsers) {
                list.add(entSignupUser);
            }
            adapter.initSelectedMap();
            adapter.notifyDataSetChanged();
        }
    };

    ResponseListener attendanceResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            Log.i(TAG, "出勤信息提交成功.");
            Intent teamAttendanceIntent = new Intent(context, GameAttendanceActivity.class);
            teamAttendanceIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
            teamAttendanceIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_NAME, selectedTeamName);
            startActivity(teamAttendanceIntent);
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "选中的人员是:" + position);
    }

    @Override
    public void onClick(View view) {
        commitAttendanceInfo();
    }
}
