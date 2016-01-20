package com.qqzq.common.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qqzq.R;
import com.qqzq.base.BaseFragmentActivity;
import com.qqzq.common.adapter.MyFragmentPagerAdapter;
import com.qqzq.common.fragment.ChatFragment;
import com.qqzq.common.fragment.FindFragment;
import com.qqzq.common.fragment.MeFragment;
import com.qqzq.common.fragment.MyTeamFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class MainActivity2 extends BaseFragmentActivity implements View.OnClickListener {


    private com.qqzq.common.fragment.ChatFragment chatFragment;
    private com.qqzq.common.fragment.MyTeamFragment myTeamFrag;
    private com.qqzq.common.fragment.FindFragment findFragment;
    private com.qqzq.common.fragment.MeFragment meFragment;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Bind(R.id.pager) ViewPager mViewPager;
    @Bind(R.id.bottom)
    LinearLayout group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        myTeamFrag = new MyTeamFragment();
        chatFragment = new ChatFragment();
        findFragment = new FindFragment();
        meFragment = new MeFragment();

        fragments.add(myTeamFrag);
        fragments.add(findFragment);
        fragments.add(chatFragment);
        fragments.add(meFragment);

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(this,getSupportFragmentManager(),
                fragments, mViewPager,group);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);

    }

    @Override
    public void onClick(View v) {

    }private long lastClickTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - lastClickTime < 2000) {
                return super.onKeyDown(keyCode, event);
            } else {
                lastClickTime = System.currentTimeMillis();
                Toast.makeText(MainActivity2.this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
