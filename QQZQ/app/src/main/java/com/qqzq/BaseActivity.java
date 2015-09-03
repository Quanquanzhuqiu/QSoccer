package com.qqzq;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.qqzq.network.RequestManager;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class BaseActivity extends Activity {

    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    @Override
    public void onStop() {
        super.onStop();
        try {

            RequestManager.cancelAll(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
