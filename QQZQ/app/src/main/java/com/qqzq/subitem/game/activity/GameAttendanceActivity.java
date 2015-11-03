package com.qqzq.subitem.game.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseFragmentActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntGameInfo;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.game.AttendanceStatisticsFragment;
import com.qqzq.subitem.game.GameAttendManageFragment;
import com.qqzq.util.Utils;
import com.qqzq.widget.menu.TopBar;
import com.qqzq.widget.tab.PagerSlidingTabStrip;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 15/10/25.
 */
public class GameAttendanceActivity extends BaseFragmentActivity {

    private Activity context = this;
    private final static String TAG = "GameAttendanceActivity";

    //球队出勤管理Fragment
    GameAttendManageFragment gameAttendanceManagementFragment;

    //球队出勤统计Fragment
    AttendanceStatisticsFragment attendanceStatisticsFragment;

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

    private int selectedTeamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game_attendance_manage);
        initData();
        initView();
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_TEAM_ID)) {

            selectedTeamId = extras.getInt(Constants.EXTRA_SELECTED_TEAM_ID);
            if (selectedTeamId > 0) {
                loadGameList();
            }
        }
    }

    private void initView() {

        mTopbarTopBar = (TopBar) findViewById(R.id.topbar);
        mTabsPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mPagerViewPager = (ViewPager) findViewById(R.id.pager);
        dm = getResources().getDisplayMetrics();
    }


    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        mTabsPagerSlidingTabStrip.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mTabsPagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mTabsPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        mTabsPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        mTabsPagerSlidingTabStrip.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        mTabsPagerSlidingTabStrip.setIndicatorColor(Color.parseColor("#45c01a"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        mTabsPagerSlidingTabStrip.setSelectedTextColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        mTabsPagerSlidingTabStrip.setTabBackground(0);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"出勤管理", "出勤统计"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (gameAttendanceManagementFragment == null) {
                        gameAttendanceManagementFragment = new GameAttendManageFragment();
                    }
                    return gameAttendanceManagementFragment;
                case 1:
                    if (attendanceStatisticsFragment == null) {
                        attendanceStatisticsFragment = new AttendanceStatisticsFragment();
                    }
                    return attendanceStatisticsFragment;
                default:
                    return null;
            }
        }

    }

    public void loadGameList() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", 10);
        mParameters.put("teamId", selectedTeamId);
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_GAME_URL, mParameters);
        GsonRequest gsonRequest = new GsonRequest<EntGameInfo[]>(queryUrl, EntGameInfo[].class,
                findGameResponseListener);
        executeRequest(gsonRequest);
    }


    ResponseListener findGameResponseListener = new ResponseListener<EntGameInfo[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntGameInfo[] entGameInfos) {
            GameAttendManageFragment.list = Arrays.asList(entGameInfos);
            loadMemberAttendceInfo();
        }
    };

    public void loadMemberAttendceInfo() {
        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_MEMBER_DETAIL_BY_ID_URL, selectedTeamId);
        GsonRequest gsonRequest = new GsonRequest<EntTeamMember[]>(queryUrl, EntTeamMember[].class,
                findTeamMemberResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findTeamMemberResponseListener = new ResponseListener<EntTeamMember[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntTeamMember[] entTeamMembers) {
            Log.i(TAG, "找到本队球员" + entTeamMembers.length);
            AttendanceStatisticsFragment.list = Arrays.asList(entTeamMembers);
            mPagerViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
            mTabsPagerSlidingTabStrip.setViewPager(mPagerViewPager);
            setTabsValue();
        }
    };
}
