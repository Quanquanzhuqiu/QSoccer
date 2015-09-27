package com.qqzq.listener;

import android.app.Activity;
import android.view.View;

/**
 * Created by jie.xiao on 15/9/27.
 */
public class BackButtonListener implements View.OnClickListener {

    private Activity activity;

    public BackButtonListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        activity.finish();
    }
}
