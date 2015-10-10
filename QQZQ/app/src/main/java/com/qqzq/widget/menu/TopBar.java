package com.qqzq.widget.menu;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qqzq.R;
import com.qqzq.listener.TopBarListener;

/**
 * Created by jie.xiao on 15/10/9.
 */
public class TopBar extends LinearLayout implements View.OnClickListener {

    private final String TAG = "TopBar";

    private Activity context = null;

    private ImageView mBackImageView;
    private LinearLayout mBackLinearLayout;
    private TextView mTitleTextView;
    private ImageView mMoreImageView;
    private TextView mCommitTextView;
    private LinearLayout mMoreLinearLayout;
    private PopupMenuWindow popupMenuWindow;

    private int leftImage;
    private int rightImage;
    private String rightText;
    private String pageTitle;

    private TopBarListener mListener;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            return;
        }

        this.context = (Activity) context;
        initAttribute(attrs);
        initView();
        initListener();
    }

    public void setListener(TopBarListener mListener) {
        this.mListener = mListener;
    }

    private void initAttribute(AttributeSet attrs) {
        //获取自定义的属性并赋值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        pageTitle = typedArray.getString(R.styleable.TopBar_pageTitle);
        leftImage = typedArray.getResourceId(R.styleable.TopBar_leftImage, -1);
        rightImage = typedArray.getResourceId(R.styleable.TopBar_rightImage, -1);
        rightText = typedArray.getString(R.styleable.TopBar_rightText);

        Log.i(TAG, "pageTitle = " + pageTitle);
        Log.i(TAG, "leftImage = " + leftImage);
        Log.i(TAG, "rightImage = " + rightImage);
        Log.i(TAG, "rightText = " + rightText);

        //释放资源
        typedArray.recycle();
    }


    private void initView() {
        LayoutInflater.from(context).inflate(R.layout.common_topbar, this);
        mBackImageView = (ImageView) findViewById(R.id.iv_back);
        mBackLinearLayout = (LinearLayout) findViewById(R.id.ll_back);
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mMoreImageView = (ImageView) findViewById(R.id.iv_more);
        mCommitTextView = (TextView) findViewById(R.id.tv_commit);
        mMoreLinearLayout = (LinearLayout) findViewById(R.id.ll_more);

        if (leftImage > 0) {
            mBackImageView.setBackgroundResource(leftImage);
            mBackImageView.setVisibility(VISIBLE);
        } else {
            mBackImageView.setVisibility(GONE);
        }

        if (rightImage > 0) {
            mMoreImageView.setBackgroundResource(rightImage);
            mMoreImageView.setVisibility(VISIBLE);
            mCommitTextView.setVisibility(GONE);

            if (rightImage == R.drawable.ic_btn_more) {
                Log.i(TAG, "含有弹出菜单的页面！");
                popupMenuWindow = new PopupMenuWindow(context, null);
                popupMenuWindow.dismiss();
            }

        } else if (rightText != null) {
            mMoreImageView.setVisibility(GONE);
            mCommitTextView.setText(rightText);
            mCommitTextView.setVisibility(VISIBLE);
        } else {
            mMoreImageView.setVisibility(GONE);
            mCommitTextView.setVisibility(GONE);
        }

    }

    private void initListener() {
        mBackLinearLayout.setOnClickListener(this);
        mMoreLinearLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                context.finish();
                break;
            case R.id.ll_more:
                if (rightText != null && mListener != null) {
                    mListener.rightButtonClick();
                } else {
                    Toast.makeText(context, "已点中弹出菜单", Toast.LENGTH_LONG).show();
                    popupMenuWindow.showAsDropDown(mMoreLinearLayout);
                }
                break;
            default:
                break;
        }
    }
}
