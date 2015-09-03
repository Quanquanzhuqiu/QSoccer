package com.qqzq.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qqzq.R;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class PhotoPopupWindow extends PopupWindow{

    private View mMenuView; // PopupWindow 菜单布局
    private Context context; // 上下文参数
    private View.OnClickListener myOnClick; // PopupWindow 菜单 空间单击事件

    public PhotoPopupWindow(Activity context, View.OnClickListener myOnClick) {
        super(context);
        this.context = context;
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
        btn_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

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
}
