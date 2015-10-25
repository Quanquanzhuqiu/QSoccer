package com.qqzq.subitem.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;


/**
 * Created by jie.xiao on 15/10/25.
 */
public class GameAttendanceDetailActivity extends BaseActivity {

    private final static String TAG = "AttendanceDetail";
    private Activity context = this;
    private String selectedGameId, selectedGameName, selectedGameDate;

    private TextView tv_game_name;
    private TextView tv_game_date;
    private ListView lv_game_attendee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_attendance_detail);
        initData();
        initView();
//        initListener();
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_GAME_ID)
                && extras.containsKey(Constants.EXTRA_SELECTED_GAME_NAME)
                && extras.containsKey(Constants.EXTRA_SELECTED_GAME_DATE)) {

            selectedGameId = extras.getString(Constants.EXTRA_SELECTED_GAME_ID);
            selectedGameName = extras.getString(Constants.EXTRA_SELECTED_GAME_NAME);
            selectedGameDate = extras.getString(Constants.EXTRA_SELECTED_GAME_DATE);

            Log.i(TAG, "selectedGameId = " + selectedGameId);
            Log.i(TAG, "selectedGameName = " + selectedGameName);
            Log.i(TAG, "selectedGameDate = " + selectedGameDate);
        }
    }

    private void initView() {
        tv_game_name = (TextView) findViewById(R.id.tv_game_name);
        tv_game_date = (TextView) findViewById(R.id.tv_game_date);
        lv_game_attendee = (ListView) findViewById(R.id.lv_game_attendee);

        tv_game_name.setText(selectedGameName);
        tv_game_date.setText(selectedGameDate);
    }
}
