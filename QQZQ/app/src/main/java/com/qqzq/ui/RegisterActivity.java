package com.qqzq.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.qqzq.BaseActivity;
import com.qqzq.R;

/**
 * Created by jie.xiao on 9/8/2015.
 */
public class RegisterActivity extends BaseActivity {

    private Context context = this;
    private TextView tv_qqzq_agreement;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        tv_qqzq_agreement = (TextView) findViewById(R.id.tv_qqzq_agreement);
        SpannableString spanableInfo = new SpannableString(context.getResources().getString(R.string.txt_sign_agreement));
        int start = 9;
        int end = spanableInfo.length();
        spanableInfo.setSpan(clickableSpan, start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_qqzq_agreement.setText(spanableInfo);
    }

    ClickableSpan clickableSpan = new MyClickableSpan() {
        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
    };

    private class MyClickableSpan extends ClickableSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
//            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false); //去掉下划线
        }

        @Override
        public void onClick(View widget) {

        }
    }
}