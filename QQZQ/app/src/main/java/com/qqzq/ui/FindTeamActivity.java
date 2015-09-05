package com.qqzq.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.adapter.TeamListViewAdapter;
import com.qqzq.common.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.EntTeamListItem;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.adapter.PullableListViewAdapter;
import com.qqzq.view.PullToRefreshLayout;
import com.qqzq.view.PullableListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class FindTeamActivity extends BaseActivity implements PullableListView.OnLoadListener{
    private EditText et_search;
    private LinearLayout layout_default_find_team;
    private PullToRefreshLayout pullToRefreshLayout;
    private PullableListView teamListView;
    private TeamListViewAdapter adapter;

    private List<EntTeamListItem> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_team);
        init();
    }

    private void init() {
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        teamListView = (PullableListView) findViewById(R.id.content_view);
        layout_default_find_team = (LinearLayout) findViewById(R.id.layout_default_find_team);
        et_search = (EditText) findViewById(R.id.et_search);
        layout_default_find_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setVisibility(View.VISIBLE);
            }
        });

        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    et_search.setVisibility(View.GONE);
                    layout_default_find_team.setVisibility(View.VISIBLE);
                } else {

                }

            }
        });

     pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout refreshLayout) {

                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

                // 下拉刷新操作
              new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 5000);
            }
        });

                loadTeamListFromBackend();
//        teamListView.setOnLoadListener(this);
    }

    public void loadTeamListFromBackend() {

        String queryUrl = Constants.API_FIND_TEAM_URL + "?offset=0&limit=6";

        GsonRequest gsonRequest = new GsonRequest<EntTeamInfo[]>(queryUrl, EntTeamInfo[].class,
                new ResponseListener<EntTeamInfo[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println(new String(volleyError.networkResponse.data));
                    }

                    @Override
                    public void onResponse(EntTeamInfo[] entTeamInfos) {
                        initTeamList(entTeamInfos);
                        initTeamListview();
                    }
                });

        executeRequest(gsonRequest);
    }

    public void initTeamList(EntTeamInfo[] entTeamInfos){
        if(entTeamInfos==null){
            return;
        }
        teamList = new ArrayList(entTeamInfos.length);
        for (EntTeamInfo entTeamInfo : entTeamInfos) {
            EntTeamListItem entTeamListItem = new EntTeamListItem();
            entTeamListItem.setLogoUrl(entTeamInfo.getTeamlogo());
            entTeamListItem.setTeamName(entTeamInfo.getTeamname());
            entTeamListItem.setTeamCaptain(entTeamInfo.getTeamleaderusrrnm());
            entTeamListItem.setTeamMembers(entTeamInfo.getOftensoccerpernum());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entTeamInfo.getEstablishdate());
            String establishDay = calendar.get(Calendar.YEAR)+"年"+calendar.get(Calendar.MONTH)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日";
            entTeamListItem.setTeamEstablishDay(establishDay);
            teamList.add(entTeamListItem);
        }
    }

    private void initTeamListview(){
        adapter = new TeamListViewAdapter(this, teamList);
        teamListView.setAdapter(adapter);
/*        teamListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {
                Toast.makeText(
                        FindTeamActivity.this,
                        "LongClick on "
                                + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        teamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(FindTeamActivity.this,
                        " Click on " + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public void onLoad(PullableListView pullableListView) {

        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
//                    adapter.addItem("这里是自动加载进来的item");
                // 千万别忘了告诉控件加载完毕了哦！
                teamListView.finishLoading();
            }
        }.sendEmptyMessageDelayed(0, 5000);
    }
}
