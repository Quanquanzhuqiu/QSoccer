package com.qqzq.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.game.dto.EntGameInfoDTO;
import com.qqzq.listener.TopBarListener;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;
import com.qqzq.widget.menu.TopBar;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by jie.xiao on 15/10/13.
 */
public class GameDetailActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "GameDetailActivity";
    private final static String GAME_DETAIL_TITLE_FORMAT = "[{0}]{1}";
    private Activity context = this;

    private int selectedGameId;
    private TextView mTeamGameNameTextView;
    private TextView mPublisherTextView;
    private Button mJoinGameButton;
    private ImageView mMoreInfoImageView;
    private TextView mAddressTextView;
    private TextView mTypeTextView;
    private TextView mDateTextView;
    private TextView mTimeTextView;
    private TextView mCostTextView;
    private TextView mPayTypeTextView;
    private TextView mDescTextView;
    private LinearLayout mCommentLinearLayout;
    private LinearLayout mContactGamePublisherLinearLayout;
    private TextView mJoinedNoTextView;
    private TextView mLeftNoTextView ;
    private TopBar mTopBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mTeamGameNameTextView = (TextView) findViewById(R.id.tv_team_game_name);
        mPublisherTextView = (TextView) findViewById(R.id.tv_game_publisher);
        mJoinGameButton = (Button) findViewById(R.id.btn_join_game);
        mMoreInfoImageView = (ImageView) findViewById(R.id.iv_more_info);
        mAddressTextView = (TextView) findViewById(R.id.tv_game_address);
        mTypeTextView = (TextView) findViewById(R.id.tv_game_type);
        mDateTextView = (TextView) findViewById(R.id.tv_game_date);
        mTimeTextView = (TextView) findViewById(R.id.tv_game_time);
        mCostTextView = (TextView) findViewById(R.id.tv_game_cost);
        mPayTypeTextView = (TextView) findViewById(R.id.tv_game_pay_type);
        mDescTextView = (TextView) findViewById(R.id.tv_game_desc);
        mCommentLinearLayout = (LinearLayout) findViewById(R.id.ll_game_comment);
        mContactGamePublisherLinearLayout = (LinearLayout) findViewById(R.id.ll_contact_game_publisher);
        mJoinedNoTextView = (TextView) findViewById(R.id.tv_game_joinedNo);
        mLeftNoTextView = (TextView) findViewById(R.id.tv_game_leftNo);
        mTopBar = (TopBar) findViewById(R.id.topbar);

    }


    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_GAME_ID)) {

            selectedGameId = extras.getInt(Constants.EXTRA_SELECTED_GAME_ID, 0);
            mTopBar.setData(selectedGameId);
            if (selectedGameId > 0) {
                loadGameDetailFromBackend(selectedGameId);
            }
        }
    }

    private void initListener() {
        mJoinGameButton.setOnClickListener(this);
        mTopBar.setListener(new TopBarListener() {
            @Override
            public void leftButtonClick() {
                finish();
            }

            @Override
            public void rightButtonClick() {

            }

            @Override
            public int getButtonType() {
                return 0;
            }
        });
    }

    private void initForm(EntGameInfoDTO entGameInfo) {
        String actTitle = entGameInfo.getActtitle();
        String gamePublisher = entGameInfo.getPublisher();
        String gameAddress = entGameInfo.getActaddress();
        String gameTypeName = entGameInfo.getActTypeName();
        Date gameDate = entGameInfo.getActdate();
        int gameCost = entGameInfo.getCost();
        String payTypeName = entGameInfo.getActPayTypeName();
        String gameDesc = entGameInfo.getStat();

        String gameDateWithWeek = Utils.getStrDateWithWeek(gameDate);
        String gameTime = Utils.getStrTime(gameDate);
        String teamName = entGameInfo.getTeamname();
        teamName = TextUtils.isEmpty(teamName) ? "公开活动" : teamName;

        String gameDetailTitle = MessageFormat.format(GAME_DETAIL_TITLE_FORMAT, teamName, actTitle);
        mTeamGameNameTextView.setText(gameDetailTitle);
        mPublisherTextView.setText(gamePublisher);
        mAddressTextView.setText(gameAddress);
        mTypeTextView.setText(gameTypeName);
        mDateTextView.setText(gameDateWithWeek);
        mTimeTextView.setText(gameTime);
        mCostTextView.setText(gameCost + "");
        mPayTypeTextView.setText(payTypeName);
        mDescTextView.setText(gameDesc);

        mJoinedNoTextView.setText(entGameInfo.getSignupcount() + "");
        int leftCount = entGameInfo.getPersonmaxlimit() - entGameInfo.getSignupcount() > 0 ?
                entGameInfo.getPersonmaxlimit() - entGameInfo.getSignupcount():0;
        mLeftNoTextView.setText(leftCount + "");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_join_game:
                Log.i(TAG, "点击报名加入");
                joinGame(selectedGameId);
                break;
        }
    }

    private void joinGame(int id) {
        String queryUrl = MessageFormat.format(Constants.API_GAME_JOIN_APPLICATION_URL, id);

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, queryUrl,
                EntClientResponse.class, null, null, joinGameResponseListener);
        executeRequest(gsonRequest);
    }

    private void loadGameDetailFromBackend(int id) {
        String queryUrl = MessageFormat.format(Constants.API_FIND_GAME_BY_ID_URL, id);
        GsonRequest gsonRequest = new GsonRequest<EntGameInfoDTO>(queryUrl, EntGameInfoDTO.class,
                findGameResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findGameResponseListener = new ResponseListener<EntGameInfoDTO>() {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntGameInfoDTO response) {
            initForm(response);
        }

    };

    ResponseListener joinGameResponseListener = new ResponseListener<EntClientResponse>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntClientResponse response) {
            context.finish();
        }
    };

}
