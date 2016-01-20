package com.qqzq.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.base.BaseApplication;
import com.qqzq.common.activity.SelectGenderActivity;
import com.qqzq.common.activity.SelectLocationActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.listener.TopBarListener;
import com.qqzq.me.dto.EntUserInfoSettingDTO;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.widget.menu.TopBar;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class MePersonalSettingActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "MePersonalSettingActivity";

    private Activity context = this;
    private TopBar topBar;
    private EditText edt_nickname;
    private EditText edt_qqzq_id;
    private EditText edt_gender;
    private EditText edt_location;
    private EditText edt_best_position;
    private EditText edt_signature;
    private EditText edt_age;

    private int selectedProvinceCode = 0;
    private int selectedCityCode = 0;
    private String selectedLocation;
    private String selectedGender;
    private String selectedGenderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me_setting);
//        initDate();
        initView();
        initListener();
    }

    private void initView() {

        topBar = (TopBar) findViewById(R.id.topbar);
        ImageView infoImageView = (ImageView) findViewById(R.id.iv_more_info);
        edt_nickname = (EditText) findViewById(R.id.edt_nickname);
        edt_qqzq_id = (EditText) findViewById(R.id.edt_qqzq_id);
        edt_gender = (EditText) findViewById(R.id.edt_gender);
        edt_location = (EditText) findViewById(R.id.edt_location);
        edt_best_position = (EditText) findViewById(R.id.edt_best_position);
        edt_signature = (EditText) findViewById(R.id.edt_signature);
        edt_age = (EditText) findViewById(R.id.edt_age);
        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.ll_main);
    }

    private void initListener() {
        edt_location.setOnClickListener(this);
        edt_gender.setOnClickListener(this);
        topBar.setListener(new TopBarListener() {

            @Override
            public void leftButtonClick() {
            }

            @Override
            public void rightButtonClick() {
                commit();
            }

            @Override
            public int getButtonType() {
                return TopBarListener.RIGHT;
            }
        });
    }

    private Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();

        String nickname = edt_nickname.getText().toString();
        String qqzq_id = edt_qqzq_id.getText().toString();
        String gender = selectedGenderId;
        String best_position = edt_best_position.getText().toString();
        String signature = edt_signature.getText().toString();
        String age = edt_age.getText().toString();

        EntUserInfoSettingDTO userInfoSetting = new EntUserInfoSettingDTO();
        userInfoSetting.setUsername(BaseApplication.QQZQ_USER);
        userInfoSetting.setNickname(nickname);
        userInfoSetting.setProvince(selectedProvinceCode);
        userInfoSetting.setCity(selectedCityCode);
        userInfoSetting.setSex(gender);
        userInfoSetting.setArea(best_position);
        userInfoSetting.setAutograph(signature);
        userInfoSetting.setAge(age);

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, userInfoSetting);
        return mParameters;
    }

    private void commit() {
        Map<String, Object> mParameters = prepareRequestJson();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, Constants.API_ME_SETTING_URL,
                EntClientResponse.class, null, mParameters, new ResponseListener<EntClientResponse>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(EntClientResponse response) {
                Log.i(TAG, "设置用户资料成功");
                Intent intent = new Intent(context, MeActivity.class);
                startActivity(intent);
            }
        });

        executeRequest(gsonRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_location:
                Intent intent = new Intent(context, SelectLocationActivity.class);
                intent.putExtra(Constants.EXTRA_PREV_PAGE_NAME, "MePersonalSettingActivity");
                startActivityForResult(intent, 10);
                break;
            case R.id.edt_gender:
                Intent selectedGenderIntent = new Intent(context, SelectGenderActivity.class);
                selectedGenderIntent.putExtra(Constants.EXTRA_PREV_PAGE_NAME, "MePersonalSettingActivity");
                startActivityForResult(selectedGenderIntent, 20);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = null;
            switch (requestCode) {
                //选择地区
                case 10:
                    bundle = data.getExtras();
                    selectedProvinceCode = bundle.getInt(Constants.EXTRA_SELECTED_PROVINCE_CODE);
                    selectedCityCode = bundle.getInt(Constants.EXTRA_SELECTED_CITY_CODE);
                    selectedLocation = bundle.getString(Constants.EXTRA_SELECTED_LOCATION);

                    if (!TextUtils.isEmpty(selectedLocation)) {
                        edt_location.setText(selectedLocation);
                    }
                    break;
                //选择性别
                case 20:
                    bundle = data.getExtras();
                    selectedGender = bundle.getString(Constants.EXTRA_SELECTED_GENDER);
                    selectedGenderId = bundle.getString(Constants.EXTRA_SELECTED_GENDER_ID);
                    if (!TextUtils.isEmpty(selectedGender)) {
                        edt_gender.setText(selectedGender);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
