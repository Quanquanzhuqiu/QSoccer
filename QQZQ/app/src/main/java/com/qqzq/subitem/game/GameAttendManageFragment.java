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
import com.qqzq.subitem.game.activity.GameAttendanceDetailActivity;
import com.qqzq.subitem.game.adapter.GameAttendanceListViewAdapter;
import com.qqzq.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xiao on 15/10/24.
 */
public class GameAttendManageFragment extends BaseFragment {

    private final static String TAG = "AttendanceMgrFragment";
    private Context context;
    private ListView lv_attendance_manage;
    private GameAttendanceListViewAdapter adapter;
    private TextView tv_attendance_hint;

    public static List<EntGameInfo> list = new ArrayList<EntGameInfo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_manage, container, false);
        context = view.getContext();
        init(view);
        return view;
    }

    private void init(View view) {
        lv_attendance_manage = (ListView) view.findViewById(R.id.lv_attendance_manage);
        adapter = new GameAttendanceListViewAdapter(context, list);
        lv_attendance_manage.setAdapter(adapter);
        lv_attendance_manage.setOnItemClickListener(mOnClickListener);
    }


    final private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "选中的活动位置 = " + position);
            if (!list.isEmpty()) {
                EntGameInfo entGameInfo = list.get(position);
                Log.i(TAG, "选中的活动ID = " + entGameInfo.getId());

                Intent intent = new Intent(context, GameAttendanceDetailActivity.class);
                intent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, entGameInfo.getTeamid());
                intent.putExtra(Constants.EXTRA_SELECTED_TEAM_NAME, entGameInfo.getTeamname());
                intent.putExtra(Constants.EXTRA_SELECTED_GAME_ID, entGameInfo.getId());
                intent.putExtra(Constants.EXTRA_SELECTED_GAME_NAME, entGameInfo.getActtitle());
                intent.putExtra(Constants.EXTRA_SELECTED_GAME_DATE, Utils.getFormatedSimpleDate(entGameInfo.getActdate()));
                startActivity(intent);
            }
        }
    };
}
