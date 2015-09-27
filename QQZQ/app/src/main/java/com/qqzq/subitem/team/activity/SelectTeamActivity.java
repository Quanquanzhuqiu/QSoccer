package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.EntTeamListItem;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.game.activity.GamePublishActivity;
import com.qqzq.subitem.team.adapter.TeamListViewAdapter;
import com.qqzq.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/20.
 */
public class SelectTeamActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Activity context = this;
    private TextView tv_title;
    private ListView lv_team;
    private LinearLayout ll_back;
    private TeamListViewAdapter listViewAdapter;
    private List<EntTeamListItem> teamList = new ArrayList<EntTeamListItem>();
    private String prevPageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);

        initView();
        initListener();
        initData();
    }

    private void initView() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        lv_team = (ListView) findViewById(R.id.lv_team);
        tv_title.setText("选择球队");
        ll_back = (LinearLayout) findViewById(R.id.ll_back);

        listViewAdapter = new TeamListViewAdapter(context, teamList);
        listViewAdapter.setHasDetail(false);
        lv_team.setAdapter(listViewAdapter);


    }

    private void initListener() {
        lv_team.setOnItemClickListener(this);
        ll_back.setOnClickListener(this);
    }

    private void initData() {
        Bundle extras = context.getIntent().getExtras();
        if (extras.containsKey(Constants.EXTRA_PREV_PAGE_NAME)) {
            prevPageName = extras.getString(Constants.EXTRA_PREV_PAGE_NAME);
            System.out.println(prevPageName);
        }

        reqeustTeamList();
    }

    private void reqeustTeamList() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", Constants.PAGE_SIZE);
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_TEAM_URL, mParameters);
        System.out.println(queryUrl);
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
            entTeamListItem.setTeamId(entTeamInfo.getTeamno());
            entTeamListItem.setLogoUrl(entTeamInfo.getTeamlogo());
            entTeamListItem.setTeamName(entTeamInfo.getTeamname());
            entTeamListItem.setTeamCaptain(entTeamInfo.getTeamleaderusrrnm());
            entTeamListItem.setTeamMembers(entTeamInfo.getOftensoccerpernum());
            entTeamListItem.setTeamEstablishDay(Utils.getFormatedSimpleDate(entTeamInfo.getEstablishdate()));
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
            refreshTeamListView(entTeamInfos);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!teamList.isEmpty() && !TextUtils.isEmpty(prevPageName)) {

            Intent intent = null;
            if ("GamePublishActivity".equals(prevPageName)) {
                intent = new Intent(context, GamePublishActivity.class);
            }

            if (intent != null) {
                EntTeamListItem entTeamListItem = teamList.get(position);
                String teamName = entTeamListItem.getTeamName();
                String teamId = entTeamListItem.getTeamId();

                intent.putExtra(Constants.EXTRA_SELECTED_TEAM, teamName);
                intent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, teamId);
                startActivity(intent);
            }
        }
    }
}
