package com.qqzq.subitem.team.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.activity.BaseApplication;
import com.qqzq.activity.MainActivity;
import com.qqzq.listener.BackButtonListener;
import com.qqzq.listener.TopBarListener;
import com.qqzq.subitem.find.activity.FindLocationActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.MultipartRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.widget.menu.TopBar;
import com.qqzq.widget.photo.PhotoPopupWindow;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 8/31/2015.
 */
public class CreateTeamActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "CreateTeamActivity";
    private Context context = this;
    private TopBar topBar;
    private EditText edt_team_name;
    private EditText edt_team_location;
    private EditText edt_team_detail;
    private CheckBox cbox_5_persons;
    private CheckBox cbox_7_persons;
    private CheckBox cbox_9_persons;
    private CheckBox cbox_11_persons;
    private RadioGroup radio_group_join_config;
    private LinearLayout ll_team_logo;
    private ImageView iv_logo;
    private View ll_create_team;

    private PhotoPopupWindow photoPopupWindow;

    private Bitmap logo;//Logo Bitmap

    private String logoPathInServer;

    private Bundle extras;
    private String selectedProvinceCode = null;
    private String selectedCityCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        initView();
        initListener();
        initData();
    }

    private void initView() {

        topBar = (TopBar) findViewById(R.id.topbar);
        ll_create_team = findViewById(R.id.ll_create_team);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        edt_team_name = (EditText) findViewById(R.id.edt_team_name);
        edt_team_location = (EditText) findViewById(R.id.edt_team_location);
        edt_team_detail = (EditText) findViewById(R.id.edt_team_detail);
        radio_group_join_config = (RadioGroup) findViewById(R.id.radio_group_join_config);
        cbox_5_persons = (CheckBox) findViewById(R.id.cbox_5_persons);
        cbox_7_persons = (CheckBox) findViewById(R.id.cbox_7_persons);
        cbox_9_persons = (CheckBox) findViewById(R.id.cbox_9_persons);
        cbox_11_persons = (CheckBox) findViewById(R.id.cbox_11_persons);
        ll_team_logo = (LinearLayout) findViewById(R.id.ll_team_logo);
        photoPopupWindow = new PhotoPopupWindow(CreateTeamActivity.this, null);
        photoPopupWindow.dismiss();

    }

    private void initListener() {
        ll_team_logo.setOnClickListener(this);
        edt_team_location.setOnClickListener(this);
        topBar.setListener(new TopBarListener() {

            @Override
            public void leftButtonClick() {
            }

            @Override
            public void rightButtonClick() {
                uploadLogoAndTeamBasicInfo();
            }

            @Override
            public int getButtonType() {
                return TopBarListener.RIGHT;
            }
        });


    }

    private void initData() {
        extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_LOCATION)
                && extras.containsKey(Constants.EXTRA_SELECTED_PROVINCE_CODE)
                && extras.containsKey(Constants.EXTRA_SELECTED_CITY_CODE)) {

            selectedProvinceCode = extras.getString(Constants.EXTRA_SELECTED_PROVINCE_CODE);
            selectedCityCode = extras.getString(Constants.EXTRA_SELECTED_CITY_CODE);

            String selectedLocation = extras.getString(Constants.EXTRA_SELECTED_LOCATION);
            edt_team_location.setText(selectedLocation);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从手机相册选择
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.e("uri", uri.toString());
                    System.out.println("------->" + photoPopupWindow);
                    photoPopupWindow.cropPhoto(uri);//裁剪图片
                }
                break;
            //拍照
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Constants.IMAGE_PHOTO_TMP_PATH
                            + "/logo.png");
                    photoPopupWindow.cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            //照片剪裁后
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    logo = extras.getParcelable("data");
                    if (logo != null) {
                        /**
                         * 上传服务器代码
                         */
                        photoPopupWindow.setPicToView(logo);//保存在SD卡中
                        iv_logo.setImageBitmap(logo);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadLogoAndTeamBasicInfo() {
        File logoFile = new File(
                Constants.IMAGE_PHOTO_TMP_PATH + "logo.jpg");
        if (logoFile.exists()) {
            System.out.println(Constants.API_FILE_UPLOAD_FASTDFS_URL);
            MultipartRequest<EntClientResponse> request = new MultipartRequest(Constants.API_FILE_UPLOAD_FASTDFS_URL,
                    logoFile, EntClientResponse.class, null, uploadLogoResponseListner);

            executeRequest(request);
        }
    }

    private String formCheck() {

        if (selectedProvinceCode == null
                || selectedCityCode == null
                || TextUtils.isEmpty(edt_team_location.getText())) {
            return "请输入球队常活动地点";
        }

        if (TextUtils.isEmpty(edt_team_name.getText())) {
            return "请输入球队名";
        }

        RadioButton radioButton = (RadioButton) findViewById(radio_group_join_config.getCheckedRadioButtonId());
        if (radioButton == null) {
            return "请选择入队验证的方式";
        }

        return null;
    }

    public void commit() {
        String checkResult = formCheck();
        if (!TextUtils.isEmpty(checkResult)) {
            Toast.makeText(context, checkResult, Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, Object> mParameters = prepareRequestJson();
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, Constants.API_CREATE_TEAM_URL,
                EntTeamInfo.class, null, mParameters, createTeamResponseListener);

        executeRequest(gsonRequest);
    }

    public Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        String teamName = edt_team_name.getText().toString();
        String teamDetail = edt_team_detail.getText().toString();

        int join_config = -1;
        for (int i = 0; i < radio_group_join_config.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radio_group_join_config.getChildAt(i);
            if (radioButton.isChecked()) {
                join_config = i;
                break;
            }
        }

        String soccerPersons = null;
        if (cbox_5_persons.isChecked()) {
            soccerPersons = "5";
        } else if (cbox_7_persons.isChecked()) {
            soccerPersons = "7";
        } else if (cbox_9_persons.isChecked()) {
            soccerPersons = "9";
        } else if (cbox_11_persons.isChecked()) {
            soccerPersons = "11";
        }

        EntTeamInfo entTeamInfo = new EntTeamInfo();
        entTeamInfo.setTeamname(teamName);
        entTeamInfo.setTeamno("0");
        entTeamInfo.setOftencity(Integer.valueOf(selectedProvinceCode));
        entTeamInfo.setOftendistinct(Integer.valueOf(selectedCityCode));
        entTeamInfo.setJoinconfig(join_config);
        entTeamInfo.setOftensoccerpernum(soccerPersons);
        entTeamInfo.setSumary(teamDetail);
        entTeamInfo.setEstablishdate(new Date());
        entTeamInfo.setOftencity(0);
        entTeamInfo.setOftendistinct(0);
        entTeamInfo.setTeamleaderusrrnm(BaseApplication.QQZQ_USER);
        if (!TextUtils.isEmpty(logoPathInServer)) {
            entTeamInfo.setTeamlogo(logoPathInServer);
        }

        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entTeamInfo);
        return mParameters;
    }

    ResponseListener createTeamResponseListener = new ResponseListener<EntTeamInfo>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntTeamInfo entTeamInfo) {
            System.out.println("创建球队成功");
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
    };

    ResponseListener uploadLogoResponseListner = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            logoPathInServer = response.getLocation();
            System.out.println(logoPathInServer);
            //提交LOGO成功后，再提交整个表单到后台
            commit();
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_team_logo:
                photoPopupWindow.showAtLocation(ll_create_team,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.edt_team_location:
                Intent intent = new Intent(context, FindLocationActivity.class);
                intent.putExtra(Constants.EXTRA_PREV_PAGE_NAME, "CreateTeamActivity");
                startActivity(intent);
                break;
        }
    }
}
