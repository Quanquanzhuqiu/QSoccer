package com.qqzq.subitem.game.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/15.
 */
public class GamePublishActivity extends BaseActivity {

    private Context context = this;
    private TextView tv_titile;
    private TextView tv_commit;
    private EditText edt_game_name;
    private EditText edt_game_location;
    private EditText edt_game_date;
    private EditText edt_game_time;
    private RadioButton rbtn_game_type_public;
    private RadioButton rbtn_game_type_private;
    private RadioButton rbtn_game_court_type_5;
    private RadioButton rbtn_game_court_type_7;
    private RadioButton rbtn_game_court_type_9;
    private RadioButton rbtn_game_court_type_11;
    private CheckBox cbox_eat_and_play;
    private RadioButton rbtn_game_cost_average;
    private RadioButton rbtn_game_cost_member_charge;
    private RadioButton rbtn_game_cost_member_no_registrator;
    private RadioButton rbtn_game_cost_member_link_registrator;
    private EditText edt_game_registrator_upper_limit;
    private EditText edt_game_registrator_lower_limit;
    private EditText edt_game_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_publish);
        init();
    }

    private void init() {
        tv_titile = (TextView) findViewById(R.id.tv_title);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_titile.setText("发起活动");
        tv_commit.setText("发布");
        edt_game_name = (EditText) findViewById(R.id.edt_game_name);
        edt_game_location = (EditText) findViewById(R.id.edt_game_location);
        edt_game_date = (EditText) findViewById(R.id.edt_game_date);
        edt_game_time = (EditText) findViewById(R.id.edt_game_time);
        rbtn_game_type_public = (RadioButton) findViewById(R.id.rbtn_game_type_public);
        rbtn_game_type_private = (RadioButton) findViewById(R.id.rbtn_game_type_private);
        rbtn_game_court_type_5 = (RadioButton) findViewById(R.id.rbtn_game_court_type_5);
        rbtn_game_court_type_7 = (RadioButton) findViewById(R.id.rbtn_game_court_type_7);
        rbtn_game_court_type_9 = (RadioButton) findViewById(R.id.rbtn_game_court_type_9);
        rbtn_game_court_type_11 = (RadioButton) findViewById(R.id.rbtn_game_court_type_11);
        cbox_eat_and_play = (CheckBox) findViewById(R.id.cbox_eat_and_play);
        rbtn_game_cost_average = (RadioButton) findViewById(R.id.rbtn_game_cost_average);
        rbtn_game_cost_member_charge = (RadioButton) findViewById(R.id.rbtn_game_cost_member_charge);
        rbtn_game_cost_member_no_registrator = (RadioButton) findViewById(R.id.rbtn_game_cost_member_no_registrator);
        rbtn_game_cost_member_link_registrator = (RadioButton) findViewById(R.id.rbtn_game_cost_member_link_registrator);
        edt_game_registrator_upper_limit = (EditText) findViewById(R.id.edt_game_registrator_upper_limit);
        edt_game_registrator_lower_limit = (EditText) findViewById(R.id.edt_game_registrator_lower_limit);
        edt_game_description = (EditText) findViewById(R.id.edt_game_description);

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formCheck()) {
                    commitToBackend();
                }
            }
        });
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
        Map<String, Object> mParameters = new HashMap<>();

        String gameName = edt_game_name.getText().toString();
        String gameLocation = edt_game_location.getText().toString();
        String gameDateStr = edt_game_date.getText().toString();
        String gameTimeStr = edt_game_time.getText().toString();
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
        } else if (rbtn_game_cost_member_charge.isChecked()) {
            gameCostType = 2;
        }

        boolean relatelist = true;
        if (rbtn_game_cost_member_no_registrator.isChecked()) {
            relatelist = false;
        } else if (rbtn_game_cost_member_link_registrator.isChecked()) {
            relatelist = true;
        }

        int personMaxLimit = -1;
        if (TextUtils.isEmpty(edt_game_registrator_upper_limit.getText())) {
            personMaxLimit = Integer.parseInt(edt_game_registrator_upper_limit.getText().toString());
        }

        int personMinLimit = -1;
        if (TextUtils.isEmpty(edt_game_registrator_lower_limit.getText())) {
            personMinLimit = Integer.parseInt(edt_game_registrator_lower_limit.getText().toString());
        }

        String gameDescription = edt_game_description.getText().toString();

        EntGameInfo entGameInfo = new EntGameInfo();
        entGameInfo.setActtitle(gameName);
        entGameInfo.setActaddress(gameLocation);
        entGameInfo.setActdatetime(new Date());
        entGameInfo.setActdate(new Date());
        entGameInfo.setActtime(new Date());
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

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entGameInfo);
        return mParameters;
    }

    ResponseListener publishGameResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            System.out.println(volleyError);
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            System.out.println("发起活动成功");
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    };
}
