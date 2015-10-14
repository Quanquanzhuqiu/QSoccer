package com.qqzq.subitem.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntGameInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.game.adapter.GameListViewAdapter;
import com.qqzq.util.Utils;
import com.qqzq.widget.menu.TopBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 15/10/15.
 */
public class GameListActivity extends BaseActivity {

    private Activity context = this;
    private TopBar topBar;
    private ListView lv_games;
    private GameListViewAdapter adapter;
    public static List<EntGameInfo> list = new ArrayList<EntGameInfo>();
    String selectedTeamId;
    String selectedTeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_games);
        initData();
        initView();
    }

    private void initView() {
        topBar = (TopBar) findViewById(R.id.topbar);
        topBar.pageTitle = selectedTeamName;
        topBar.initView();
        lv_games = (ListView) findViewById(R.id.lv_games);
        adapter = new GameListViewAdapter(context, list);
        lv_games.setAdapter(adapter);
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_NAME)) {

            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
            selectedTeamName = extras.getString(Constants.EXTRA_SELECTED_TEAM_NAME);
        }

        loadGameList();
    }

    public void loadGameList() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", 10);
        mParameters.put("teamId", selectedTeamId);
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_GAME_URL, mParameters);
        GsonRequest gsonRequest = new GsonRequest<EntGameInfo[]>(queryUrl, EntGameInfo[].class,
                findGameResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findGameResponseListener = new ResponseListener<EntGameInfo[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntGameInfo[] entGameInfos) {

            for (EntGameInfo entGameInfo : entGameInfos) {
                list.add(entGameInfo);
            }
            adapter.notifyDataSetChanged();
        }
    };
}
