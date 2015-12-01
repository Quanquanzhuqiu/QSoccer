package com.qqzq.team.fragemnt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.base.BaseFragment;
import com.qqzq.config.Constants;
import com.qqzq.team.activity.MemberFeeIncomeActivity;
import com.qqzq.team.activity.MemberFeePayoutActivity;
import com.qqzq.team.adapter.MemberFeeListViewAdapter;
import com.qqzq.team.dto.EntTeamMemberDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xiao on 11/3/2015.
 */
public class MemberBalanceFragment extends BaseFragment implements View.OnClickListener {
    private final static String TAG = "MemberBalanceFragment";
    public static List<EntTeamMemberDTO> list = new ArrayList<EntTeamMemberDTO>();

    private Context context;
    private MemberFeeListViewAdapter adapter;
    private ListView lv_member_fee;
    private TextView tv_team_name;
    private TextView tv_team_balance;
    private ImageView iv_add_income;
    private ImageView iv_add_payout;

    private int selectedTeamId;
    private String selectedTeamName;
    private float selectedTeamBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_fee_manage, container, false);
        context = view.getContext();
        initView(view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null
                && bundle.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)
                && bundle.containsKey(Constants.EXTRA_SELECTED_TEAM_NAME)
                && bundle.containsKey(Constants.EXTRA_SELECTED_TEAM_BALANCE)) {

            selectedTeamId = bundle.getInt(Constants.EXTRA_SELECTED_TEAM_ID);
            selectedTeamName = bundle.getString(Constants.EXTRA_SELECTED_TEAM_NAME);
            selectedTeamBalance = bundle.getFloat(Constants.EXTRA_SELECTED_TEAM_BALANCE);

            Log.i(TAG, "selectedTeamId = " + selectedTeamId);
            Log.i(TAG, "selectedTeamName = " + selectedTeamName);
            Log.i(TAG, "selectedTeamBalance = " + selectedTeamBalance);

            tv_team_name.setText(selectedTeamName);
            tv_team_balance.setText(selectedTeamBalance + "");
        }
    }

    private void initView(View view) {
        lv_member_fee = (ListView) view.findViewById(R.id.lv_member_fee);
        tv_team_name = (TextView) view.findViewById(R.id.tv_team_name);
        tv_team_balance = (TextView) view.findViewById(R.id.tv_team_balance);
        iv_add_income = (ImageView) view.findViewById(R.id.iv_add_income);
        iv_add_payout = (ImageView) view.findViewById(R.id.iv_add_payout);

        adapter = new MemberFeeListViewAdapter(context, list, true);
        lv_member_fee.setAdapter(adapter);
    }

    private void initListener() {
        iv_add_income.setOnClickListener(this);
        iv_add_payout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_income:
                Intent incomeIntent = new Intent(context, MemberFeeIncomeActivity.class);
                incomeIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                incomeIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_NAME, selectedTeamName);
                incomeIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_BALANCE, selectedTeamBalance);
                startActivity(incomeIntent);
                break;
            case R.id.iv_add_payout:
                Intent payoutIntent = new Intent(context, MemberFeePayoutActivity.class);
                payoutIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, selectedTeamId);
                payoutIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_NAME, selectedTeamName);
                payoutIntent.putExtra(Constants.EXTRA_SELECTED_TEAM_BALANCE, selectedTeamBalance);
                startActivity(payoutIntent);
                break;
            default:
                break;
        }
    }
}
