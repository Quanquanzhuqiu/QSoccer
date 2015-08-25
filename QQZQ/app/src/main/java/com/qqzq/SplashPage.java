package com.qqzq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import com.qqzq.util.ShareReferenceUtil;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class SplashPage extends Activity {

    private static long backClickTime = -1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
			if(ShareReferenceUtil.isFirstRun(getApplicationContext())){
            ShareReferenceUtil.setIsFirstRun(getApplicationContext(), false);
            startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
            finish();
			}else{
				startActivity(new Intent(getApplicationContext(),MainActivity.class));
				finish();
			}
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        Message msg = new Message();
        handler.sendMessageDelayed(msg, 1500);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            long nowTime = System.currentTimeMillis();
            if(backClickTime<=0 || nowTime-backClickTime>2000){
                backClickTime = nowTime;
                Toast.makeText(getApplicationContext(), "再按一次 退出程序", Toast.LENGTH_SHORT).show();
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
