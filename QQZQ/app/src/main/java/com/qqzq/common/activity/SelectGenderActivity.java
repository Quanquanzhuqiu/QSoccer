package com.qqzq.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.me.activity.MePersonalSettingActivity;

/**
 * Created by jie.xiao on 15/12/1.
 */
public class SelectGenderActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SelectGenderActivity";
    private Context context = this;
    private TextView mMaleTextView;
    private TextView mFemaleTextView;

    private String prevPageName = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);
        initView();
        initListener();
        initData();
    }

    @Override
    public void onClick(View view) {
        String selectedGender = "";
        String selectedGenderId = "";
        switch (view.getId()) {
            case R.id.tv_male:
                selectedGender = "男";
                selectedGenderId = "0";
                break;
            case R.id.tv_female:
                selectedGender = "女";
                selectedGenderId = "1";
                break;
        }

        Intent intent = null;
        if ("MePersonalSettingActivity".equals(prevPageName)) {
            intent = new Intent(context, MePersonalSettingActivity.class);
        }

        if (intent != null) {
            intent.putExtra(Constants.EXTRA_SELECTED_GENDER, selectedGender);
            intent.putExtra(Constants.EXTRA_SELECTED_GENDER_ID, selectedGenderId);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void initView() {
        mMaleTextView = (TextView) findViewById(R.id.tv_male);
        mFemaleTextView = (TextView) findViewById(R.id.tv_female);
    }

    private void initListener() {
        mMaleTextView.setOnClickListener(this);
        mFemaleTextView.setOnClickListener(this);
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras.containsKey(Constants.EXTRA_PREV_PAGE_NAME)) {
            prevPageName = extras.getString(Constants.EXTRA_PREV_PAGE_NAME);
        }

    }

}
