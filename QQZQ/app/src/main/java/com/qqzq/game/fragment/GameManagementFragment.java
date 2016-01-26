package com.qqzq.game.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseFragment;
import com.qqzq.config.Constants;
import com.qqzq.game.adapter.GameListViewAdapter;
import com.qqzq.game.dto.EntGameInfoDTO;
import com.qqzq.game.activity.GameDetailActivity;
import com.qqzq.game.activity.GamePublishActivity;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.RequestManager;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;
import com.qqzq.widget.DropDownListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class GameManagementFragment extends BaseFragment {

    private final static String TAG = "GameManagementFragment";

    private Context context;
    private TextView tv_game_publish;
    private String pageType = "";
    private DropDownListView lv_games;
    private GameListViewAdapter adapter;
    public static List<EntGameInfoDTO> list = new ArrayList<EntGameInfoDTO>();
    public static int GAMEDETAILREQUESTCODE = 100;
    public static int GAMEDETAILRESULTCODE = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.EXTRA_PAGE_TYPE)) {
            pageType = bundle.getString(Constants.EXTRA_PAGE_TYPE);

            Log.i(TAG, "pageType = " + pageType);
            if (Constants.PAGE_TYPE_NO_GAME.equals(pageType)) {
                view = inflater.inflate(R.layout.fragment_main_no_game, container, false);
                context = view.getContext();
                initNoGamePage(view);
            } else if (Constants.PAGE_TYPE_HAVE_GAME.equals(pageType)) {
                view = inflater.inflate(R.layout.fragment_main_with_game, container, false);
                context = view.getContext();
                initHaveGamePage(view);
            }
        }

        return view;
    }

    private int offset = 0;
    private int limit = 100;

    private void initNoGamePage(View view) {
        tv_game_publish = (TextView) view.findViewById(R.id.tv_game_publish);

        tv_game_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GamePublishActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initHaveGamePage(View view) {
        lv_games = (DropDownListView) view.findViewById(R.id.lv_games);
        adapter = new GameListViewAdapter(context, list);
        lv_games.setAdapter(adapter);
        lv_games.setOnItemClickListener(mOnClickListener);

//        lv_games.setOnDropDownListener(new DropDownListView.OnDropDownListener() {
//            @Override
//            public void onDropDown() {
//                resetData();
//                loadGameList();
//            }
//        });
//
//        lv_games.setOnBottomListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                offset = offset < 1 ? 1 : offset;
//                loadGameList();
//            }
//        });

    }

    final private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "选中的活动位置 = " + position);
            if (!list.isEmpty()) {
                EntGameInfoDTO entGameInfo = list.get(position);
                Log.i(TAG, "选中的活动ID = " + entGameInfo.getId());

                Intent intent = new Intent(context, GameDetailActivity.class);
                intent.putExtra(Constants.EXTRA_SELECTED_GAME_ID, entGameInfo.getId());
                startActivityForResult(intent,GAMEDETAILREQUESTCODE);
            }
        }
    };
    public void loadGameList() {
        Map<String, Object> mParameters = new HashMap<String, Object>();
        mParameters.put("offset", offset);
        mParameters.put("limit", limit);
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_GAME_URL, mParameters);
        GsonRequest gsonRequest = new GsonRequest<EntGameInfoDTO[]>(queryUrl, EntGameInfoDTO[].class,
                new ResponseListener<EntGameInfoDTO[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, Utils.parseErrorResponse(volleyError).getCustomMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(EntGameInfoDTO[] entGameInfos) {


                        if (entGameInfos.length > 0) {
                            List<EntGameInfoDTO> result = new ArrayList<>();
                            for(EntGameInfoDTO entGameInfoDTO:entGameInfos){
                                result.add(entGameInfoDTO);
                            }
                            list.addAll(result);
                            adapter.notifyDataSetChanged();
//                            gamePageType = Constants.PAGE_TYPE_HAVE_GAME;
                        } else {
//                            gamePageType = Constants.PAGE_TYPE_NO_GAME;
                        }

//                        Log.i(TAG, "gamePageType = " + gamePageType);
//                        mPagerViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
//                        mTabsPagerSlidingTabStrip.setViewPager(mPagerViewPager);
//                        setTabsValue();
                    }
                });
        RequestManager.addRequest(gsonRequest, this);
    }
    private void resetData(){
        offset = 0;
        list.clear();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"requestCode:"+requestCode+",resultCode:"+resultCode);
        if(requestCode == GAMEDETAILREQUESTCODE){
            if(resultCode == GAMEDETAILRESULTCODE){
                resetData();
                loadGameList();
            }
        }
    }
}
