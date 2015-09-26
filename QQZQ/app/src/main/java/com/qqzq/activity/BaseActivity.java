package com.qqzq.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.qqzq.network.RequestManager;

import cn.smssdk.SMSSDK;

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
            System.out.println("Cancel all json request!!!");
            RequestManager.cancelAll(this);

            //主要所有SMSSDK监听事件
            SMSSDK.unregisterAllEventHandler();
        } catch (Exception e) {
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


    // 弹出Toast

    public static void showLongToast(Context ctx, String text) {
        Toast.makeText(ctx, text, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context ctx, String text) {
        Toast toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showSuccessShortToast(Context ctx) {
        Toast toast = Toast.makeText(ctx, "操作成功", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showFailShortToast(Context ctx) {
        Toast toast = Toast.makeText(ctx, "操作失败", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
