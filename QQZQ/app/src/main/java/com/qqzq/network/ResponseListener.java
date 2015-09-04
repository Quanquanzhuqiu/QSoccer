package com.qqzq.network;

import com.android.volley.Response;

/**
 * Created by jie.xiao on 15/9/4.
 */
public interface ResponseListener<T> extends Response.ErrorListener,Response.Listener<T> {

}
