package com.qqzq.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.common.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class PhotoPopupWindow extends PopupWindow {

    private View mMenuView; // PopupWindow 菜单布局
    private Activity context; // 上下文参数
    private View.OnClickListener myOnClick; // PopupWindow 菜单 空间单击事件

    private File photoSavedFile = null;

    public PhotoPopupWindow(Activity context, View.OnClickListener myOnClick) {
        super(context);
        this.context = context;

        if (myOnClick == null) {
            myOnClick = itemsOnClick;
        }
        this.myOnClick = myOnClick;
        Init();
    }

    private void Init() {
        // TODO Auto-generated method stub
        // PopupWindow 导入
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popitem_alter_icon, null);
        TextView btn_camera = (TextView) mMenuView
                .findViewById(R.id.btn_alter_pic_camera);
        TextView btn_photo = (TextView) mMenuView
                .findViewById(R.id.btn_alter_pic_photo);
        TextView btn_cancel = (TextView) mMenuView
                .findViewById(R.id.btn_alter_exit);

        btn_camera.setOnClickListener(myOnClick);
        btn_cancel.setOnClickListener(myOnClick);
        btn_photo.setOnClickListener(myOnClick);

        // 导入布局
        this.setContentView(mMenuView);
        // 设置动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mMenuView.findViewById(R.id.ll_pop).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            dismiss();
            switch (v.getId()) {
                //从手机相册选择
                case R.id.btn_alter_pic_photo:
                    Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    context.startActivityForResult(intent1, 1);
                    break;
                //拍照
                case R.id.btn_alter_pic_camera:
                    photoSavedFile = new File(Constants.IMAGE_PHOTO_TMP_PATH + "logo.jpg");
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoSavedFile));
                    context.startActivityForResult(intent2, 2);//采用ForResult打开
                    break;
                default:
                    break;
            }


        }

    };

    /**
     * 调用系统的裁剪
     *
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
        context.startActivityForResult(intent, 3);
    }

    public void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(Constants.IMAGE_PHOTO_TMP_PATH);
        file.mkdirs();// 创建文件夹
        String fileName = Constants.IMAGE_PHOTO_TMP_PATH + "logo.jpg";//图片名字
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
}
