package com.qqzq.subitem.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.activity.BaseFragment;
import com.qqzq.common.Constants;

/**
 * Created by jie.xiao on 15/9/12.
 */
public class GameManagementFragment extends BaseFragment {
    String pageType = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.EXTRA_PAGE_TYEP)) {
            pageType = bundle.getString(Constants.EXTRA_PAGE_TYEP);
            if (Constants.PAGE_TYPE_NO_GAME.equals(pageType)) {
                view = inflater.inflate(R.layout.fragment_main_no_game, container, false);
            } else if (Constants.PAGE_TYPE_HAVE_GAME.equals(pageType)) {
                view = inflater.inflate(R.layout.fragment_main_with_team, container, false);
            }
        }

        return view;
    }
}
