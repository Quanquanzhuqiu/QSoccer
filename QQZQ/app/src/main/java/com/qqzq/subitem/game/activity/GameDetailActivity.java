package com.qqzq.subitem.game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.activity.BaseActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntGameInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;

import java.text.MessageFormat;

/**
 * Created by jie.xiao on 15/10/13.
 */
public class GameDetailActivity extends BaseActivity {

    private final static String TAG = "GameDetailActivity";
    private Activity context = this;

    private int selectedGameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        initData();
    }


    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras != null
                && extras.containsKey(Constants.EXTRA_SELECTED_GAME_ID)) {

            selectedGameId = extras.getInt(Constants.EXTRA_SELECTED_GAME_ID, 0);
            if (selectedGameId > 0) {
                loadGameDetailFromBackend(selectedGameId);
            }
        }
    }

    private void loadGameDetailFromBackend(int id) {
        String queryUrl = MessageFormat.format(Constants.API_FIND_GAME_BY_ID_URL, id);
        GsonRequest gsonRequest = new GsonRequest<EntGameInfo>(queryUrl, EntGameInfo.class,
                findGameResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findGameResponseListener = new ResponseListener<EntGameInfo>() {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e(TAG, volleyError.toString());
            Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResponse(EntGameInfo response) {

        }

    };
}
