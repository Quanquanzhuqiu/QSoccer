package com.qqzq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.subitem.find.activity.FindLocationActivity;

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
