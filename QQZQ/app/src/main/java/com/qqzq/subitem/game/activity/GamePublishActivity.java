package com.qqzq.subitem.game.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.team.activity.SelectTeamActivity;
import com.qqzq.widget.time.TimePickerWindow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/15.
 */
public class GamePublishActivity extends BaseActivity implements View.OnClickListener {

    private Activity context = this;
    private TextView tv_titile;
    private LinearLayout ll_commit;
    private TextView tv_commit;
    private LinearLayout ll_back;
    private EditText edt_game_name;
    private EditText edt_game_location;
    private EditText edt_game_date;
    private RadioButton rbtn_game_type_public;
    private RadioButton rbtn_game_type_private;
    private RadioButton rbtn_game_court_type_5;
    private RadioButton rbtn_game_court_type_7;
    private RadioButton rbtn_game_court_type_9;
    private RadioButton rbtn_game_court_type_11;
    private CheckBox cbox_eat_and_play;
    private RadioButton rbtn_game_cost_average;
    private RadioButton rbtn_game_cost_fixed;
    private RadioButton rbtn_game_cost_member_charge;
    private RadioButton rbtn_game_cost_member_no_registrator;
    private RadioButton rbtn_game_cost_member_link_registrator;
    private EditText edt_game_registrator_upper_limit;
    private EditText edt_game_registrator_lower_limit;
    private EditText edt_game_description;
    private TextView tv_select_team;
    private TextView tv_selected_team;
    private LinearLayout ll_game_publish;
    private LinearLayout ll_select_team;
    private LinearLayout ll_game_cost_member_no_registrator;
    private LinearLayout ll_game_cost_member_link_registrator;

    private TimePickerWindow timePickerWindow; // 时间选择器窗口

