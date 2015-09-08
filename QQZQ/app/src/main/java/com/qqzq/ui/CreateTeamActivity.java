package com.qqzq.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.common.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.MultipartRequest;
import com.qqzq.network.ResponseListener;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 8/31/2015.
 */
public class CreateTeamActivity extends BaseActivity {

    private TextView tv_commit;
    private EditText edt_team_name;
    private EditText edt_team_id;
    private EditText edt_team_province;
    private EditText edt_team_city;
    private EditText edt_team_detail;
    private CheckBox cbox_5_persons;
    private CheckBox cbox_7_persons;
    private CheckBox cbox_9_persons;
    private CheckBox cbox_11_persons;
    private RadioGroup radio_group_join_config;
    private ImageView iv_logo;
    private View ll_create_team;

    private PhotoPopupWindow photoPopupWindow;

    private Bitmap logo;//Logo Bitmap

    private String logoPathInServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        init();
    }

    private void init() {
        ll_create_team = findViewById(R.id.ll_create_team);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        edt_team_id = (EditText) findViewById(R.id.edt_team_id);
        edt_team_name = (EditText) findViewById(R.id.edt_team_name);
        edt_team_province = (EditText) findViewById(R.id.edt_team_province);
        edt_team_city = (EditText) findViewById(R.id.edt_team_city);
        edt_team_detail = (EditText) findViewById(R.id.edt_team_detail);
        radio_group_join_config = (RadioGroup) findViewById(R.id.radio_group_join_config);
        cbox_5_persons = (CheckBox) findViewById(R.id.cbox_5_persons);
        cbox_7_persons = (CheckBox) findViewById(R.id.cbox_7_persons);
        cbox_9_persons = (CheckBox) findViewById(R.id.cbox_9_persons);
        cbox_11_persons = (CheckBox) findViewById(R.id.cbox_11_persons);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadLogoAndTeamBasicInfo();
            }
        });

        iv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPopupWindow = new PhotoPopupWindow(CreateTeamActivity.this, null);
                photoPopupWindow.showAtLocation(ll_create_team,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从手机相册选择
            case 1:
                if (resultCode == RESULT_OK) {
                    photoPopupWindow.cropPhoto(data.getData());//裁剪图片
                }
                break;
            //拍照
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Constants.IMAGE_PHOTO_TMP_PATH
                            + "/logo.jpg");
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
        MultipartRequest<EntClientResponse> request = new MultipartRequest(Constants.API_FILE_UPLOAD_FASTDFS_URL,
                logoFile, EntClientResponse.class, null, uploadLogoResponseListner);

        executeRequest(request);
    }

    public void commit() {
        Map<String, Object> mParameters = prepareRequestJson();

        if (mParameters == null || mParameters.isEmpty()) {
            return;
        }

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, Constants.API_CREATE_TEAM_URL,
                EntTeamInfo.class, null, mParameters, createTeamResponseListener);

        executeRequest(gsonRequest);
    }

    public Map<String, Object> prepareRequestJson() {
        Map<String, Object> mParameters = new HashMap<>();
        String teamId = edt_team_id.getText().toString();
        String teamName = edt_team_name.getText().toString();
        String teamProvince = edt_team_province.getText().toString();
        String teamCity = edt_team_city.getText().toString();
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
        entTeamInfo.setTeamno(teamId);
        entTeamInfo.setTeamname(teamName);
        entTeamInfo.setJoinconfig(join_config);
        entTeamInfo.setOftensoccerpernum(soccerPersons);
        entTeamInfo.setSumary(teamDetail);
        entTeamInfo.setEstablishdate(new Date());
        entTeamInfo.setOftencity(0);
        entTeamInfo.setOftendistinct(0);
//        entTeamInfo.setTeamleadernm("");
        entTeamInfo.setTeamleaderusrrnm("13551063785");
//        entTeamInfo.setTeamleaderusrrnm("");
        if (!TextUtils.isEmpty(logoPathInServer)) {
            entTeamInfo.setTeamlogo(logoPathInServer);
        }
        mParameters.put(Constants.GSON_REQUST_POST_PARAM_KEY, entTeamInfo);
        return mParameters;
    }

    ResponseListener createTeamResponseListener = new ResponseListener<EntTeamInfo>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            System.out.println(new String(volleyError.networkResponse.data));
        }

        @Override
        public void onResponse(EntTeamInfo entTeamInfo) {
            System.out.println("创建球队成功");
        }
    };

    ResponseListener uploadLogoResponseListner = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            String result = new String(volleyError.networkResponse.data);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            logoPathInServer = response.getLocation();
            System.out.println(logoPathInServer);
            //提交LOGO成功后，再提交整个表单到后台
            commit();
        }
    };
}
