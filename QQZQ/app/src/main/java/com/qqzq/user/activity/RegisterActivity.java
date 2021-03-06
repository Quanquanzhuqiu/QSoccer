package com.qqzq.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.common.activity.SelectLocationActivity;
import com.qqzq.config.Constants;
import com.qqzq.user.dto.EntRegisterDTO;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by jie.xiao on 9/8/2015.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "RegisterActivity";

    private Context context = this;
    private EditText edt_select_location;
    private EditText edt_phone_no;
    private EditText edt_verify_code;
    private EditText edt_password;
    private EditText edt_password_retype;
    private CheckBox cbox_qqzq_agreement;
    private TextView tv_qqzq_agreement;
    private Button btn_verify_code;
    private Button btn_register;

    private int selectedProvinceCode = 0;
    private int selectedCityCode = 0;
    private String selectedLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            selectedProvinceCode = bundle.getInt(Constants.EXTRA_SELECTED_PROVINCE_CODE);
            selectedCityCode = bundle.getInt(Constants.EXTRA_SELECTED_CITY_CODE);
            selectedLocation = bundle.getString(Constants.EXTRA_SELECTED_LOCATION);

            if (!TextUtils.isEmpty(selectedLocation)) {
                edt_select_location.setText(selectedLocation);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_select_location:
                Intent intent = new Intent(context, SelectLocationActivity.class);
                intent.putExtra(Constants.EXTRA_PREV_PAGE_NAME, "RegisterActivity");
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_verify_code:

                if(edt_phone_no == null){
                    return;
                }

                if(TextUtils.isEmpty(edt_phone_no.getText())){
                    Toast.makeText(context,"请输入手机号！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_phone_no.getText().toString().length() != 11 || Utils.isMobile(edt_phone_no.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "验证码将通过短信发送到你的手机，请注意查收！", Toast.LENGTH_LONG).show();
                sendSMS(edt_phone_no.getText().toString());

                break;
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

    private void initView() {
        tv_qqzq_agreement = (TextView) findViewById(R.id.tv_qqzq_agreement);
        edt_select_location = (EditText) findViewById(R.id.edt_select_location);
        edt_phone_no = (EditText) findViewById(R.id.edt_phone_no);
        edt_verify_code = (EditText) findViewById(R.id.edt_verify_code);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password_retype = (EditText) findViewById(R.id.edt_password_retype);
        cbox_qqzq_agreement = (CheckBox) findViewById(R.id.cbox_qqzq_agreement);
        btn_verify_code = (Button) findViewById(R.id.btn_verify_code);
        btn_register = (Button) findViewById(R.id.btn_register);

    }

    private void initListener() {
        edt_select_location.setOnClickListener(this);
        btn_verify_code.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        //注册短信回调
        SMSSDK.registerEventHandler(eh);
    }

    private void initData() {
        initSpanableString();
    }


    private void sendSMS(String phoneNo) {
        Log.i(TAG, "phoneNo = " + phoneNo);
        SMSSDK.getVerificationCode(Constants.CHINA_MOBLIE_NO, phoneNo);
    }

    private void initSpanableString() {
        SpannableString spanableInfo = new SpannableString(context.getResources().getString(R.string.txt_qqzq_agreement));
        int start = 7;
        int end = spanableInfo.length();
        spanableInfo.setSpan(new MyClickableSpan(), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_qqzq_agreement.setText(spanableInfo);
        tv_qqzq_agreement.setMovementMethod(LinkMovementMethod.getInstance());
        tv_qqzq_agreement.setFocusable(false);
        tv_qqzq_agreement.setClickable(false);
        tv_qqzq_agreement.setLongClickable(false);
    }

    private class MyClickableSpan extends ClickableSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false); //去掉下划线
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
    }


    private void registerUser() {
        String checkResult = formCheck();
        if (!TextUtils.isEmpty(checkResult)) {
            Toast.makeText(context, checkResult, Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> mParameters = prepareRequestJson();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, Constants.API_USER_REGISTER_URL,
                EntClientResponse.class, null, mParameters, new ResponseListener<EntClientResponse>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                Log.e(TAG, volleyError.toString());
            }

            @Override
            public void onResponse(EntClientResponse entClientResponse) {
                System.out.println("注册成功");
                jumpPage();
            }
        });

        executeRequest(gsonRequest);
    }

    private Map<String, Object> prepareRequestJson() {

        Map<String, Object> mParameters = new HashMap<String, Object>();

        EntRegisterDTO dto = new EntRegisterDTO();
        dto.setProvince(selectedProvinceCode);
        dto.setCity(selectedCityCode);
        dto.setUsername(edt_phone_no.getText().toString());
        dto.setPassword(edt_password.getText().toString());
        dto.setVerifyCode(edt_verify_code.getText().toString());

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, dto);
        return mParameters;
    }

    private String formCheck() {

        if (selectedProvinceCode == 0
                || selectedCityCode == 0
                || TextUtils.isEmpty(edt_select_location.getText())) {
            return "请输入你的所在地";
        }

        if (TextUtils.isEmpty(edt_phone_no.getText())) {
            return "请输入手机号码";
        }

        if (TextUtils.isEmpty(edt_verify_code.getText())) {
            return "请输入验证码";
        }

        if (TextUtils.isEmpty(edt_password.getText()) || TextUtils.isEmpty(edt_password_retype.getText())) {
            return "请输入密码";
        }

        if (edt_password.getText().equals(edt_password_retype.getText())) {
            return "两次输入的密码不一致";
        }

        if (!cbox_qqzq_agreement.isChecked()) {
            return "请同意圈圈足球协议";
        }

        return "";
    }

    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    System.out.println("result=" + result);
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    private void jumpPage() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }
    public class TimerCount extends CountDownTimer {

        public TimerCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_verify_code.setEnabled(false);
            btn_verify_code.setText(millisUntilFinished / 1000 + "s后重新发送");
        }

        @Override
        public void onFinish() {

            btn_verify_code.setEnabled(true);
            btn_verify_code.setText(getResources().getString(R.string.txt_get_verify_code));
        }
    }

}