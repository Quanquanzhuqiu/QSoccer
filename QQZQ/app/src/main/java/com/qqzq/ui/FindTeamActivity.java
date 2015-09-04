package com.qqzq.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.adapter.TeamListViewAdapter;
import com.qqzq.common.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.EntTeamListItem;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class FindTeamActivity extends BaseActivity {
    private EditText et_search;
    private LinearLayout layout_default_find_team;
    private ListView lv_team_list;

    private List<EntTeamListItem> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_team);
        init();
    }

    private void init() {
        layout_default_find_team = (LinearLayout) findViewById(R.id.layout_default_find_team);
        lv_team_list = (ListView) findViewById(R.id.lv_team_list);
        et_search = (EditText) findViewById(R.id.et_search);
        layout_default_find_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setVisibility(View.VISIBLE);
            }
        });

        loadTeamListFromBackend();
    }

    public void loadTeamListFromBackend() {

        String queryUrl = Constants.API_FIND_TEAM_URL + "?offset=0&limit=1000";

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
            entTeamListItem.setTeamName(entTeamInfo.getTeamname());
            entTeamListItem.setTeamCaptain(entTeamInfo.getTeamleadernm());
            entTeamListItem.setTeamMembers(entTeamInfo.getOftensoccerpernum());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(entTeamInfo.getEstablishdate());
            String establishDay = calendar.get(Calendar.YEAR)+"年"+calendar.get(Calendar.MONTH)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日";
            entTeamListItem.setTeamEstablishDay(establishDay);
            teamList.add(entTeamListItem);
        }
    }

    public void initTeamListview(){
        lv_team_list.setAdapter(new TeamListViewAdapter(this,teamList));
    }
}
