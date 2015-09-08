package com.qqzq.ui;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.common.Constants;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by jie.xiao on 9/8/2015.
 */
public class RegisterActivity extends BaseActivity {

    private Context context = this;
    private TextView tv_qqzq_agreement;
    private ImageView iv_return;
    private EditText edt_select_location;
    private EditText edt_phone_no;
    private Button btn_verify_code;
    private Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        extras = this.getIntent().getExtras();
        iv_return = (ImageView) findViewById(R.id.iv_return);
        tv_qqzq_agreement = (TextView) findViewById(R.id.tv_qqzq_agreement);
        edt_select_location = (EditText) findViewById(R.id.edt_select_location);
        edt_phone_no = (EditText) findViewById(R.id.edt_phone_no);
        btn_verify_code = (Button) findViewById(R.id.btn_verify_code);

        //注册短信回调
        SMSSDK.registerEventHandler(eh);
        initSpanableString();

        if (extras != null && extras.containsKey(Constants.EXTRA_SELECTED_LOCATION)) {
            String selectedLocation = extras.getString(Constants.EXTRA_SELECTED_LOCATION);
            edt_select_location.setText(selectedLocation);
        }

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        edt_select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectLocationActivity.class);
                startActivity(intent);
            }
        });

        btn_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_phone_no != null && !TextUtils.isEmpty(edt_phone_no.getText())) {

                    new AlertDialog.Builder(context)
                            .setTitle("提示")
                            .setMessage("验证码将通过短信发送到你的手机，请注意查收！")
                            .setPositiveButton("确定", null)
                            .show();

                    sendSMS(edt_phone_no.getText().toString());
                }
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
}