package com.qqzq.subitem.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.game.adapter.RegistrationListViewAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xiao on 15/10/14.
 */
public class RegistrationListActivity extends BaseActivity {

    private final static String TAG = "RegistrationListActivity";
    private Activity context = this;
    private ListView lv_game_registration;
    private RegistrationListViewAdapter listViewAdapter;
    private List<EntTeamMember> memberList = new ArrayList<EntTeamMember>();

    private String selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        initView();
    }

    private void initView() {
        lv_game_registration = (ListView) findViewById(R.id.lv_game_registration);
        listViewAdapter = new RegistrationListViewAdapter(context, memberList);
        lv_game_registration.setAdapter(listViewAdapter);
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
        }
        reqeustRegistrationList(selectedTeamId);
    }

    private void refreshListView(EntTeamMember[] entTeamMembers) {
        if (entTeamMembers == null) {
            return;
        }
        memberList.clear();
        for (EntTeamMember entTeamMember : entTeamMembers) {
            memberList.add(entTeamMember);
        }
        listViewAdapter.notifyDataSetChanged();
    }

    private void reqeustRegistrationList(String id) {
        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_MEMBER_BY_ID_URL, id);
        System.out.println(queryUrl);
        GsonRequest gsonRequest = new GsonRequest<EntTeamMember[]>(queryUrl, EntTeamMember[].class,
                findRegistrationResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findRegistrationResponseListener = new ResponseListener<EntTeamMember[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            showLongToast(context, volleyError.toString());
        }

        @Override
        public void onResponse(EntTeamMember[] entTeamMembers) {
            refreshListView(entTeamMembers);
        }
    };
}
