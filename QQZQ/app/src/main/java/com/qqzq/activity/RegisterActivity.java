package com.qqzq.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntRegisterInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by jie.xiao on 9/8/2015.
 */
public class RegisterActivity extends BaseActivity {

    private Context context = this;
    private TextView tv_title;
    private ImageView iv_back;
    private EditText edt_select_location;
    private EditText edt_phone_no;
    private EditText edt_verify_code;
    private EditText edt_password;
    private EditText edt_password_retype;
    private CheckBox cbox_qqzq_agreement;
    private TextView tv_qqzq_agreement;
    private Button btn_verify_code;
    private Button btn_register;

    private Bundle extras;
    private int selectedProvinceCode = 0;
    private int selectedCityCode = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("注册");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_qqzq_agreement = (TextView) findViewById(R.id.tv_qqzq_agreement);
        edt_select_location = (EditText) findViewById(R.id.edt_select_location);
        edt_phone_no = (EditText) findViewById(R.id.edt_phone_no);
        edt_verify_code = (EditText) findViewById(R.id.edt_verify_code);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password_retype = (EditText) findViewById(R.id.edt_password_retype);
        cbox_qqzq_agreement = (CheckBox) findViewById(R.id.cbox_qqzq_agreement);
        btn_verify_code = (Button) findViewById(R.id.btn_verify_code);
        btn_register = (Button) findViewById(R.id.btn_register);

        //注册短信回调
        SMSSDK.registerEventHandler(eh);
        initSpanableString();

        extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_LOCATION)
                && extras.containsKey(Constants.EXTRA_SELECTED_PROVINCE_CODE)
                && extras.containsKey(Constants.EXTRA_SELECTED_CITY_CODE)) {

            selectedProvinceCode = extras.getInt(Constants.EXTRA_SELECTED_PROVINCE_CODE);
            selectedCityCode = extras.getInt(Constants.EXTRA_SELECTED_CITY_CODE);

            String selectedLocation = extras.getString(Constants.EXTRA_SELECTED_LOCATION);
            edt_select_location.setText(selectedLocation);
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });

        edt_select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectLocationActivity.class);
                intent.putExtra(Constants.EXTRA_PREV_PAGE_NAME, "RegisterActivity");
                startActivity(intent);
            }
        });

        btn_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_phone_no != null && !TextUtils.isEmpty(edt_phone_no.getText())) {

                    Toast.makeText(context, "验证码将通过短信发送到你的手机，请注意查收！", Toast.LENGTH_LONG).show();

                    sendSMS(edt_phone_no.getText().toString());
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void sendSMS(String phoneNo) {
        SMSSDK.getVerificationCode(Constants.CHINA_MOBLIE_NO, "13688390128");
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
                EntRegisterInfo.class, null, mParameters, registerUserResponseListener);

        executeRequest(gsonRequest);
    }

    private Map<String, Object> prepareRequestJson() {

        Map<String, Object> mParameters = new HashMap<>();

        EntRegisterInfo entRegisterInfo = new EntRegisterInfo();
        entRegisterInfo.setProvince(selectedProvinceCode);
        entRegisterInfo.setCity(selectedCityCode);
        entRegisterInfo.setUsername(edt_phone_no.getText().toString());
        entRegisterInfo.setPassword(edt_password.getText().toString());
        entRegisterInfo.setVerifyCode(edt_verify_code.getText().toString());

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entRegisterInfo);
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

    ResponseListener registerUserResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            System.out.println(volleyError);
        }

        @Override
        public void onResponse(EntClientResponse entClientResponse) {
            System.out.println("注册成功");
            jumpPage();
        }
    };
}