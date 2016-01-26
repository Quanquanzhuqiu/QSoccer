package com.qqzq.common.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.qqzq.game.fragment.GameManagementFragment;
import com.qqzq.me.activity.MeActivity;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.RequestManager;
import com.qqzq.network.ResponseListener;
import com.qqzq.team.dto.EntTeamInfoDTO;
import com.qqzq.team.fragemnt.TeamManageFragment;
import com.qqzq.util.Utils;
import com.qqzq.widget.MyViewPager;
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
public class MyTeamFragment extends Fragment implements View.OnClickListener {

    private Activity context = this.getActivity();
    private final static String TAG = "MyTeamFragment";

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
    private com.qqzq.widget.MyViewPager mPagerViewPager;
    private LinearLayout mMainLinearLayout;
    LinearLayout ll_bottom_myteam, ll_bottom_chat, ll_bottom_find, ll_bottom_me;

    public MyTeamFragment() {

    }

//    private FragmentManager fm;
//    public void setFragmentManager (FragmentManager fm){
//        this.fm = fm;
//
//    }

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_team, container,false);
        initData();
        initView();
        return  view;
    }

    private void initData() {
        loadTeamList();
    }

    private void initView() {
        mTopbarTopBar = (TopBar) view.findViewById(R.id.topbar);
        mTabsPagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        mPagerViewPager = (MyViewPager) view. findViewById(R.id.pager);
        mMainLinearLayout = (LinearLayout) view.findViewById(R.id.ll_main);
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
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.show()
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
        RequestManager.addRequest(gsonRequest, this);
    }

    public void loadGameList() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", 100);
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
                            for(EntGameInfoDTO entGameInfoDTO:entGameInfos){
                                gameList.add(entGameInfoDTO);
                            }
                            GameManagementFragment.list = gameList;
                            gamePageType = Constants.PAGE_TYPE_HAVE_GAME;
                        } else {
                            gamePageType = Constants.PAGE_TYPE_NO_GAME;
                        }

                        Log.i(TAG, "gamePageType = " + gamePageType);
                        mPagerViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
                        mTabsPagerSlidingTabStrip.setViewPager(mPagerViewPager);
                        setTabsValue();
                    }
                });
        RequestManager.addRequest(gsonRequest, this);
    }

}