    private String selectedTeamName;
    private String selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_publish);
        initView();
        initLinstener();
        initData();

        initTestData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timePickerWindow != null && timePickerWindow.isShowing()) {
            timePickerWindow.dismiss();
        }
    }

    private void initView() {
        tv_titile = (TextView) findViewById(R.id.tv_title);
        tv_titile.setText("发起活动");
        ll_commit = (LinearLayout) findViewById(R.id.ll_commit);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setText("发布");
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        edt_game_name = (EditText) findViewById(R.id.edt_game_name);
        edt_game_location = (EditText) findViewById(R.id.edt_game_location);
        edt_game_date = (EditText) findViewById(R.id.edt_game_date);
        rbtn_game_type_public = (RadioButton) findViewById(R.id.rbtn_game_type_public);
        rbtn_game_type_private = (RadioButton) findViewById(R.id.rbtn_game_type_private);
        rbtn_game_court_type_5 = (RadioButton) findViewById(R.id.rbtn_game_court_type_5);
        rbtn_game_court_type_7 = (RadioButton) findViewById(R.id.rbtn_game_court_type_7);
        rbtn_game_court_type_9 = (RadioButton) findViewById(R.id.rbtn_game_court_type_9);
        rbtn_game_court_type_11 = (RadioButton) findViewById(R.id.rbtn_game_court_type_11);
        cbox_eat_and_play = (CheckBox) findViewById(R.id.cbox_eat_and_play);
        rbtn_game_cost_fixed = (RadioButton) findViewById(R.id.rbtn_game_cost_fixed);
        rbtn_game_cost_average = (RadioButton) findViewById(R.id.rbtn_game_cost_average);
        rbtn_game_cost_member_charge = (RadioButton) findViewById(R.id.rbtn_game_cost_member_charge);
        rbtn_game_cost_member_no_registrator = (RadioButton) findViewById(R.id.rbtn_game_cost_member_no_registrator);
        rbtn_game_cost_member_link_registrator = (RadioButton) findViewById(R.id.rbtn_game_cost_member_link_registrator);
        edt_game_registrator_upper_limit = (EditText) findViewById(R.id.edt_game_registrator_upper_limit);
        edt_game_registrator_lower_limit = (EditText) findViewById(R.id.edt_game_registrator_lower_limit);
        edt_game_description = (EditText) findViewById(R.id.edt_game_description);
        tv_select_team = (TextView) findViewById(R.id.tv_select_team);
        tv_selected_team = (TextView) findViewById(R.id.tv_selected_team);
        ll_game_publish = (LinearLayout) findViewById(R.id.ll_game_publish);
        ll_select_team = (LinearLayout) findViewById(R.id.ll_select_team);
        ll_game_cost_member_no_registrator = (LinearLayout) findViewById(R.id.ll_game_cost_member_no_registrator);
        ll_game_cost_member_link_registrator = (LinearLayout) findViewById(R.id.ll_game_cost_member_link_registrator);

        timePickerWindow = new TimePickerWindow(GamePublishActivity.this, null);
        timePickerWindow.dismiss();
    }

    private void initLinstener() {

        // 初始化控件监听器
        ll_commit.setOnClickListener(this);
        ll_back.setOnClickListener(new BackButtonListener(this));
        tv_select_team.setOnClickListener(this);
        edt_game_date.setOnClickListener(this);
        edt_game_location.setOnClickListener(this);
        rbtn_game_type_private.setOnClickListener(this);
        rbtn_game_type_public.setOnClickListener(this);
        rbtn_game_cost_member_charge.setOnClickListener(this);
        rbtn_game_cost_average.setOnClickListener(this);
        rbtn_game_cost_fixed.setOnClickListener(this);
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
                System.out.println("选中的球队是:" + selectedTeamName + " " + selectedTeamId);
                ll_select_team.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_commit:
                if (formCheck()) {
                    commitToBackend();
                }
                break;
            case R.id.tv_select_team:
                Intent selectTeamIntent = new Intent(context, SelectTeamActivity.class);
                selectTeamIntent.putExtra(Constants.EXTRA_PREV_PAGE_NAME, context.getClass().getSimpleName());
                startActivity(selectTeamIntent);
                break;
            case R.id.edt_game_location:
                break;
            case R.id.rbtn_game_type_public:
                rbtn_game_type_private.setChecked(false);
                break;
            case R.id.rbtn_game_type_private:
                selectedTeamId = "";
                selectedTeamName = "";
                rbtn_game_type_public.setChecked(false);
                ll_select_team.setVisibility(View.GONE);
                break;
            case R.id.rbtn_game_cost_member_charge:
                rbtn_game_cost_average.setChecked(false);
                rbtn_game_cost_fixed.setChecked(false);
                ll_game_cost_member_no_registrator.setVisibility(View.VISIBLE);
                ll_game_cost_member_link_registrator.setVisibility(View.VISIBLE);
                break;
            case R.id.rbtn_game_cost_average:
                rbtn_game_cost_member_charge.setChecked(false);
                rbtn_game_cost_fixed.setChecked(false);
                ll_game_cost_member_no_registrator.setVisibility(View.GONE);
                ll_game_cost_member_link_registrator.setVisibility(View.GONE);
                break;
            case R.id.rbtn_game_cost_fixed:
                rbtn_game_cost_member_charge.setChecked(false);
                rbtn_game_cost_average.setChecked(false);
                ll_game_cost_member_no_registrator.setVisibility(View.GONE);
                ll_game_cost_member_link_registrator.setVisibility(View.GONE);
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

    private boolean formCheck() {
        return true;
    }

    private void commitToBackend() {
        Map<String, Object> mParameters = prepareRequestJson();

        if (mParameters == null || mParameters.isEmpty()) {
            return;
        }

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, Constants.API_GAME_PUBLISH_URL,
                EntGameInfo.class, null, mParameters, publishGameResponseListener);

        executeRequest(gsonRequest);
    }

    public Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();

        String gameName = edt_game_name.getText().toString();
        String gameLocation = edt_game_location.getText().toString();
        Date gameDate = timePickerWindow.getSelectedTime();
        int gameType = -1;
        if (rbtn_game_type_private.isChecked()) {
            gameType = 0;
        } else if (rbtn_game_type_public.isChecked()) {
            gameType = 1;
        }

        int soccerpersonnum = -1;
        if (rbtn_game_court_type_5.isChecked()) {
            soccerpersonnum = 5;
        } else if (rbtn_game_court_type_7.isChecked()) {
            soccerpersonnum = 7;
        } else if (rbtn_game_court_type_9.isChecked()) {
            soccerpersonnum = 9;
        } else if (rbtn_game_court_type_11.isChecked()) {
            soccerpersonnum = 11;
        }

        boolean eatAndPlay = (cbox_eat_and_play.isChecked()) ? true : false;

        int gameCostType = -1;
        if (rbtn_game_cost_average.isChecked()) {
            gameCostType = 1;
        } else if (rbtn_game_cost_fixed.isChecked()) {
            gameCostType = 2;
        } else if (rbtn_game_cost_member_charge.isChecked()) {
            gameCostType = 3;
        }

        boolean relatelist = true;
        if (rbtn_game_cost_member_no_registrator.isChecked()) {
            relatelist = false;
        } else if (rbtn_game_cost_member_link_registrator.isChecked()) {
            relatelist = true;
        }

        int personMaxLimit = -1;
        if (!TextUtils.isEmpty(edt_game_registrator_upper_limit.getText())) {
            personMaxLimit = Integer.parseInt(edt_game_registrator_upper_limit.getText().toString());
        }

        int personMinLimit = -1;
        if (!TextUtils.isEmpty(edt_game_registrator_lower_limit.getText())) {
            personMinLimit = Integer.parseInt(edt_game_registrator_lower_limit.getText().toString());
        }

        String gameDescription = edt_game_description.getText().toString();

        EntGameInfo entGameInfo = new EntGameInfo();
        entGameInfo.setActtitle(gameName);
        entGameInfo.setActaddress(gameLocation);
        entGameInfo.setActdatetime(gameDate);
        entGameInfo.setActdate(gameDate);
        entGameInfo.setActtime(gameDate);
        entGameInfo.setPersonmaxlimit(personMaxLimit);
        entGameInfo.setPersonminlimit(personMinLimit);
        entGameInfo.setActtype(gameType);
        entGameInfo.setActpaytype(gameCostType);
        entGameInfo.setIsrelatelist(relatelist);
        entGameInfo.setSoccerpersonnum(soccerpersonnum);
        entGameInfo.setIsdinner(eatAndPlay);
        entGameInfo.setPublisher(BaseApplication.QQZQ_USER);
        entGameInfo.setPublishdate(new Date());
        entGameInfo.setCreatedate(new Date());
        entGameInfo.setUpdatedate(new Date());
        entGameInfo.setStat(gameDescription);

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entGameInfo);
        return mParameters;
    }

    ResponseListener publishGameResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            System.out.println("发起活动成功");
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    };

    private void initTestData() {
        edt_game_name.setText("周末约球");
        edt_game_location.setText("天府四街");
        rbtn_game_type_private.setChecked(true);
        rbtn_game_court_type_7.setChecked(true);
        rbtn_game_cost_fixed.setChecked(true);
        edt_game_registrator_lower_limit.setText("5");
        edt_game_description.setText("周末约球活动简介，与老曼联比赛。");
    }
}
