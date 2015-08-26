package com.qqzq.ui;

import android.app.ActivityGroup;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;

import com.qqzq.BaseActivity;
import com.qqzq.R;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class MainTabBar extends BaseActivity implements View.OnClickListener {

    //Fragment:我的球队
    private MyTeamFragment myTeamFragment;

    //我的球队页面布局
    private View myTeamLayout;

    //Fragment管理器
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_tab_bar);
        fragmentManager = getFragmentManager();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_bottom_menu_layout_1:
                selectTab(0);
                break;
            case R.id.tab_bottom_menu_layout_2:
                selectTab(1);
                break;
            case R.id.tab_bottom_menu_layout_3:
                selectTab(2);
                break;
            case R.id.tab_bottom_menu_layout_4:
                selectTab(3);
                break;
            default:
                break;
        }
    }

    private void selectTab(int tabIndex) {
        clearTabSelection();
        //开启Fragment事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //隐藏所有Fragment
        hideAllFragments(fragmentTransaction);
        switch (tabIndex) {
            case 0:

                if (myTeamFragment == null) {
                    //如果myTeamFragment为空，则创建一个添加到界面上
                    myTeamFragment = new MyTeamFragment();
                    fragmentTransaction.add(R.id.body_content, myTeamFragment);
                } else {
                    // 如果myTeamFragment不为空，则直接将它显示出来
                    fragmentTransaction.show(myTeamFragment);
                }

                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

        fragmentTransaction.commit();
    }

    private void clearTabSelection() {

    }

    private void hideAllFragments(FragmentTransaction transaction) {

        if (myTeamFragment != null) {
            transaction.hide(myTeamFragment);
        }
    }
}
