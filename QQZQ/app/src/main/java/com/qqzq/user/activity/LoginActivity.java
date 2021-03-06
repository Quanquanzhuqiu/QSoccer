package com.qqzq.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.base.BaseApplication;
import com.qqzq.common.activity.MainActivity;
import com.qqzq.common.activity.MainActivity2;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.user.dto.EntLoginDTO;
import com.qqzq.util.ShareReferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Context context = this;
    private EditText edt_username;
    private EditText edt_password;
    private Button btn_register;
    private Button btn_login;
    private TextView tv_forgot_password;

    String username;
    String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String usrName = ShareReferenceUtil.getStringValue(LoginActivity.this,ShareReferenceUtil.USERNAME);
        String pwd = ShareReferenceUtil.getStringValue(LoginActivity.this,ShareReferenceUtil.PASSWORD);

        //密码不为空 直接到详情页面
        if(!TextUtils.isEmpty(pwd)){
            BaseApplication.QQZQ_USER = usrName;
            BaseApplication.QQZQ_PASSWORD = pwd;
            jumpPage();
            return;
        }

        initView();

        initListener();

        initTestData();
    }

    private void initView() {

        tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        edt_username.setText(ShareReferenceUtil.getStringValue(this,ShareReferenceUtil.USERNAME));
        edt_password.setText(ShareReferenceUtil.getStringValue(this,ShareReferenceUtil.PASSWORD));


    }

    private void initListener() {
        tv_forgot_password.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forgot_password:
                Intent forgotPasswordIntent = new Intent(context, PasswordModifyActivity.class);
                startActivity(forgotPasswordIntent);
                break;
        }
    }

    private void login() {
        String checkResult = formCheck();
        if (!TextUtils.isEmpty(checkResult)) {
            Toast.makeText(context, checkResult, Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> mParameters = prepareRequestJson();
        GsonRequest loginRequest = new GsonRequest(Request.Method.POST, Constants.API_USER_LOGIN_URL,
                EntClientResponse.class, null, mParameters, new ResponseListener<EntClientResponse>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(EntClientResponse response) {

                BaseApplication.QQZQ_USER = username;
                BaseApplication.QQZQ_PASSWORD = password;

                ShareReferenceUtil.putString(LoginActivity.this,ShareReferenceUtil.USERNAME,username);
                ShareReferenceUtil.putString(LoginActivity.this,ShareReferenceUtil.PASSWORD,username);

                jumpPage();
            }
        });
        executeRequest(loginRequest);
    }

    private Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        username = edt_username.getText().toString();
        password = edt_password.getText().toString();

        EntLoginDTO entLoginDto = new EntLoginDTO();
        entLoginDto.setUsername(username);
        entLoginDto.setPassword(password);
        entLoginDto.setRememberme(false);
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entLoginDto);
        return mParameters;
    }

    private String formCheck() {
        if (TextUtils.isEmpty(edt_username.getText())) {
            return "请输入你的用户名";
        }

        if (TextUtils.isEmpty(edt_password.getText())) {
            return "请输入密码";
        }

        return null;
    }

    private void jumpPage() {
        Intent intent = new Intent(context, MainActivity2.class);
        startActivity(intent);
        this.finish();
    }

    private void checkMetrics() {
        // Get the metrics
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;
        float density = metrics.density;
        float scaledDensity = metrics.scaledDensity;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;

        Log.i("LoginActivity", "Screen W x H pixels: " + widthPixels + " x "
                + heightPixels);
        Log.i("LoginActivity", "Screen X x Y dpi: " + xdpi + " x " + ydpi);
        Log.i("LoginActivity", "density = " + density + "  scaledDensity = "
                + scaledDensity + "  densityDpi = " + densityDpi);
    }


    private void initTestData() {
        //for test only
        checkMetrics();
    }
}
