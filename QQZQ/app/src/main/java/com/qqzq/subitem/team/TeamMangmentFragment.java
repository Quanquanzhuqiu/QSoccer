package com.qqzq.subitem.team;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseFragment;
import com.qqzq.common.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.subitem.find.activity.FindTeamActivity;
import com.qqzq.subitem.team.activity.CreateTeamActivity;
import com.qqzq.subitem.team.adapter.TeamGridViewAdapter;
import com.qqzq.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 8/25/2015.
 */
public class TeamMangmentFragment extends BaseFragment {

    String pageType = "";

    private Context context;
    private GridView gv_team;
    private TeamGridViewAdapter teamGridViewAdapter;
    private List<EntTeamInfo> list = new ArrayList<EntTeamInfo>();

    private TextView tv_create_team;
    private TextView tv_find_team;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constants.EXTRA_PAGE_TYEP)) {
            pageType = bundle.getString(Constants.EXTRA_PAGE_TYEP);
            if (Constants.PAGE_TYPE_NO_TEAM.equals(pageType)) {
                System.out.println("没有加入任何球队");
                view = inflater.inflate(R.layout.fragment_main_no_team, container, false);
                context = view.getContext();
                initNoTeamPage(view);
            } else if (Constants.PAGE_TYPE_HAVE_TEAM.equals(pageType)) {
                System.out.println("已加入球队");
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
                Intent intent = new Intent(context, FindTeamActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initHaveTeamPage(View view) {
        gv_team = (GridView) view.findViewById(R.id.gv_team);
        loadTeamListFromBackend();
    }


    public void loadTeamListFromBackend() {
        Map<String, Object> mParameters = new HashMap<>();
        mParameters.put("offset", 0);
        mParameters.put("limit", Constants.PAGE_SIZE);
        mParameters.put("teamLeaderUsrNm", "13551063785");
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_TEAM_URL, mParameters);
        GsonRequest gsonRequest = new GsonRequest<EntTeamInfo[]>(queryUrl, EntTeamInfo[].class,
                findTeamResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findTeamResponseListener = new ResponseListener<EntTeamInfo[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            System.out.println(volleyError);
        }

        @Override
        public void onResponse(EntTeamInfo[] entTeamInfos) {
            list = Arrays.asList(entTeamInfos);
            for (EntTeamInfo entTeamInfo : list) {
                System.out.println(entTeamInfo);
            }

            teamGridViewAdapter = new TeamGridViewAdapter(getActivity(), list);
            gv_team.setAdapter(teamGridViewAdapter);
        }
    };
}
