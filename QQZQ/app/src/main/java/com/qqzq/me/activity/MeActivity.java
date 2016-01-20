package com.qqzq.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.listener.TopBarListener;
import com.qqzq.me.dto.EntUserInfoDTO;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.widget.menu.TopBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class MeActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "MeActivity";

    private Activity context = this;

    @Bind(R.id.ll_main)
    LinearLayout mMainLinearLayout;
    private Button[] mTabs;
    @Bind(R.id.tv_user_name)
     TextView mUserNameTextView;
    @Bind(R.id.tv_qqzq_id)
    TextView mQqzqIdTextView;
    @Bind(R.id.iv_more_info)
    ImageView mMoreInfoImageView;
    @Bind(R.id.ll_my_info)
    LinearLayout mMyInfoLinearLayout;
    @Bind(R.id.topbar)
    TopBar topBar;

    private EntUserInfoDTO entUserInfoDTO;
    private TopBar mTopbarTopBar;
    @Bind(R.id.ll_my_wallet)
    LinearLayout mMyWalletLinearLayout;
    @Bind(R.id.ll_my_set)
    LinearLayout mMySetLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initView() {


        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_my_team);

        mTabs[1] = (Button) findViewById(R.id.btn_chat);
        mTabs[2] = (Button) findViewById(R.id.btn_find);
        mTabs[3] = (Button) findViewById(R.id.btn_me);
        // 把第一个tab设为选中状态
        mTabs[3].setSelected(true);
    }

    private void initData() {
        loadUserInfoFromBackend();
    }

    private void initListener() {
        mMyInfoLinearLayout.setOnClickListener(this);
        mMyWalletLinearLayout.setOnClickListener(this);
        mMySetLinearLayout.setOnClickListener(this);
        topBar.setListener(new TopBarListener() {

            @Override
            public void leftButtonClick() {
            }

            @Override
            public void rightButtonClick() {
                Intent intent = new Intent(context, MePersonalSettingActivity.class);
                startActivity(intent);
            }

            @Override
            public int getButtonType() {
                return TopBarListener.RIGHT;
            }
        });
    }

    private void initForm(EntUserInfoDTO entUserInfoDTO) {
        String name = "";
        if (TextUtils.isEmpty(entUserInfoDTO.getNickname())) {
            name = entUserInfoDTO.getUsername();
        } else {
            name = entUserInfoDTO.getNickname();
        }
        mUserNameTextView.setText(name);
        mQqzqIdTextView.setText(entUserInfoDTO.getQuanquanid());
    }

    private void loadUserInfoFromBackend() {
        String queryUrl = Constants.API_GET_ME_INFO_URL;
        GsonRequest gsonRequest = new GsonRequest<EntUserInfoDTO>(queryUrl, EntUserInfoDTO.class,
                new ResponseListener<EntUserInfoDTO>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(EntUserInfoDTO _entUserInfoDTO) {
                        if (_entUserInfoDTO != null) {
                            initForm(_entUserInfoDTO);
                            entUserInfoDTO = _entUserInfoDTO;
                        }
                    }
                });
        executeRequest(gsonRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_info:
                Intent intent = new Intent(context, MeCardActivity.class);
                intent.putExtra(Constants.EXTRA_USER_INFO, entUserInfoDTO);
                startActivity(intent);
                break;
            case R.id.ll_my_wallet:
                Intent myWalletIntent = new Intent(context, MeWalletActivity.class);
                startActivity(myWalletIntent);
                break;
            case R.id.ll_my_set:
                Intent setIntent = new Intent(context,MeCommonSettingActivity.class);
                startActivity(setIntent);
                break;
        }
    }
}
