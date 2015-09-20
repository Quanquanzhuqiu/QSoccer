package com.qqzq.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.qqzq.R;

/**
 * Created by jie.xiao on 15/9/20.
 */
public class CustomPopupView extends PopupWindow {

    public CustomPopupView(Activity activity, View parent, View layoutView) {

        layoutView.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.fade_ins));
        layoutView.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_bottom_in_2));
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = activity.getWindowManager().getDefaultDisplay().getWidth();
        this.setWidth(w / 2);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setContentView(layoutView);
        this.showAsDropDown(parent);
//        this.showAtLocation(parent, Gravity.LEFT, 0, 0);
        this.update();
    }

}