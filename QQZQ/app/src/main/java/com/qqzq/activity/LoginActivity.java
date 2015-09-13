package com.qqzq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.common.Constants;
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
    }

    private void login() {
        Map<String, Object> mParameters = prepareRequestJson();

        if (mParameters == null || mParameters.isEmpty()) {
            return;
        }
//        GsonRequest loginRequest = new GsonRequest(Request.Method.POST, Constants.API_USER_LOGIN_URL,
//                EntClientResponse.class, null, mParameters, loginResponseListener);
//        executeRequest(loginRequest);
        jumpPage();
    }

    private Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<>();
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();

        EntLoginRequest entLoginRequest = new EntLoginRequest();
        entLoginRequest.setUsername(username);
        entLoginRequest.setPassword(password);
        entLoginRequest.setRememberme(false);
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entLoginRequest);
        return mParameters;
    }

    private void jumpPage() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    ResponseListener<EntClientResponse> loginResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            System.out.println(new String(volleyError.networkResponse.data));
        }

        @Override
        public void onResponse(EntClientResponse response) {
            jumpPage();
        }
    };
}
