package com.qqzq.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
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
import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.common.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.entity.RequestJsonParameter;
import com.qqzq.network.GsonRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private static String path="/sdcard/qqzq/logo/";//sd路径
    private Bitmap head;//头像Bitmap

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

                mParameters = prepareRequestJson();

                if (mParameters == null) {
                    return;
                }

                System.out.println("==========>" + mParameters.getParameter().getTeamno());

                executeRequest(new GsonRequest<EntTeamInfo>(Request.Method.POST, Constants.API_CREATE_TEAM_URL, EntTeamInfo.class, null, mParameters,
                        responseListener(), errorListener()));
            }
        });

        iv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPopupWindow = new PhotoPopupWindow(CreateTeamActivity.this, itemsOnClick);
                photoPopupWindow.showAtLocation(ll_create_team,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            photoPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_alter_pic_photo:
                    Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent1, 1);
                    break;
                case R.id.btn_alter_pic_camera:
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "head.jpg")));
                    startActivityForResult(intent2, 2);//采用ForResult打开
                    break;
                default:
                    break;
            }


        }

    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        iv_logo.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    };
    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        mParameters.setParameter(entTeamInfo);
        return mParameters;
    }

    private Response.Listener<EntTeamInfo> responseListener() {
        return new Response.Listener<EntTeamInfo>() {
            @Override
            public void onResponse(EntTeamInfo response) {
                System.out.println(">>>>>>>>>>>" + response);
//                mTvResult.setText(new Gson().toJson(response));
            }
        };
    }
}
