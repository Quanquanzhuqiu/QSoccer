package com.qqzq.team.fragemnt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.base.BaseFragment;
import com.qqzq.common.activity.SelectTeamActivity;
import com.qqzq.config.Constants;
import com.qqzq.team.activity.CreateTeamActivity;
import com.qqzq.team.activity.TeamDetailActivity;
import com.qqzq.team.adapter.TeamGridViewAdapter;
import com.qqzq.team.dto.EntTeamInfoDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class TeamManageFragment extends BaseFragment {

    private final static String TAG = "TeamMangementFragment";
    String pageType = "";

    private Context context;
    private GridView gv_team;
    private TeamGridViewAdapter teamGridViewAdapter;
    public static List<EntTeamInfoDTO> list = new ArrayList<EntTeamInfoDTO>();

    private TextView tv_create_team;
    private TextView tv_find_team;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.EXTRA_PAGE_TYPE)) {
            pageType = bundle.getString(Constants.EXTRA_PAGE_TYPE);
            if (Constants.PAGE_TYPE_NO_TEAM.equals(pageType)) {
                Log.i(TAG, "没有加入任何球队");
                view = inflater.inflate(R.layout.fragment_main_no_team, container, false);
                context = view.getContext();
                initNoTeamPage(view);
            } else if (Constants.PAGE_TYPE_HAVE_TEAM.equals(pageType)) {
                Log.i(TAG, "已加入球队");
                view = inflater.inflate(R.layout.fragment_main_with_team, container, false);
                context = view.getContext();
                initHaveTeamPage(view);
            }
        }

        return view;
    }

    private void initNoTeamPage(View view) {
        tv_create_team = (TextView) view.findViewById(R.id.tv_create_team);
        tv_find_team = (TextView) view.findViewById(R.id.tv_find_team);

        tv_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateTeamActivity.class);
                startActivity(intent);
            }
        });

        tv_find_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectTeamActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initHaveTeamPage(View view) {
        gv_team = (GridView) view.findViewById(R.id.gv_team);
        teamGridViewAdapter = new TeamGridViewAdapter(getActivity(), list);
        gv_team.setAdapter(teamGridViewAdapter);
        gv_team.setOnItemClickListener(mOnClickListener);
    }

    final private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "选中的球队位置 = " + position);
            if (!list.isEmpty()) {
                EntTeamInfoDTO entTeamInfo = list.get(position);

                Intent intent = new Intent(context, TeamDetailActivity.class);
                intent.putExtra(Constants.EXTRA_SELECTED_TEAM_ID, entTeamInfo.getId());
                startActivity(intent);
            }
        }
    };
}
