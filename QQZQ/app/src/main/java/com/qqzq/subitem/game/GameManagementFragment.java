package com.qqzq.subitem.game;

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

import com.qqzq.R;
import com.qqzq.activity.BaseFragment;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntGameInfo;
import com.qqzq.subitem.game.activity.GameDetailActivity;
import com.qqzq.subitem.game.activity.GamePublishActivity;
import com.qqzq.subitem.game.adapter.GameListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class GameManagementFragment extends BaseFragment {

    private final static String TAG = "GameManagementFragment";

    private Context context;
    private TextView tv_game_publish;
    private String pageType = "";
    private ListView lv_games;
    private GameListViewAdapter adapter;
    public static List<EntGameInfo> list = new ArrayList<EntGameInfo>();

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
        lv_games = (ListView) view.findViewById(R.id.lv_games);
        adapter = new GameListViewAdapter(context, list);
        lv_games.setAdapter(adapter);
        lv_games.setOnItemClickListener(mOnClickListener);
    }

    final private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "选中的活动位置 = " + position);
            if (!list.isEmpty()) {
                EntGameInfo entGameInfo = list.get(position);
                Log.i(TAG, "选中的活动ID = " + entGameInfo.getId());

                Intent intent = new Intent(context, GameDetailActivity.class);
                intent.putExtra(Constants.EXTRA_SELECTED_GAME_ID, entGameInfo.getId());
                startActivity(intent);
            }
        }
    };

}
