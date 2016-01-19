package com.qqzq.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseApplication;
import com.qqzq.base.BaseFragmentActivity;
import com.qqzq.common.adapter.MyFragmentPagerAdapter;
import com.qqzq.common.fragment.ChatFragment;
import com.qqzq.common.fragment.FindFragment;
import com.qqzq.common.fragment.MeFragment;
import com.qqzq.common.fragment.MineFragment;
import com.qqzq.common.fragment.MyTeamFrag;
import com.qqzq.common.fragment.MyTeamFragment;
import com.qqzq.config.Constants;
import com.qqzq.game.dto.EntGameInfoDTO;
import com.qqzq.game.fragment.GameManagementFragment;
import com.qqzq.me.activity.MeActivity;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.team.dto.EntTeamInfoDTO;
import com.qqzq.team.fragemnt.TeamManageFragment;
import com.qqzq.util.Utils;
import com.qqzq.widget.menu.TopBar;
import com.qqzq.widget.tab.PagerSlidingTabStrip;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class MainActivity2 extends BaseFragmentActivity implements View.OnClickListener {


    private com.qqzq.common.fragment.MyTeamFragment myTeamFragment;
    private com.qqzq.common.fragment.ChatFragment chatFragment;
    private com.qqzq.common.fragment.FindFragment findFragment;
    private com.qqzq.common.fragment.MeFragment meFragment;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Bind(R.id.pager) ViewPager mViewPager;
    @Bind(R.id.bottom)
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
//
//        myTeamFragment = new MyTeamFragment();
//        myTeamFragment.setFragmentManager(this.getSupportFragmentManager());

        MyTeamFrag myTeamFrag = new MyTeamFrag();
//        myTeamFrag.setFragmentManager(this.getSupportFragmentManager());

        chatFragment = new ChatFragment();
        findFragment = new FindFragment();
        meFragment = new MeFragment();
        fragments.add(myTeamFrag);
        fragments.add(findFragment);
        fragments.add(chatFragment);
        fragments.add(meFragment);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(this,getSupportFragmentManager(),
                fragments, mViewPager,radioGroup);
        mViewPager.setAdapter(myFragmentPagerAdapter);
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
