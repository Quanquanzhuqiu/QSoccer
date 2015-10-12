package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.listener.BackButtonListener;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.team.adapter.TeamMemberListViewAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jie.xiao on 15/9/27.
 */
public class TeamMemberActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final static String TAG = "TeamMemberActivity";

    private Activity context = this;
    private TextView tv_title;
    private TextView tv_commit;
    private LinearLayout ll_back;
    private ListView lv_team_member;
    private TeamMemberListViewAdapter listViewAdapter;
    private List<EntTeamMember> memberList = new ArrayList<EntTeamMember>();


    private String selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_memeber);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        tv_title.setText("球队成员");
        tv_commit.setText("踢出");

        lv_team_member = (ListView) findViewById(R.id.lv_team_member);
        listViewAdapter = new TeamMemberListViewAdapter(context, memberList);
        lv_team_member.setAdapter(listViewAdapter);
    }

    private void initListener() {
        tv_commit.setOnClickListener(this);
        ll_back.setOnClickListener(new BackButtonListener(this));
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
        }

        reqeustTeamMemberList(selectedTeamId);
    }

    private void refreshTeamMemberListView(EntTeamMember[] entTeamMembers) {
        if (entTeamMembers == null) {
            return;
        }
//        memberList = Arrays.asList(entTeamMembers);
        memberList.clear();
        for (EntTeamMember entTeamMember : entTeamMembers) {
            memberList.add(entTeamMember);
        }
        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                break;
            default:
                break;
        }
    }

    private void reqeustTeamMemberList(String id) {
        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_MEMBER_BY_ID_URL, id);
        System.out.println(queryUrl);
        GsonRequest gsonRequest = new GsonRequest<EntTeamMember[]>(queryUrl, EntTeamMember[].class,
                findTeamMemberResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findTeamMemberResponseListener = new ResponseListener<EntTeamMember[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            showLongToast(context, volleyError.toString());
        }

        @Override
        public void onResponse(EntTeamMember[] entTeamMembers) {
            refreshTeamMemberListView(entTeamMembers);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
