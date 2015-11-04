package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntMemberBalanceInfo;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.listener.TopBarListener;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.team.adapter.MemberFeeEditListViewAdapter;
import com.qqzq.widget.menu.TopBar;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 11/4/2015.
 */
public class MemberFeeEditListActivity extends BaseActivity {
    private final static String TAG = "MemberFeeEdit";
    private Activity context = this;
    private TopBar topBar;
    private ListView lv_team_member;
    private MemberFeeEditListViewAdapter adapter;
    private List<EntTeamMember> mList = new ArrayList<>();
    private int selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_fee_edit_list);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        topBar = (TopBar) findViewById(R.id.topbar);
        lv_team_member = (ListView) findViewById(R.id.lv_team_member);
        adapter = new MemberFeeEditListViewAdapter(context, mList);
        lv_team_member.setAdapter(adapter);
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getInt(Constants.EXTRA_SELECTED_TEAM_ID);
            Log.i(TAG, "selectedTeamId=" + selectedTeamId);
        }

        reqeustTeamMemberList(selectedTeamId);
    }

    private void initListener() {
        topBar.setListener(new TopBarListener() {

            @Override
            public void leftButtonClick() {
            }

            @Override
            public void rightButtonClick() {
                sendEditedMemberFee();
            }

            @Override
            public int getButtonType() {
                return TopBarListener.RIGHT;
            }
        });
    }

    private void reqeustTeamMemberList(int id) {
        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_MEMBER_DETAIL_BY_ID_URL, id);
        System.out.println(queryUrl);
        GsonRequest gsonRequest = new GsonRequest<EntTeamMember[]>(queryUrl, EntTeamMember[].class,
                new ResponseListener<EntTeamMember[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.toString());
                        showLongToast(context, volleyError.toString());
                    }

                    @Override
                    public void onResponse(EntTeamMember[] entTeamMembers) {
                        mList.clear();
                        for (EntTeamMember entTeamMember : entTeamMembers) {
                            mList.add(entTeamMember);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        executeRequest(gsonRequest);
    }

    private void sendEditedMemberFee() {
        EntMemberBalanceInfo entMemberBalanceInfo = new EntMemberBalanceInfo();
        Map<Integer, Float> selectedData = adapter.getSelectedData();
        if (selectedData != null) {
            Iterator<Integer> it = selectedData.keySet().iterator();
            while (it.hasNext()) {
                int memberId = it.next();
                float personalBalance = selectedData.get(memberId);
                EntTeamMember memberBalancesEntity = new EntTeamMember();
                memberBalancesEntity.setId(memberId);
                memberBalancesEntity.setUserid(memberId);
                memberBalancesEntity.setPersonalbalance(personalBalance);
                Log.i(TAG, "会员 " + memberId + " = " + personalBalance);
                entMemberBalanceInfo.getMemberBalances().add(memberBalancesEntity);
            }
        }

        Intent intent = new Intent(context, CreateTeamActivity.class);
        intent.putExtra(Constants.EXTRA_EDITED_MEMBER_FEE, entMemberBalanceInfo);
        setResult(RESULT_OK, intent);
        finish();
    }
}