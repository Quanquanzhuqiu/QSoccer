package com.qqzq.common.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.qqzq.me.activity.MeCardActivity;
import com.qqzq.me.activity.MeSettingActivity;
import com.qqzq.me.activity.MeWalletActivity;
import com.qqzq.me.dto.EntUserInfoDTO;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.RequestManager;
import com.qqzq.network.ResponseListener;
import com.qqzq.widget.menu.TopBar;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    private final static String TAG = "MeActivity";

    private Activity context = null;
    private LinearLayout mMainLinearLayout;
    private Button[] mTabs;
    private TextView mUserNameTextView;
    private TextView mQqzqIdTextView;
    private ImageView mMoreInfoImageView;
    private LinearLayout mMyInfoLinearLayout;
    private TopBar topBar;

    private EntUserInfoDTO entUserInfoDTO;
    private TopBar mTopbarTopBar;
    private LinearLayout mMyWalletLinearLayout;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_me);
//
//
//        initData();
//        initView();
//        initListener();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getActivity();
    }

    private View view ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me,container,false);
        initData();
        initView();
        initListener();

        return view;
    }

    private void initView() {
        topBar = (TopBar) view.findViewById(R.id.topbar);
        mUserNameTextView = (TextView) view.findViewById(R.id.tv_user_name);
        mQqzqIdTextView = (TextView) view.findViewById(R.id.tv_qqzq_id);
        mMoreInfoImageView = (ImageView) view.findViewById(R.id.iv_more_info);
        mMyInfoLinearLayout = (LinearLayout) view.findViewById(R.id.ll_my_info);
        mMyWalletLinearLayout = (LinearLayout) view.findViewById(R.id.ll_my_wallet);
        mMainLinearLayout = (LinearLayout) view.findViewById(R.id.ll_main);

    }

    private void initData() {
        loadUserInfoFromBackend();
    }

    private void initListener() {
        mMyInfoLinearLayout.setOnClickListener(this);
        mMyWalletLinearLayout.setOnClickListener(this);
        topBar.setListener(new TopBarListener() {

            @Override
            public void leftButtonClick() {
            }

            @Override
            public void rightButtonClick() {
                Intent intent = new Intent(context, MeSettingActivity.class);
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
        RequestManager.addRequest(gsonRequest, this);
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
        }
    }
}
