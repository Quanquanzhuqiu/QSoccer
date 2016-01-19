package com.qqzq.common.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;


import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/15.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter implements RadioGroup.OnCheckedChangeListener{
    private ArrayList<Fragment> mFragments;
    private ViewPager mViewPager;
    private RadioGroup mGroup;
    private FragmentActivity mFragmentActivity;
    private FragmentManager mFrm;
    public MyFragmentPagerAdapter(FragmentActivity fragmentActivity, FragmentManager fm, ArrayList<Fragment> fragements,
                                  ViewPager viewPager, RadioGroup group){
        super(fm);
        this.mFragments = fragements;
        this.mViewPager = viewPager;
        this.mGroup = group;
        this.mFragmentActivity = fragmentActivity;
        this.mFrm = fm;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mGroup.check(mGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mGroup.setOnCheckedChangeListener(this);
    }

    public MyFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for(int i = 0; i < this.mGroup.getChildCount(); ++i) {
            if(this.mGroup.getChildAt(i).getId() == checkedId) {
//                Fragment fragment = (Fragment)this.mFragments.get(i);
//                FragmentTransaction ft = this.obtainFragmentTransaction();
                mViewPager.setCurrentItem(i,false);
//                this.getCurrentFragment().onPause();
//                if(fragment.isAdded()) {
//                    fragment.onStart();
//                } else {
//                    ft.add(this.fgContentId, fragment);
//                }
//
//                this.showFragment(i);
//                ft.commit();
//                if(this.mCheckedListener != null) {
//                    this.mCheckedListener.onChecked(i);
//                }
//                break;
            }
        }
    }
    private FragmentTransaction obtainFragmentTransaction() {
        FragmentTransaction fg;
        if(this.mFragmentActivity == null) {
            fg = this.mFrm.beginTransaction();
        } else {
            fg = this.mFragmentActivity.getSupportFragmentManager().beginTransaction();
        }

        return fg;
    }
}
