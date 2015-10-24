package com.qqzq.subitem.game.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.activity.BaseApplication;
import com.qqzq.activity.MainActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntGameInfo;
import com.qqzq.listener.BackButtonListener;
import com.qqzq.listener.TopBarListener;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.team.activity.SelectTeamActivity;
import com.qqzq.widget.menu.TopBar;
import com.qqzq.widget.time.TimePickerWindow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/15.
 */
public class GamePublishActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private final static String TAG = "GamePublishActivity";

    private Activity context = this;
    private TopBar topBar;
    private TextView tv_titile;
    private LinearLayout ll_commit;
    private TextView tv_commit;
    private LinearLayout ll_back;
    private EditText edt_game_name;
    private EditText edt_game_location;
    private EditText edt_game_date;
    private RadioButton rbtn_game_type_public;
    private RadioButton rbtn_game_type_private;
    private CheckBox cbox_eat_and_play;
    private RadioGroup rgrp_soccer_person;
    private RadioGroup rgrp_game_pay;
    private RadioGroup rgrp_link_member_list;
    private EditText edt_game_pay_average;
    private EditText edt_game_pay_fixed;
    private EditText edt_game_pay_member_charge;
    private EditText edt_game_registrator_upper_limit;
    private EditText edt_game_registrator_lower_limit;
    private EditText edt_game_description;
    private TextView tv_select_team;
    private TextView tv_selected_team;
    private LinearLayout ll_game_publish;
    private LinearLayout ll_select_team;
    private LinearLayout ll_game_pay_member_no_registrator;
    private LinearLayout ll_game_pay_member_link_registrator;
    private LinearLayout ll_link_member_list;

    private TimePickerWindow timePickerWindow; // 时间选择器窗口

    private String selectedTeamName;
    private String selectedTeamId;
    private int selectedSoccerPerson = -1;
    private int selectedPayType = -1;
    private boolean isRelatedMemberList = false;
    private int cost = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_publish);
        initView();
        initLinstener();
        initData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timePickerWindow != null && timePickerWindow.isShowing()) {
            timePickerWindow.dismiss();
        }
    }

    private void initView() {
        topBar = (TopBar) findViewById(R.id.topbar);
        edt_game_name = (EditText) findViewById(R.id.edt_game_name);
        edt_game_location = (EditText) findViewById(R.id.edt_game_location);
        edt_game_date = (EditText) findViewById(R.id.edt_game_date);
        rbtn_game_type_public = (RadioButton) findViewById(R.id.rbtn_game_type_public);
        rbtn_game_type_private = (RadioButton) findViewById(R.id.rbtn_game_type_private);
        rgrp_soccer_person = (RadioGroup) findViewById(R.id.rgrp_soccer_person);
        rgrp_game_pay = (RadioGroup) findViewById(R.id.rgrp_game_pay);
        rgrp_link_member_list = (RadioGroup) findViewById(R.id.rgrp_link_member_list);
        cbox_eat_and_play = (CheckBox) findViewById(R.id.cbox_eat_and_play);
        edt_game_pay_average = (EditText) findViewById(R.id.edt_game_pay_average);
        edt_game_pay_fixed = (EditText) findViewById(R.id.edt_game_pay_fixed);
        edt_game_pay_member_charge = (EditText) findViewById(R.id.edt_game_pay_member_charge);
        edt_game_registrator_upper_limit = (EditText) findViewById(R.id.edt_game_registrator_upper_limit);
        edt_game_registrator_lower_limit = (EditText) findViewById(R.id.edt_game_registrator_lower_limit);
        edt_game_description = (EditText) findViewById(R.id.edt_game_description);
        tv_select_team = (TextView) findViewById(R.id.tv_select_team);
        tv_selected_team = (TextView) findViewById(R.id.tv_selected_team);
        ll_game_publish = (LinearLayout) findViewById(R.id.ll_game_publish);
        ll_select_team = (LinearLayout) findViewById(R.id.ll_select_team);
        ll_link_member_list = (LinearLayout) findViewById(R.id.ll_link_member_list);

        timePickerWindow = new TimePickerWindow(GamePublishActivity.this, null);
        timePickerWindow.dismiss();
    }

    private void initLinstener() {

        // 初始化控件监听器
        tv_select_team.setOnClickListener(this);
        edt_game_date.setOnClickListener(this);
        edt_game_location.setOnClickListener(this);
        rbtn_game_type_private.setOnClickListener(this);
        rbtn_game_type_public.setOnClickListener(this);
        rgrp_soccer_person.setOnCheckedChangeListener(this);
        rgrp_game_pay.setOnCheckedChangeListener(this);
        rgrp_link_member_list.setOnCheckedChangeListener(this);

        topBar.setListener(new TopBarListener() {

            @Override
            public void leftButtonClick() {
            }

            @Override
            public void rightButtonClick() {
                commitToBackend();
            }

            @Override
            public int getButtonType() {
                return TopBarListener.RIGHT;
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        Bundle extras = context.getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(Constants.EXTRA_SELECTED_TEAM)
                    && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {
                selectedTeamName = extras.getString(Constants.EXTRA_SELECTED_TEAM);
                selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
                tv_selected_team.setText(selectedTeamName);
                Log.i(TAG, "选中的球队是:" + selectedTeamName + " " + selectedTeamId);
                rbtn_game_type_private.setChecked(false);
                rbtn_game_type_public.setChecked(true);
                tv_select_team.setVisibility(View.VISIBLE);
                ll_select_team.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        RadioButton selectedRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        Log.i(TAG, "选中的RadioButton是：" + selectedRadioButton.getText());

        switch (radioGroup.getId()) {
            case R.id.rgrp_soccer_person:
                edt_game_pay_average.setEnabled(false);
                edt_game_pay_fixed.setEnabled(false);
                edt_game_pay_member_charge.setEnabled(true);

                String selectedSoccerPersonText = selectedRadioButton.getText().toString().replace("人制", "");
                selectedSoccerPerson = Integer.parseInt(selectedSoccerPersonText);

                break;
            case R.id.rgrp_game_pay:

                edt_game_pay_average.setText("");
                edt_game_pay_fixed.setText("");
                edt_game_pay_member_charge.setText("");
                edt_game_pay_average.setEnabled(false);
                edt_game_pay_fixed.setEnabled(false);
                edt_game_pay_member_charge.setEnabled(false);
                ll_link_member_list.setVisibility(View.GONE);

                if (selectedRadioButton.getText().equals(getString(R.string.game_pay_average))) {
                    edt_game_pay_average.setEnabled(true);
                    edt_game_pay_average.requestFocus();
                    selectedPayType = 1;
                } else if (selectedRadioButton.getText().equals(getString(R.string.game_pay_fixed))) {
                    edt_game_pay_fixed.setEnabled(true);
                    edt_game_pay_fixed.requestFocus();
                    selectedPayType = 2;
                } else if (selectedRadioButton.getText().equals(getString(R.string.game_pay_member_charge))) {
                    edt_game_pay_member_charge.setEnabled(true);
                    edt_game_pay_member_charge.requestFocus();
                    selectedPayType = 3;
                    ll_link_member_list.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.rgrp_link_member_list:
                edt_game_pay_fixed.setEnabled(true);
                edt_game_pay_average.setEnabled(false);
                edt_game_pay_member_charge.setEnabled(false);

                isRelatedMemberList = selectedRadioButton.getText().toString().contains("不关联") ? false : true;

                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_team:
                Intent selectTeamIntent = new Intent(context, SelectTeamActivity.class);
                selectTeamIntent.putExtra(Constants.EXTRA_PREV_PAGE_NAME, context.getClass().getSimpleName());
                startActivity(selectTeamIntent);
                break;
            case R.id.edt_game_location:
                break;
            case R.id.rbtn_game_type_private:
                tv_select_team.setVisibility(View.VISIBLE);
                rbtn_game_type_public.setChecked(false);
                break;
            case R.id.rbtn_game_type_public:
                selectedTeamId = "";
                selectedTeamName = "";
                tv_select_team.setVisibility(View.INVISIBLE);
                ll_select_team.setVisibility(View.GONE);
                rbtn_game_type_private.setChecked(false);
                break;
            case R.id.edt_game_date:
                timePickerWindow.showAtLocation(ll_game_publish,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                timePickerWindow.setEditText(edt_game_date);
                break;
            default:
                break;
        }
    }

    public Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();

        Log.i(TAG, "selectedSoccerPerson = " + selectedSoccerPerson);
        Log.i(TAG, "selectedPayType = " + selectedPayType);
        Log.i(TAG, "isRelatedMemberList = " + isRelatedMemberList);

        String gameName = edt_game_name.getText().toString();
        String gameLocation = edt_game_location.getText().toString();
        Date gameDate = timePickerWindow.getSelectedTime();
        boolean eatAndPlay = (cbox_eat_and_play.isChecked()) ? true : false;
        String gameDescription = edt_game_description.getText().toString();

        int gameType = -1;
        if (rbtn_game_type_private.isChecked()) {
            gameType = 0;
        } else if (rbtn_game_type_public.isChecked()) {
            gameType = 1;
        }


        int personMaxLimit = -1;
        if (!TextUtils.isEmpty(edt_game_registrator_upper_limit.getText())) {
            personMaxLimit = Integer.parseInt(edt_game_registrator_upper_limit.getText().toString());
        }

        int personMinLimit = Integer.parseInt(edt_game_registrator_lower_limit.getText().toString());


        EntGameInfo entGameInfo = new EntGameInfo();
        entGameInfo.setActtitle(gameName);
        entGameInfo.setActaddress(gameLocation);
        entGameInfo.setActdatetime(gameDate);
        entGameInfo.setActdate(gameDate);
        entGameInfo.setActtime(gameDate);
        entGameInfo.setPersonmaxlimit(personMaxLimit);
        entGameInfo.setPersonminlimit(personMinLimit);
        entGameInfo.setActtype(gameType);
        entGameInfo.setActpaytype(selectedPayType);
        entGameInfo.setCost(cost);
        entGameInfo.setIsrelatelist(isRelatedMemberList);
        entGameInfo.setSoccerpersonnum(selectedSoccerPerson);
        entGameInfo.setIsdinner(eatAndPlay);
        entGameInfo.setPublisher(BaseApplication.QQZQ_USER);
        entGameInfo.setPublishdate(new Date());
        entGameInfo.setCreatedate(new Date());
        entGameInfo.setUpdatedate(new Date());
        entGameInfo.setStat(gameDescription);

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entGameInfo);
        return mParameters;
    }

    private String formCheck() {

        if (!TextUtils.isEmpty(edt_game_pay_average.getText())) {
            cost = Integer.parseInt(edt_game_pay_average.getText().toString());
        } else if (!TextUtils.isEmpty(edt_game_pay_fixed.getText())) {
            cost = Integer.parseInt(edt_game_pay_fixed.getText().toString());
        } else if (!TextUtils.isEmpty(edt_game_pay_member_charge.getText())) {
            cost = Integer.parseInt(edt_game_pay_member_charge.getText().toString());
        }

        Log.i(TAG, "selectedPayType = " + selectedPayType);
        Log.i(TAG, "cost = " + cost);


        if (TextUtils.isEmpty(edt_game_name.getText())) {
            return "请输入活动标题";
        }

        if (TextUtils.isEmpty(edt_game_date.getText())) {
            return "请输入活动时间";
        }

        if (rbtn_game_type_private.isChecked() || rbtn_game_type_public.isChecked()) {
        } else {
            return "请选择活动类型";
        }

        if (selectedSoccerPerson < 0) {
            return "请选择场地类型";
        }

        if (selectedPayType < 0) {
            return "请选择活动费用类型";
        }

        if (cost < 0) {
            return "请输入活动费用";
        }

        if (TextUtils.isEmpty(edt_game_registrator_lower_limit.getText())) {
            return "请输入活动人数下限";
        }

        if (TextUtils.isEmpty(edt_game_description.getText())) {
            return "请输入活动的简介";
        }

        return null;
    }

    private void commitToBackend() {

        String checkResult = formCheck();
        if (!TextUtils.isEmpty(checkResult)) {
            Toast.makeText(context, checkResult, Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> mParameters = prepareRequestJson();

        if (mParameters == null || mParameters.isEmpty()) {
            return;
        }

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, Constants.API_GAME_PUBLISH_URL,
                EntClientResponse.class, null, null, publishGameResponseListener);

        executeRequest(gsonRequest);
    }

    ResponseListener publishGameResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    };

}
