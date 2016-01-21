package com.qqzq.common.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;


import com.qqzq.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/15.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter implements RadioGroup.OnCheckedChangeListener, View.OnClickListener{
    private ArrayList<Fragment> mFragments;
    private ViewPager mViewPager;
    private LinearLayout mGroup;
    private FragmentActivity mFragmentActivity;
    private FragmentManager mFrm;
    @Bind(R.id.btn_my_team)
    Button teamBtn;
    @Bind(R.id.btn_chat)
    Button chatBtn;
    @Bind(R.id.btn_find)
    Button findBtn;
    @Bind(R.id.btn_me)
    Button meBtn;
    public MyFragmentPagerAdapter(FragmentActivity fragmentActivity, FragmentManager fm, ArrayList<Fragment> fragements,
                                  ViewPager viewPager, LinearLayout group){
        super(fm);
        this.mFragments = fragements;
        this.mViewPager = viewPager;
        this.mGroup = group;
        this.mFragmentActivity = fragmentActivity;
        this.mFrm = fm;
        currentPos = 0;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("pager", "onPageSelected--> postion-->" + position);
                currentPos = position;
                setButtonState(currentPos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ButterKnife.bind(this, mGroup);
        initDrawable();
        teamBtn.setOnClickListener(this);
        chatBtn.setOnClickListener(this);
        findBtn.setOnClickListener(this);
        meBtn.setOnClickListener(this);
        setButtonState(0);

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

                mViewPager.setCurrentItem(i,false);
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

    private int currentPos;

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_my_team:
                if(currentPos == 0){
                    return;
                }
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.btn_chat:
                if(currentPos == 1){
                    return;
                }
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.btn_find:
                if(currentPos == 2){
                    return;
                }
                mViewPager.setCurrentItem(2,false);
                break;
            case R.id.btn_me:
                if(currentPos == 3){
                    return;
                }
                mViewPager.setCurrentItem(3,false);
                break;
        }
    }
    Drawable teamNormal;
    Drawable teamSelected;
    Drawable chatNormal;
    Drawable chatSelected;
    Drawable findNormal;
    Drawable findSelected;
    Drawable meNormal;
    Drawable meSelected;
    private void initDrawable(){
        teamNormal = mFragmentActivity.getResources().getDrawable(R.drawable.ic_tab_myteam_normal);
        teamSelected = mFragmentActivity.getResources().getDrawable(R.drawable.ic_tab_myteam_selected);
        chatNormal = mFragmentActivity.getResources().getDrawable(R.drawable.ic_tab_chat_normal);
        chatSelected = mFragmentActivity.getResources().getDrawable(R.drawable.ic_tab_chat_selected);
        findNormal = mFragmentActivity.getResources().getDrawable(R.drawable.ic_tab_find_normal);
        findSelected = mFragmentActivity.getResources().getDrawable(R.drawable.ic_tab_find_selected);
        meNormal = mFragmentActivity.getResources().getDrawable(R.drawable.ic_tab_me_normal);
        meSelected  = mFragmentActivity.getResources().getDrawable(R.drawable.ic_tab_me_selected);
    }
    private void setButtonState(int pos){
        switch (pos){
            case 0:
                teamSelected.setBounds(0,0,teamSelected.getIntrinsicWidth()- 30,teamSelected.getIntrinsicHeight() - 30);
                teamBtn.setCompoundDrawables(null,teamSelected,null,null);

                chatNormal.setBounds(0,0,chatNormal.getIntrinsicWidth()- 30,chatNormal.getIntrinsicHeight()- 30);
                chatBtn.setCompoundDrawables(null,chatNormal,null,null);

                findNormal.setBounds(0, 0, findNormal.getIntrinsicWidth()- 30,findNormal.getIntrinsicHeight()- 30);
                findBtn.setCompoundDrawables(null, findNormal, null, null);

                meNormal.setBounds(0, 0,meNormal.getIntrinsicWidth()- 30,meNormal.getIntrinsicHeight()-30);
                meBtn.setCompoundDrawables(null, meNormal, null, null);

                break;
            case 1:

                teamNormal.setBounds(0,0,teamNormal.getIntrinsicWidth()- 30,teamNormal.getIntrinsicHeight()- 30);
                teamBtn.setCompoundDrawables(null,teamNormal,null,null);

                chatSelected.setBounds(0,0,chatSelected.getIntrinsicWidth()- 30,chatSelected.getIntrinsicHeight()- 30);
                chatBtn.setCompoundDrawables(null,chatSelected,null,null);

                findNormal.setBounds(0,0,findNormal.getIntrinsicWidth()- 30,findNormal.getIntrinsicHeight()- 30);
                findBtn.setCompoundDrawables(null, findNormal,null,null);

                meNormal.setBounds(0,0,meNormal.getIntrinsicWidth()- 30,meNormal.getIntrinsicHeight()- 30);
                meBtn.setCompoundDrawables(null, meNormal,null,null);

                break;
            case 2:

                teamNormal.setBounds(0,0,teamNormal.getIntrinsicWidth()- 30,teamNormal.getIntrinsicHeight()- 30);
                teamBtn.setCompoundDrawables(null,teamNormal,null,null);

                chatNormal.setBounds(0,0,chatNormal.getIntrinsicWidth()- 30,chatNormal.getIntrinsicHeight()- 30);
                chatBtn.setCompoundDrawables(null,chatNormal,null,null);

                findSelected.setBounds(0,0,findSelected.getIntrinsicWidth()- 30,findSelected.getIntrinsicHeight()- 30);
                findBtn.setCompoundDrawables(null, findSelected,null,null);

                meNormal.setBounds(0,0,meNormal.getIntrinsicWidth()- 30,meNormal.getIntrinsicHeight()- 30);
                meBtn.setCompoundDrawables(null, meNormal,null,null);

                break;
            case 3:

                teamNormal.setBounds(0,0,teamNormal.getIntrinsicWidth()- 30,teamNormal.getIntrinsicHeight()- 30);
                teamBtn.setCompoundDrawables(null, teamNormal,null,null);

                chatNormal.setBounds(0,0,chatNormal.getIntrinsicWidth()- 30,chatNormal.getIntrinsicHeight()- 30);
                chatBtn.setCompoundDrawables(null, chatNormal,null,null);

                findNormal.setBounds(0,0,findNormal.getIntrinsicWidth()- 30,findNormal.getIntrinsicHeight()- 30);
                findBtn.setCompoundDrawables(null, findNormal,null,null);

                meSelected.setBounds(0,0,meSelected.getIntrinsicWidth()- 30,meSelected.getIntrinsicHeight()- 30);
                meBtn.setCompoundDrawables(null, meSelected,null,null);


                break;
        }
    }

}
