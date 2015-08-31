package com.qqzq.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qqzq.R;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class MyTeamFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.fragment_my_team, container, false);
        return layout;
    }
}
