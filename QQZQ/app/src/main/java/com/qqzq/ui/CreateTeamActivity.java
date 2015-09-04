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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.common.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.EntUplodResponse;
import com.qqzq.entity.RequestJsonParameter;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.MultipartRequest;
import com.qqzq.network.ResponseListener;

import java.io.File;
import java.util.Date;

/**
 * Created by jie.xiao on 8/31/2015.
 */
public class CreateTeamActivity extends BaseActivity {

    private TextView tv_commit;
    private EditText edit_team_name;
    private EditText edit_team_id;
    private EditText edit_team_province;
    private EditText edit_team_city;
    private EditText edit_team_detail;
    private CheckBox cbox_5_persons;
    private CheckBox cbox_7_persons;
    private CheckBox cbox_9_persons;
    private CheckBox cbox_11_persons;
    private RadioGroup radio_group_join_config;
    private ImageView iv_logo;
    private View ll_create_team;

    private PhotoPopupWindow photoPopupWindow;

    private RequestJsonParameter<EntTeamInfo> mParameters;

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
        edit_team_id = (EditText) findViewById(R.id.edit_team_id);
        edit_team_name = (EditText) findViewById(R.id.edit_team_name);
        edit_team_province = (EditText) findViewById(R.id.edit_team_province);
        edit_team_city = (EditText) findViewById(R.id.edit_team_city);
        edit_team_detail = (EditText) findViewById(R.id.edit_team_detail);
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
        MultipartRequest<EntUplodResponse> request = new MultipartRequest(Constants.API_FILE_UPLOAD_FASTDFS_URL, logoFile, EntUplodResponse.class, null, new ResponseListener<EntUplodResponse>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(new String(volleyError.networkResponse.data));
            }

            @Override
            public void onResponse(EntUplodResponse response) {
                logoPathInServer = response.getLocation();
                System.out.println(logoPathInServer);
                //提交LOGO成功后，再提交整个表单到后台
                commit();
            }
        });

        executeRequest(request);
    }

    public void commit(){
        mParameters = prepareRequestJson();

        if (mParameters == null) {
            return;
        }

        GsonRequest gsonRequest = new GsonRequest<EntTeamInfo>(Request.Method.POST, Constants.API_CREATE_TEAM_URL, EntTeamInfo.class, null, mParameters,
                new ResponseListener<EntTeamInfo>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println(new String(volleyError.networkResponse.data));
                    }

                    @Override
                    public void onResponse(EntTeamInfo entTeamInfo) {
                        System.out.println("创建球队成功");
                    }
                });

        executeRequest(gsonRequest);
    }

    public RequestJsonParameter prepareRequestJson() {
        mParameters = new RequestJsonParameter<EntTeamInfo>();
        String teamId = edit_team_id.getText().toString();
        String teamName = edit_team_name.getText().toString();
        String teamProvince = edit_team_province.getText().toString();
        String teamCity = edit_team_city.getText().toString();
        String teamDetail = edit_team_detail.getText().toString();

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
        if(!TextUtils.isEmpty(logoPathInServer)){
            entTeamInfo.setTeamlogo(logoPathInServer);
        }
        mParameters.setParameter(entTeamInfo);
        return mParameters;
    }

}
