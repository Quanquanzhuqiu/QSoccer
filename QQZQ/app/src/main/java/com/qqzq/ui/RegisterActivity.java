package com.qqzq.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.common.Constants;

/**
 * Created by jie.xiao on 9/8/2015.
 */
public class RegisterActivity extends BaseActivity {

    private Context context = this;
    private TextView tv_qqzq_agreement;
    private ImageView iv_return;
    private EditText edt_select_location;
    private Bundle extras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        extras = this.getIntent().getExtras();
        iv_return = (ImageView) findViewById(R.id.iv_return);
        tv_qqzq_agreement = (TextView) findViewById(R.id.tv_qqzq_agreement);
        edt_select_location = (EditText) findViewById(R.id.edt_select_location);

        initSpanableString();

        if(extras.containsKey(Constants.EXTRA_SELECTED_LOCATION)){
            String selectedLocation = extras.getString(Constants.EXTRA_SELECTED_LOCATION);
            edt_select_location.setText(selectedLocation);
        }

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });

        edt_select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSpanableString() {
        SpannableString spanableInfo = new SpannableString(context.getResources().getString(R.string.txt_qqzq_agreement));
        int start = 7;
        int end = spanableInfo.length();
        spanableInfo.setSpan(new MyClickableSpan(), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_qqzq_agreement.setText(spanableInfo);
        tv_qqzq_agreement.setMovementMethod(LinkMovementMethod.getInstance());
        tv_qqzq_agreement.setFocusable(false);
        tv_qqzq_agreement.setClickable(false);
        tv_qqzq_agreement.setLongClickable(false);
    }

    private class MyClickableSpan extends ClickableSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.BLUE);
            ds.setUnderlineText(false); //去掉下划线
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        }
    }
}