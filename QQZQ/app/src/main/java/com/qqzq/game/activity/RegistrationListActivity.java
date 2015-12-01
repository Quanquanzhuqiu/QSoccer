package com.qqzq.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.game.adapter.RegistrationListViewAdapter;
import com.qqzq.team.dto.EntTeamMemberDTO;

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
    private List<EntTeamMemberDTO> memberList = new ArrayList<EntTeamMemberDTO>();

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

    private void refreshListView(EntTeamMemberDTO[] entTeamMembers) {
        if (entTeamMembers == null) {
            return;
        }
        memberList.clear();
        for (EntTeamMemberDTO entTeamMember : entTeamMembers) {
            memberList.add(entTeamMember);
        }
        listViewAdapter.notifyDataSetChanged();
    }

    private void reqeustRegistrationList(String id) {
        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_MEMBER_BY_ID_URL, id);
        System.out.println(queryUrl);
        GsonRequest gsonRequest = new GsonRequest<EntTeamMemberDTO[]>(queryUrl, EntTeamMemberDTO[].class,
                new ResponseListener<EntTeamMemberDTO[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        showLongToast(context, volleyError.toString());
                    }

                    @Override
                    public void onResponse(EntTeamMemberDTO[] entTeamMembers) {
                        refreshListView(entTeamMembers);
                    }
                });
        executeRequest(gsonRequest);
    }

}
