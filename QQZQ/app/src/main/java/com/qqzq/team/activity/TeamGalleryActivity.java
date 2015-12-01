package com.qqzq.team.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.config.Constants;

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
    }

    private void initListener() {
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
//            case R.id.ll_take_photo:
//                break;
            default:
                break;
        }
    }

}
