package com.qqzq.subitem.game;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qqzq.R;
import com.qqzq.activity.BaseFragment;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.subitem.game.adapter.AttendanceStatisticListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xiao on 15/10/24.
 */
public class AttendanceStatisticsFragment extends BaseFragment {

    private final static String TAG = "AttendanceStatisticsFragment";
    private Context context;
    private ListView lv_attendance_statistic;
    private AttendanceStatisticListViewAdapter adapter;
    public static List<EntTeamMember> list = new ArrayList<EntTeamMember>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_statistic, container, false);
        context = view.getContext();
        init(view);
        return view;
    }

    private void init(View view) {
        lv_attendance_statistic = (ListView) view.findViewById(R.id.lv_attendance_statistic);
        adapter = new AttendanceStatisticListViewAdapter(context, list);
        lv_attendance_statistic.setAdapter(adapter);
    }

}
