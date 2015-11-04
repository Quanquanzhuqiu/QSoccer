package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;

import java.text.MessageFormat;

/**
 * Created by jie.xiao on 11/4/2015.
 */
public class MemberFeeEditListActivity extends BaseActivity {
    private final static String TAG = "MemberFeeEditListActivity";
    private Activity context = this;

    private String selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_fee_edit_list);

//        initView();
//        initListener();
        initData();
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
        }

        reqeustTeamMemberList(selectedTeamId);
    }

    private void reqeustTeamMemberList(String id) {
        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_MEMBER_BY_ID_URL, id);
        System.out.println(queryUrl);
        GsonRequest gsonRequest = new GsonRequest<EntTeamMember[]>(queryUrl, EntTeamMember[].class,
                new ResponseListener<EntTeamMember[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        showLongToast(context, volleyError.toString());
                    }

                    @Override
                    public void onResponse(EntTeamMember[] entTeamMembers) {
//                        refreshTeamMemberListView(entTeamMembers);
                    }
                });
        executeRequest(gsonRequest);
    }
}
