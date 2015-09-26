package com.qqzq.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.chat.ChatFragment;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.find.FindFragment;
import com.qqzq.subitem.game.GameManagementFragment;
import com.qqzq.subitem.me.MeFragment;
import com.qqzq.subitem.team.MyTeamFragment;
import com.qqzq.subitem.team.TeamMangmentFragment;
import com.qqzq.util.Utils;
import com.qqzq.widget.menu.PopupMenuWindow;
import com.qqzq.widget.tab.PagerSlidingTabStrip;

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

    //我的球队页面的Fragment
    private MyTeamFragment myTeamFragment;

    //聊天页面的Fragment
    private ChatFragment chatFragment;

    //发现页面的Fragment
    private FindFragment findFragment;

    //我页面的Fragment
    private MeFragment meFragment;

    //我的球队页面布局
    private View myTeamLayout;

    private ViewPager pager;

    //球队管理页面的Fragment
    private TeamMangmentFragment teamMangmentFragment;

    //球队内部活动的Fragment
    private GameManagementFragment gameManagementFragment;


    /**
     * PagerSlidingTabStrip的实例
     */
    private PagerSlidingTabStrip tabs;

    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;

    private List<EntTeamInfo> teamList = new ArrayList<EntTeamInfo>();
    private String teamPageType = Constants.PAGE_TYPE_NO_TEAM;
    private String gamePageType = Constants.PAGE_TYPE_NO_GAME;
    private ImageView iv_more_menu;
    private LinearLayout ll_more_menu;
    private PopupMenuWindow popupMenuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (popupMenuWindow != null && popupMenuWindow.isShowing()) {
            popupMenuWindow.dismiss();
        }
    }

    private void init() {
        loadTeamListFromBackend();

        iv_more_menu = (ImageView) findViewById(R.id.iv_more_menu);
        ll_more_menu = (LinearLayout) findViewById(R.id.ll_more_menu);
        dm = getResources().getDisplayMetrics();
        pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        popupMenuWindow = new PopupMenuWindow(context, null);
        popupMenuWindow.dismiss();

        ll_more_menu.setOnClickListener(this);
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#45c01a"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
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
                        teamMangmentFragment = new TeamMangmentFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXTRA_PAGE_TYEP, teamPageType);
                        teamMangmentFragment.setArguments(bundle);
                    }
                    return teamMangmentFragment;
                case 1:
                    if (gameManagementFragment == null) {
                        gameManagementFragment = new GameManagementFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXTRA_PAGE_TYEP, gamePageType);
                        gameManagementFragment.setArguments(bundle);
                    }
                    return gameManagementFragment;
                default:
                    return null;
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_more_menu:
//                Toast.makeText(context, "已点中弹出菜单", Toast.LENGTH_LONG).show();
                popupMenuWindow.showAsDropDown(iv_more_menu);
                break;
            default:
                break;
        }
    }


    public void loadTeamListFromBackend() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", 0);
        mParameters.put("limit", 6);
        mParameters.put("teamLeaderUsrNm", BaseApplication.QQZQ_USER);
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_TEAM_URL, mParameters);
        GsonRequest gsonRequest = new GsonRequest<EntTeamInfo[]>(queryUrl, EntTeamInfo[].class,
                findTeamResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findTeamResponseListener = new ResponseListener<EntTeamInfo[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntTeamInfo[] entTeamInfos) {
            if (entTeamInfos.length > 0) {
                teamList = Arrays.asList(entTeamInfos);
//                teamMangmentFragment.list = Arrays.asList(entTeamInfos);
                TeamMangmentFragment.list = Arrays.asList(entTeamInfos);
                teamPageType = Constants.PAGE_TYPE_HAVE_TEAM;
            } else {
                teamPageType = Constants.PAGE_TYPE_NO_TEAM;
            }
            pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
            tabs.setViewPager(pager);
            setTabsValue();
        }
    };
}
