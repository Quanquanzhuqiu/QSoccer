package com.qqzq.subitem.team.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Window;

import com.qqzq.R;
import com.qqzq.activity.BaseFragmentActivity;
import com.qqzq.subitem.team.MemberBalanceFragment;
import com.qqzq.subitem.team.MemberFeeDetailFragment;
import com.qqzq.widget.menu.TopBar;
import com.qqzq.widget.tab.PagerSlidingTabStrip;

/**
 * Created by jie.xiao on 11/3/2015.
 */
public class MemberFeeManageActivity extends BaseFragmentActivity {

    //队员余额表Fragment
    MemberBalanceFragment memberBalanceFragment;

    //会费收支明细表Fragment
    MemberFeeDetailFragment memberFeeDetailFragment;

    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;

    /**
     * PagerSlidingTabStrip的实例
     */
    private TopBar mTopbarTopBar;
    private PagerSlidingTabStrip mTabsPagerSlidingTabStrip;
    private ViewPager mPagerViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_member_fee_manage);
//        initData();
    }
}
