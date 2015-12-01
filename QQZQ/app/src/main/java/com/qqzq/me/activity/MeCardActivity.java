package com.qqzq.me.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.base.BaseApplication;
import com.qqzq.common.dto.EntLocation;
import com.qqzq.config.Constants;
import com.qqzq.db.LocationDao;
import com.qqzq.entity.EntTeamListItem;
import com.qqzq.me.dto.EntUserInfoDTO;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.team.adapter.TeamListViewAdapter;
import com.qqzq.team.dto.EntTeamInfoDTO;
import com.qqzq.util.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jie.xiao on 15/12/1.
 */
public class MeCardActivity extends BaseActivity {
    private final static String TAG = "MeCardActivity";
    private Activity context = this;
    private ImageView mMyAvatarImageView;
    private TextView mMyNameTextView;
    private ImageView mMyGenderImageView;
    private TextView mMyLocationTextView;
    private TextView mMyBestPositionTextView;
    private TextView mQqzqIdTextView;
    private TextView mMySignatureTextView;
    private TextView mMyPlayTimesTextView;
    private TextView mMyPersonalScoreTextView;
    private TextView mMyTeamCountTextView;
    private ListView mTeamsListView;
    private LinearLayout mMainLinearLayout;
    private TextView mMyAgeTextView;

    private List<EntTeamListItem> teamList = new ArrayList<EntTeamListItem>();
    private TeamListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me_card);
        initData();
        initView();
//        initListener();
    }


    private void initView() {
        mMyAvatarImageView = (ImageView) findViewById(R.id.iv_my_avatar);
        mMyNameTextView = (TextView) findViewById(R.id.tv_my_name);
        mMyGenderImageView = (ImageView) findViewById(R.id.iv_my_gender);
        mMyAgeTextView = (TextView) findViewById(R.id.tv_my_age);
        mMyLocationTextView = (TextView) findViewById(R.id.tv_my_location);
        mMyBestPositionTextView = (TextView) findViewById(R.id.tv_my_best_position);
        mQqzqIdTextView = (TextView) findViewById(R.id.tv_qqzq_id);
        mMySignatureTextView = (TextView) findViewById(R.id.tv_my_signature);
        mMyPlayTimesTextView = (TextView) findViewById(R.id.tv_my_play_times);
        mMyPersonalScoreTextView = (TextView) findViewById(R.id.tv_my_personal_score);
        mMyTeamCountTextView = (TextView) findViewById(R.id.tv_my_team_count);
        mTeamsListView = (ListView) findViewById(R.id.lv_teams);
        mMainLinearLayout = (LinearLayout) findViewById(R.id.ll_main);
        listViewAdapter = new TeamListViewAdapter(context, teamList);
        mTeamsListView.setAdapter(listViewAdapter);
    }

    private void initData() {
        loadUserInfoFromBackend();
        loadTeamList();
    }

    private void loadUserInfoFromBackend() {
        String queryUrl = Constants.API_GET_ME_INFO_URL;
        GsonRequest gsonRequest = new GsonRequest<EntUserInfoDTO>(queryUrl, EntUserInfoDTO.class,
                new ResponseListener<EntUserInfoDTO>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(EntUserInfoDTO entUserInfoDTO) {
                        if (entUserInfoDTO != null) {
                            initForm(entUserInfoDTO);
                        }
                    }
                });
        executeRequest(gsonRequest);
    }

    private void initForm(EntUserInfoDTO entUserInfoDTO) {
        mMyNameTextView.setText(entUserInfoDTO.getNickname());
        mMyAgeTextView.setText(entUserInfoDTO.getAge() + "岁");
        mMyBestPositionTextView.setText(entUserInfoDTO.getArea());
        mQqzqIdTextView.setText(entUserInfoDTO.getQuanquanid());
        mMySignatureTextView.setText(entUserInfoDTO.getAutograph());
        mMyPersonalScoreTextView.setText(entUserInfoDTO.getPersonalscore() + "");

        LocationDao locationDao = new LocationDao(context);

        EntLocation provinceInfo = locationDao.findLocationById(String.valueOf(entUserInfoDTO.getProvince()));
        EntLocation cityInfo = locationDao.findLocationById(String.valueOf(entUserInfoDTO.getCity()));
        if (cityInfo != null && provinceInfo != null) {
            mMyLocationTextView.setText(String.valueOf(provinceInfo.getLocation() + " " + cityInfo.getLocation()));
        }
    }

    private void loadTeamList() {
        String queryUrl = MessageFormat.format(Constants.API_FIND_TEAM_MEMBER_BY_ID_URL, BaseApplication.QQZQ_USER);
        queryUrl = Utils.makeGetRequestUrl(queryUrl, null);

        GsonRequest gsonRequest = new GsonRequest<EntTeamInfoDTO[]>(queryUrl, EntTeamInfoDTO[].class,
                new ResponseListener<EntTeamInfoDTO[]>() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(EntTeamInfoDTO[] entTeamInfos) {
                        teamList.clear();
                        mMyTeamCountTextView.setText(entTeamInfos.length + "");
                        for (EntTeamInfoDTO entTeamInfo : entTeamInfos) {
                            EntTeamListItem entTeamListItem = new EntTeamListItem();
                            entTeamListItem.setTeamId(String.valueOf(entTeamInfo.getId()));
                            entTeamListItem.setLogoUrl(entTeamInfo.getTeamlogo());
                            entTeamListItem.setTeamName(entTeamInfo.getTeamname());
                            entTeamListItem.setTeamCaptain(entTeamInfo.getTeamleaderusrrnm());
                            entTeamListItem.setTeamMembers(entTeamInfo.getOftensoccerpernum());
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(entTeamInfo.getEstablishdate());
                            String establishDay = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
                            entTeamListItem.setTeamEstablishDay(establishDay);
                            teamList.add(entTeamListItem);
                        }
                        listViewAdapter.notifyDataSetChanged();
                    }
                });
        executeRequest(gsonRequest);
    }
}
