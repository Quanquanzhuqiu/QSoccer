package com.qqzq.activity.user;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.dto.EntChangePasswordDTO;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by jie.xiao on 15/10/13.
 */
public class PasswordModifyActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "PasswordModifyActivity";
    private Context context = this;
    private EditText mPhoneNoEditText;
    private EditText mVerifyCodeEditText;
    private Button mVerifyCodeButton;
    private EditText mPasswordEditText;
    private EditText mRetypeEditText;
    private Button mConfirmButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_modify);
        initView();
        initListener();
    }

    private void initView() {
        mPhoneNoEditText = (EditText) findViewById(R.id.edt_phone_no);
        mVerifyCodeEditText = (EditText) findViewById(R.id.edt_verify_code);
        mVerifyCodeButton = (Button) findViewById(R.id.btn_verify_code);
        mPasswordEditText = (EditText) findViewById(R.id.edt_password);
        mRetypeEditText = (EditText) findViewById(R.id.edt_password_retype);
        mConfirmButton = (Button) findViewById(R.id.btn_confirm);
    }

    private void initListener() {
        mVerifyCodeButton.setOnClickListener(this);
        mConfirmButton.setOnClickListener(this);
        //注册短信回调
        SMSSDK.registerEventHandler(eh);
    }

    private void sendSMS(String phoneNo) {
        Log.i(TAG, "phoneNo = " + phoneNo);
        SMSSDK.getVerificationCode(Constants.CHINA_MOBLIE_NO, phoneNo);
    }

    private void changePassword() {
        String checkResult = formCheck();
        if (!TextUtils.isEmpty(checkResult)) {
            Toast.makeText(context, checkResult, Toast.LENGTH_LONG).show();
            return;
        }
        Log.i(TAG, "开始修改密码！");
        Map<String, Object> mParameters = prepareRequestJson();
        GsonRequest loginRequest = new GsonRequest(Request.Method.PUT, Constants.API_USER_MODIFY_PASSWORD_URL,
                EntClientResponse.class, null, mParameters, new ResponseListener<EntClientResponse>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.toString());
                Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(EntClientResponse response) {

            }
        });
        executeRequest(loginRequest);
    }

    private String formCheck() {

        if (TextUtils.isEmpty(mPhoneNoEditText.getText())) {
            return "请输入手机号码";
        }

        if (TextUtils.isEmpty(mVerifyCodeEditText.getText())) {
            return "请输入验证码";
        }

        if (TextUtils.isEmpty(mPasswordEditText.getText()) || TextUtils.isEmpty(mRetypeEditText.getText())) {
            return "请输入密码";
        }

        if (mPasswordEditText.getText().equals(mRetypeEditText.getText())) {
            return "两次输入的密码不一致";
        }

        return null;
    }

    private Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        String verifyCode = mVerifyCodeEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        EntChangePasswordDTO dto = new EntChangePasswordDTO();
        dto.setVerifyCode(verifyCode);
        dto.setPassword(password);
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, dto);
        return mParameters;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verify_code:
                if (mPhoneNoEditText != null && !TextUtils.isEmpty(mPhoneNoEditText.getText())) {
                    Toast.makeText(context, "验证码将通过短信发送到你的手机，请注意查收！", Toast.LENGTH_LONG).show();
                    sendSMS(mPhoneNoEditText.getText().toString());
                }
                break;
            case R.id.btn_confirm:
                changePassword();
                break;
        }
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
                    Log.i(TAG, "获取验证码成功," + result);
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };
}
