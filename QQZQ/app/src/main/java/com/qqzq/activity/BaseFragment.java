package com.qqzq.activity;

import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.qqzq.network.RequestManager;

/**
 * Created by jie.xiao on 15/9/12.
 */
public abstract class BaseFragment extends Fragment {

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }
}
