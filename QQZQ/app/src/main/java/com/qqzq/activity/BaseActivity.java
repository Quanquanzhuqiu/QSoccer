package com.qqzq.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
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

    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected Cache.Entry getRequestCache(String url) {
        return RequestManager.getRequestQueue().getCache().get(url);
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
