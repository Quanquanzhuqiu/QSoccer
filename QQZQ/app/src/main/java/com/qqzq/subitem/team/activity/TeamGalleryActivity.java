package com.qqzq.subitem.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.listener.BackButtonListener;

/**
 * Created by jie.xiao on 15/9/27.
 */
public class TeamGalleryActivity extends BaseActivity implements View.OnClickListener {

    private Activity context = this;
    private final String TAG = this.getClass().getSimpleName();

    private LinearLayout ll_back;
    private LinearLayout ll_take_photo;

    private String selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_gallery);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_take_photo = (LinearLayout) findViewById(R.id.ll_take_photo);
    }

    private void initListener() {
        ll_take_photo.setOnClickListener(this);
        ll_back.setOnClickListener(new BackButtonListener(this));
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getString(Constants.EXTRA_SELECTED_TEAM_ID);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_take_photo:
                break;
            default:
                break;
        }
    }

}
