package com.qqzq.subitem.game;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.activity.BaseFragment;
import com.qqzq.config.Constants;
import com.qqzq.subitem.game.activity.GamePublishActivity;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class GameManagementFragment extends BaseFragment {
    String pageType = "";

    private Context context;
    private TextView tv_game_publish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.EXTRA_PAGE_TYEP)) {
            pageType = bundle.getString(Constants.EXTRA_PAGE_TYEP);
            if (Constants.PAGE_TYPE_NO_GAME.equals(pageType)) {
                view = inflater.inflate(R.layout.fragment_main_no_game, container, false);
                context = view.getContext();
                initNoGamePage(view);
            } else if (Constants.PAGE_TYPE_HAVE_GAME.equals(pageType)) {
                view = inflater.inflate(R.layout.fragment_main_with_team, container, false);
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

    }
}
