package com.qqzq.subitem.find.activity;

import android.content.Context;
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
import com.qqzq.subitem.team.adapter.TeamListViewAdapter;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.EntTeamListItem;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;
import com.qqzq.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class FindTeamActivity extends BaseActivity {
    private EditText et_search;
    private LinearLayout layout_default_find_team;
    private ListView lv_teams;
    private PullToRefreshView pullToRefreshListView;
    private ImageView iv_team_detail;
    private List<EntTeamListItem> teamList = new ArrayList<>();
    private TeamListViewAdapter listViewAdapter;
    private Context context = this;

    private ImageView iv_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_team);
        init();
    }

    private void init() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("查找球队");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        layout_default_find_team = (LinearLayout) findViewById(R.id.layout_default_find_team);
        pullToRefreshListView = (PullToRefreshView) findViewById(R.id.pull_refresh_listview);
        lv_teams = (ListView) findViewById(R.id.lv_teams);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_team_detail = (ImageView) findViewById(R.id.iv_team_detail);

        listViewAdapter = new TeamListViewAdapter(context, teamList);
        lv_teams.setAdapter(listViewAdapter);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindTeamActivity.this.finish();
            }
        });

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(context, "开始查找", Toast.LENGTH_LONG).show();

                    if (listViewAdapter != null) {
                        listViewAdapter.mList.clear();
                    }

                    Map<String, Object> mParameters = new HashMap<>();
                    mParameters.put("offset", 0);
                    mParameters.put("limit", Constants.PAGE_SIZE);
                    mParameters.put("teamLeaderUsrNm", v.getText().toString());
                    loadTeamListFromBackend(mParameters);
                    return true;
                }
                return false;
            }

        });

        layout_default_find_team.setOnClickListener(new View.OnClickListener()

                                                    {
                                                        @Override
                                                        public void onClick(View v) {
                                                            et_search.setVisibility(View.VISIBLE);
                                                        }
                                                    }

        );

        pullToRefreshListView.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener()

                                                         {
                                                             @Override
                                                             public void onHeaderRefresh(PullToRefreshView view) {

                                                                 if (listViewAdapter != null) {
                                                                     listViewAdapter.mList.clear();
                                                                 }

                                                                 Map<String, Object> mParameters = new HashMap<>();
                                                                 mParameters.put("offset", 0);
                                                                 mParameters.put("limit", Constants.PAGE_SIZE);
                                                                 loadTeamListFromBackend(mParameters);
                                                             }
                                                         }

        );

        pullToRefreshListView.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener()

                                                         {
                                                             @Override
                                                             public void onFooterRefresh(PullToRefreshView view) {
                                                                 Map<String, Object> mParameters = new HashMap<>();
                                                                 mParameters.put("offset", listViewAdapter.pageIdx);
                                                                 mParameters.put("limit", Constants.PAGE_SIZE);
                                                                 loadTeamListFromBackend(mParameters);
                                                             }
                                                         }

        );

        lv_teams.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                        {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Toast.makeText(context, "选中第" + position + "个球队", Toast.LENGTH_LONG).show();
                                            }
                                        }

        );

        Map<String, Object> mParameters = new HashMap<>();
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
            if (pullToRefreshListView != null) {
                pullToRefreshListView.onHeaderRefreshComplete();
                pullToRefreshListView.onFooterRefreshComplete();
            }
            System.out.println(new String(volleyError.networkResponse.data));
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


}
