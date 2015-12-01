package com.qqzq.activity.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.CreateMemberBalanceRequest;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntMemberBalanceInfo;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.activity.team.adapter.MemberFeeListViewAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 15/11/10.
 */
public class MemberFeePayoutActivity extends BaseActivity implements View.OnClickListener {
    private final static String TAG = "MemberFeePayoutActivity";
    private List<EntTeamMember> list = new ArrayList<EntTeamMember>();
    private Activity context = this;
    private MemberFeeListViewAdapter adapter;
    private TextView tv_team_name;
    private TextView tv_team_balance;
    private TextView tv_team_common_fee_balance;
    private RadioButton rbtn_common_fee_pay;
    private RadioButton rbtn_link_member_list_pay;
    private EditText edt_common_fee_pay;
    private TextView tv_yuan;
    private TextView tv_member_select;
    private ListView lv_member_fee;
    private Button btn_commit;

    private int selectedTeamId;
    private String selectedTeamName;
    private float selectedTeamBalance;
    private boolean isPayUseTeamCommonFee;

    private EntMemberBalanceInfo entMemberBalanceInfo;
    private CreateMemberBalanceRequest createMemberBalanceRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_fee_payout);
        initView();
        initData();
        initListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbtn_common_fee_pay:
                isPayUseTeamCommonFee = true;
                rbtn_link_member_list_pay.setChecked(false);
                edt_common_fee_pay.setVisibility(View.VISIBLE);
                tv_yuan.setVisibility(View.VISIBLE);
                tv_member_select.setVisibility(View.INVISIBLE);
                lv_member_fee.setVisibility(View.INVISIBLE);
                break;
            case R.id.rbtn_link_member_list_pay:
                isPayUseTeamCommonFee = false;
                rbtn_common_fee_pay.setChecked(false);
                edt_common_fee_pay.setVisibility(View.INVISIBLE);
                tv_yuan.setVisibility(View.INVISIBLE);
                tv_member_select.setVisibility(View.VISIBLE);
                lv_member_fee.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_member_select:
                Intent intent = new Intent(context, MemberFeeEditListActivity.class);
                intent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                intent.putExtra(Constants.EXTRA_PAGE_TYPE, "支出名单选择");
                startActivityForResult(intent, 0);
                break;
            case R.id.btn_commit:
                if (isPayUseTeamCommonFee) {
                    commitTeamFee();
                } else {
                    commitMemberFee();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            createMemberBalanceRequest = new CreateMemberBalanceRequest();
            float totalReduction = 0;
            entMemberBalanceInfo = (EntMemberBalanceInfo) bundle.getSerializable(Constants.EXTRA_EDITED_MEMBER_FEE);
            if (entMemberBalanceInfo != null) {
                list.clear();
                for (EntTeamMember memberBalancesEntity : entMemberBalanceInfo.getMemberBalances()) {
                    if (memberBalancesEntity.getPersonalbalance() > 0) {
                        list.add(memberBalancesEntity);

                        CreateMemberBalanceRequest.QqzqUserTeamRel qqzqUserTeamRel = createMemberBalanceRequest.new QqzqUserTeamRel();
                        qqzqUserTeamRel.setId(memberBalancesEntity.getId());
                        qqzqUserTeamRel.setUserid(memberBalancesEntity.getUserid());
                        qqzqUserTeamRel.setPersonalbalance(memberBalancesEntity.getPersonalbalance());
                        createMemberBalanceRequest.getMemberBalances().add(qqzqUserTeamRel);
                        totalReduction += memberBalancesEntity.getPersonalbalance();
                    }
                }
                createMemberBalanceRequest.setTotalReduction(totalReduction);
                adapter.notifyDataSetChanged();
                tv_member_select.setVisibility(View.VISIBLE);
                lv_member_fee.setVisibility(View.VISIBLE);
            }
        }

    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_NAME)
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_BALANCE)) {

            selectedTeamId = extras.getInt(Constants.EXTRA_SELECTED_TEAM_ID);
            selectedTeamName = extras.getString(Constants.EXTRA_SELECTED_TEAM_NAME);
            selectedTeamBalance = extras.getFloat(Constants.EXTRA_SELECTED_TEAM_BALANCE);

            Log.i(TAG, "selectedTeamId = " + selectedTeamId);
            Log.i(TAG, "selectedTeamName = " + selectedTeamName);
            Log.i(TAG, "selectedTeamBalance = " + selectedTeamBalance);

            tv_team_name.setText(selectedTeamName);
            tv_team_balance.setText(selectedTeamBalance + "");
        }
    }

    private void initView() {
        tv_team_name = (TextView) findViewById(R.id.tv_team_name);
        tv_team_balance = (TextView) findViewById(R.id.tv_team_balance);
        tv_team_common_fee_balance = (TextView) findViewById(R.id.tv_team_common_fee_balance);
        rbtn_common_fee_pay = (RadioButton) findViewById(R.id.rbtn_common_fee_pay);
        rbtn_link_member_list_pay = (RadioButton) findViewById(R.id.rbtn_link_member_list_pay);
        edt_common_fee_pay = (EditText) findViewById(R.id.edt_common_fee_pay);
        tv_yuan = (TextView) findViewById(R.id.tv_yuan);
        tv_member_select = (TextView) findViewById(R.id.tv_member_select);
        lv_member_fee = (ListView) findViewById(R.id.lv_member_fee);
        btn_commit = (Button) findViewById(R.id.btn_commit);

        adapter = new MemberFeeListViewAdapter(context, list, false);
        lv_member_fee.setAdapter(adapter);
    }

    private void initListener() {
        rbtn_common_fee_pay.setOnClickListener(this);
        rbtn_link_member_list_pay.setOnClickListener(this);
        tv_member_select.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    private void commitTeamFee() {
        Map<String, Object> mParameters = preparePayUseTeamFeeJson();
        String url = MessageFormat.format(Constants.API_PAY_USE_TEAM_COMMON_FEE_URL, selectedTeamId);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, url,
                EntClientResponse.class, null, mParameters, new ResponseListener<EntClientResponse>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.toString());
                Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(EntClientResponse response) {
                Log.i(TAG, "支出一笔球队公共资金成功.");
                context.finish();
            }
        });

        executeRequest(gsonRequest);
    }

    private Map<String, Object> preparePayUseTeamFeeJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();

        EntTeamInfo entTeamInfo = new EntTeamInfo();
        entTeamInfo.setId(selectedTeamId);
        entTeamInfo.setTeambalance(Float.valueOf(edt_common_fee_pay.getText().toString()));
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entTeamInfo);
        return mParameters;
    }

    private void commitMemberFee() {
        Map<String, Object> mParameters = preparePayUseMemberFeeJson();
        String url = MessageFormat.format(Constants.API_PAY_USE_TEAM_MEMBER_FEE_URL, selectedTeamId);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, url,
                EntClientResponse.class, null, mParameters, new ResponseListener<EntClientResponse>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.toString());
                Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(EntClientResponse response) {
                Log.i(TAG, "支出一笔会费成功.");
                context.finish();
            }
        });

        executeRequest(gsonRequest);
    }

    private Map<String, Object> preparePayUseMemberFeeJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, createMemberBalanceRequest);
        return mParameters;
    }
}
