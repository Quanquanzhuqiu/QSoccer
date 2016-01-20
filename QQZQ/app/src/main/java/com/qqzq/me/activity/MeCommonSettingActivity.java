package com.qqzq.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.common.activity.MainActivity;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.listener.TopBarListener;
import com.qqzq.network.ResponseListener;
import com.qqzq.user.activity.LoginActivity;
import com.qqzq.util.ShareReferenceUtil;
import com.qqzq.widget.menu.TopBar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MeCommonSettingActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.btn_logout)
    Button logoutBtn;
    @Bind(R.id.topbar)
    TopBar topBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        logoutBtn.setOnClickListener(this);
        topBar.setListener(new TopBarListener() {
            @Override
            public void leftButtonClick() {
                finish();
            }

            @Override
            public void rightButtonClick() {

            }

            @Override
            public int getButtonType() {
                return 0;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
//                Map<String, Object> mParameters = new HashMap<>();
//                GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, Constants.API_USER_LOGOUT_URL,
//                        EntClientResponse.class, null, mParameters, publishGameResponseListener);
//
//                executeRequest(gsonRequest);
                ShareReferenceUtil.putString(this, ShareReferenceUtil.PASSWORD, "");
                jumpPage();
                this.finish();
                break;
        }
    }
    private void jumpPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    ResponseListener publishGameResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(MeCommonSettingActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            Intent intent = new Intent(MeCommonSettingActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };
}
