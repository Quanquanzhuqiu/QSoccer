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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseApplication;
import com.qqzq.base.BaseFragmentActivity;
import com.qqzq.config.Constants;
import com.qqzq.game.dto.EntGameInfoDTO;
import com.qqzq.me.activity.MeActivity;
import com.qqzq.me.activity.MeCardActivity;
import com.qqzq.me.activity.MeSettingActivity;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.game.fragment.GameManagementFragment;
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

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private Activity context = this;
    private final static String TAG = "MyTeamFrag";

    //我的球队页面布局
    private View myTeamLayout;

    //球队管理页面的Fragment
    private TeamManageFragment teamMangmentFragment;

    //球队内部活动的Fragment
    private GameManagementFragment gameManagementFragment;

    private Button[] mTabs;

    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;

    private List<EntTeamInfoDTO> teamList = new ArrayList<EntTeamInfoDTO>();
    private List<EntGameInfoDTO> gameList = new ArrayList<EntGameInfoDTO>();

    private String teamPageType = Constants.PAGE_TYPE_NO_TEAM;
    private String gamePageType = Constants.PAGE_TYPE_NO_GAME;

    /**
     * PagerSlidingTabStrip的实例
     */
    private TopBar mTopbarTopBar;
    private PagerSlidingTabStrip mTabsPagerSlidingTabStrip;
    private ViewPager mPagerViewPager;
    private LinearLayout mMainLinearLayout;
    LinearLayout ll_bottom_myteam, ll_bottom_chat, ll_bottom_find, ll_bottom_me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        loadTeamList();
    }

    private void initView() {
        mTopbarTopBar = (TopBar) findViewById(R.id.topbar);
        mTabsPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mPagerViewPager = (ViewPager) findViewById(R.id.pager);
        mMainLinearLayout = (LinearLayout) findViewById(R.id.ll_main);
        dm = getResources().getDisplayMetrics();


        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_my_team);
        mTabs[1] = (Button) findViewById(R.id.btn_chat);
        mTabs[2] = (Button) findViewById(R.id.btn_find);
        mTabs[3] = (Button) findViewById(R.id.btn_me);
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);
    }

    private void initListener() {
        mTabs[3].setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_me:
                Intent intent = new Intent(context, MeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"球队管理", "球队内部活动"};

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
                    if (teamMangmentFragment == null) {
                        teamMangmentFragment = new TeamManageFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXTRA_PAGE_TYPE, teamPageType);
                        teamMangmentFragment.setArguments(bundle);
                    }
                    return teamMangmentFragment;
                case 1:
                    if (gameManagementFragment == null) {
                        gameManagementFragment = new GameManagementFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXTRA_PAGE_TYPE, gamePageType);
                        gameManagementFragment.setArguments(bundle);
                    }
                    return gameManagementFragment;
                default:
                    return null;
            }
        }

    }


    public void loadTeamList() {
        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_MEMBER_BY_ID_URL, BaseApplication.QQZQ_USER);
        queryUrl = Utils.makeGetRequestUrl(queryUrl, null);

        GsonRequest gsonRequest = new GsonRequest<EntTeamInfoDTO[]>(queryUrl, EntTeamInfoDTO[].class,
                new ResponseListener<EntTeamInfoDTO[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(EntTeamInfoDTO[] entTeamInfos) {
                        if (entTeamInfos.length > 0) {
                            teamList = Arrays.asList(entTeamInfos);
                            TeamManageFragment.list = teamList;
                            teamPageType = Constants.PAGE_TYPE_HAVE_TEAM;
                        } else {
                            teamPageType = Constants.PAGE_TYPE_NO_TEAM;
                        }

                        Log.i(TAG, "teamPageType = " + teamPageType);
                        loadGameList();
                    }
                });
        executeRequest(gsonRequest);
    }

    public void loadGameList() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", 10);
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_GAME_URL, mParameters);
        GsonRequest gsonRequest = new GsonRequest<EntGameInfoDTO[]>(queryUrl, EntGameInfoDTO[].class,
                new ResponseListener<EntGameInfoDTO[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(EntGameInfoDTO[] entGameInfos) {

                        if (entGameInfos.length > 0) {
                            gameList = Arrays.asList(entGameInfos);
                            GameManagementFragment.list = gameList;
                            gamePageType = Constants.PAGE_TYPE_HAVE_GAME;
                        } else {
                            gamePageType = Constants.PAGE_TYPE_NO_GAME;
                        }

                        Log.i(TAG, "gamePageType = " + gamePageType);
                        mPagerViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                        mTabsPagerSlidingTabStrip.setViewPager(mPagerViewPager);
                        setTabsValue();
                    }
                });
        executeRequest(gsonRequest);
    }

}
