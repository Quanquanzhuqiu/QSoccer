package com.qqzq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntLoginRequest;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class LoginActivity extends BaseActivity {

    private Context context = this;
    private EditText edt_username;
    private EditText edt_password;
    private Button btn_register;
    private Button btn_login;
    private TextView tv_title;
    private ImageView iv_back;

    String username;
    String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("登陆");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        //for test only
        checkMetrics();
        edt_username.setText("13551063785");
        edt_password.setText("123");
    }

    private void login() {
        String checkResult = formCheck();
        if (!TextUtils.isEmpty(checkResult)) {
            Toast.makeText(context, checkResult, Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> mParameters = prepareRequestJson();
        GsonRequest loginRequest = new GsonRequest(Request.Method.POST, Constants.API_USER_LOGIN_URL,
                EntClientResponse.class, null, mParameters, loginResponseListener);
        executeRequest(loginRequest);
    }

    private Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<>();
        username = edt_username.getText().toString();
        password = edt_password.getText().toString();

        EntLoginRequest entLoginRequest = new EntLoginRequest();
        entLoginRequest.setUsername(username);
        entLoginRequest.setPassword(password);
        entLoginRequest.setRememberme(false);
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entLoginRequest);
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
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
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

    ResponseListener<EntClientResponse> loginResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {

            BaseApplication.QQZQ_USER = username;
            BaseApplication.QQZQ_PASSWORD = password;
            jumpPage();
        }
    };
}
