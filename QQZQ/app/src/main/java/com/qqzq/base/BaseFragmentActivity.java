package com.qqzq.base;

import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.qqzq.network.RequestManager;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class BaseFragmentActivity extends FragmentActivity {

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

}
