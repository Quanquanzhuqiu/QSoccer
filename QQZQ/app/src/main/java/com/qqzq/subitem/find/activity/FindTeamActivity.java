package com.qqzq.subitem.find.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.activity.BaseActivity;
import com.qqzq.R;
import com.qqzq.listener.BackButtonListener;
import com.qqzq.activity.team.activity.TeamDetailActivity;
import com.qqzq.activity.team.adapter.TeamListViewAdapter;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamListItem;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;
import com.qqzq.widget.tab.PullToRefreshView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class FindTeamActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener, AdapterView.OnItemClickListener, PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    private Activity context = this;
    private EditText et_search;
    private LinearLayout ll_find_team;
    private ListView lv_teams;
    private PullToRefreshView pullToRefreshListView;
    private ImageView iv_team_detail;
    private List<EntTeamListItem> teamList = new ArrayList<EntTeamListItem>();
    private TeamListViewAdapter listViewAdapter;

    private LinearLayout ll_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_team);
        initView();
        initListener();
        initData();
    }

    private void initView() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("查找球队");
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_find_team = (LinearLayout) findViewById(R.id.ll_find_team);
        pullToRefreshListView = (PullToRefreshView) findViewById(R.id.pull_refresh_listview);
        lv_teams = (ListView) findViewById(R.id.lv_teams);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_team_detail = (ImageView) findViewById(R.id.iv_team_detail);
    }

    private void initListener() {

        listViewAdapter = new TeamListViewAdapter(context, teamList);
        lv_teams.setAdapter(listViewAdapter);

        ll_back.setOnClickListener(new BackButtonListener(this));
        ll_find_team.setOnClickListener(this);
        et_search.setOnEditorActionListener(this);
        pullToRefreshListView.setOnHeaderRefreshListener(this);
        pullToRefreshListView.setOnFooterRefreshListener(this);
        lv_teams.setOnItemClickListener(this);

    }

    private void initData() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", Constants.PAGE_SIZE);

        loadTeamListFromBackend(mParameters);
    }

    public void loadTeamListFromBackend(Map<String, Object> mParameters) {

        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_TEAM_URL, mParameters);
        GsonRequest gsonRequest = new GsonRequest<EntTeamInfo[]>(queryUrl, EntTeamInfo[].class,
                findTeamResponseListener);
        executeRequest(gsonRequest);
    }

    public void refreshTeamListView(EntTeamInfo[] entTeamInfos) {
        if (entTeamInfos == null) {
            return;
        }
        for (EntTeamInfo entTeamInfo : entTeamInfos) {
            EntTeamListItem entTeamListItem = new EntTeamListItem();
            entTeamListItem.setTeamId(String.valueOf(entTeamInfo.getId()));
            entTeamListItem.setLogoUrl(entTeamInfo.getTeamlogo());
            entTeamListItem.setTeamName(entTeamInfo.getTeamname());
            entTeamListItem.setTeamCaptain(entTeamInfo.getTeamleaderusrrnm());
            entTeamListItem.setTeamMembers(entTeamInfo.getOftensoccerpernum());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entTeamInfo.getEstablishdate());
            String establishDay = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
            entTeamListItem.setTeamEstablishDay(establishDay);
            teamList.add(entTeamListItem);
        }

        listViewAdapter.notifyDataSetChanged();
    }

    ResponseListener findTeamResponseListener = new ResponseListener<EntTeamInfo[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntTeamInfo[] entTeamInfos) {
            if (pullToRefreshListView != null) {
                pullToRefreshListView.onHeaderRefreshComplete();
                pullToRefreshListView.onFooterRefreshComplete();
            }
            refreshTeamListView(entTeamInfos);
            listViewAdapter.pageIdx++;
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_find_team:
                et_search.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (teamList != null && teamList.get(position) != null) {
            String teamId = teamList.get(position).getTeamId();
            showLongToast(context, "选中第" + position + "个球队" + teamId);

            Intent intent = new Intent(context, TeamDetailActivity.class);
            intent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, teamList.get(position).getTeamId());
            startActivity(intent);
        }
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        if (listViewAdapter != null) {
            listViewAdapter.mList.clear();
        }

        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", Constants.PAGE_SIZE);
        loadTeamListFromBackend(mParameters);
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", listViewAdapter.pageIdx);
        mParameters.put("limit", Constants.PAGE_SIZE);
        loadTeamListFromBackend(mParameters);
    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            showLongToast(context, "开始查找");

            if (listViewAdapter != null) {
                listViewAdapter.mList.clear();
            }

            Map<String, Object> mParameters = new HashMap<String, Object>();
            mParameters.put("offset", 0);
            mParameters.put("limit", Constants.PAGE_SIZE);
            mParameters.put("teamLeaderUsrNm", textView.getText().toString());
            loadTeamListFromBackend(mParameters);
            return true;
        }
        return false;
    }
}
