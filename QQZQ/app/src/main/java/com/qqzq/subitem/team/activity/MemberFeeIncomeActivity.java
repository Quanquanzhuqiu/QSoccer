package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.team.adapter.MemberFeeListViewAdapter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 15/11/3.
 */
public class MemberFeeIncomeActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "MemberFeeIncomeActivity";
    public static List<EntTeamMember> list = new ArrayList<EntTeamMember>();
    private Activity context = this;
    private MemberFeeListViewAdapter adapter;
    private ListView lv_member_fee;
    private TextView tv_team_name;
    private TextView tv_team_balance;
    private ImageView iv_add_income;
    private ImageView iv_add_payout;
    private RadioButton rbtn_sponsor;
    private RadioButton rbtn_member_fee;
    private EditText edt_sponsor;
    private EditText edt_fee_description;
    private Button btn_commit;

    private int selectedTeamId;
    private String selectedTeamName;
    private float selectedTeamBalance;
    private boolean isSponsorIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_fee_income);
        initView();
        initData();
        initListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbtn_sponsor:
                rbtn_member_fee.setChecked(false);
                isSponsorIncome = true;
                break;
            case R.id.rbtn_member_fee:
                rbtn_sponsor.setChecked(false);
                isSponsorIncome = false;
                break;
            case R.id.btn_commit:
                commit();
                break;
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
        lv_member_fee = (ListView) findViewById(R.id.lv_member_fee);
        tv_team_name = (TextView) findViewById(R.id.tv_team_name);
        tv_team_balance = (TextView) findViewById(R.id.tv_team_balance);
        iv_add_income = (ImageView) findViewById(R.id.iv_add_income);
        iv_add_payout = (ImageView) findViewById(R.id.iv_add_payout);
        rbtn_sponsor = (RadioButton) findViewById(R.id.rbtn_sponsor);
        rbtn_member_fee = (RadioButton) findViewById(R.id.rbtn_member_fee);
        edt_sponsor = (EditText) findViewById(R.id.edt_sponsor);
        edt_fee_description = (EditText) findViewById(R.id.edt_fee_description);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        rbtn_sponsor.setChecked(true);

        adapter = new MemberFeeListViewAdapter(context, list);
        lv_member_fee.setAdapter(adapter);
    }

    private void initListener() {
        rbtn_sponsor.setOnClickListener(this);
        rbtn_member_fee.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    private void commit() {
        Map<String, Object> mParameters = prepareAddSponsorJson();
        String url = MessageFormat.format(Constants.API_ADD_INCOME_SPONSOR_URL, selectedTeamId);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, url,
                EntClientResponse.class, null, mParameters, addIncomeResponseListener);

        executeRequest(gsonRequest);
    }

    private Map<String, Object> prepareAddSponsorJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();

        EntTeamInfo entTeamInfo = new EntTeamInfo();
        entTeamInfo.setId(selectedTeamId);
        entTeamInfo.setTeambalance(Float.valueOf(edt_sponsor.getText().toString()));
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entTeamInfo);
        return mParameters;
    }

    ResponseListener addIncomeResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            Log.i(TAG, "添加一笔收入成功.");
            context.finish();
        }
    };
}
