package com.qqzq.widget.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.common.activity.SelectTeamActivity;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntClientResponse;
import com.qqzq.game.activity.GamePublishActivity;
import com.qqzq.game.fragment.GameManagementFragment;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.RequestManager;
import com.qqzq.network.ResponseListener;
import com.qqzq.team.activity.CreateTeamActivity;
import com.qqzq.team.activity.FindTeamActivity;
import com.qqzq.util.Utils;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/20.
 */
public class PopupMenuWindow extends PopupWindow {

    private View layoutView; // PopupWindow 菜单布局
    private Activity context; // 上下文参数
    private View.OnClickListener myOnClick; // PopupWindow 菜单 空间单击事件
    private LinearLayout ll_game_publish;
    private LinearLayout ll_create_team;
    private LinearLayout ll_find_team;
    private int mType;
    private int mGameId;

    public PopupMenuWindow(Activity context, View.OnClickListener myOnClick,int type) {
        super(context);
        this.context = context;

        if (myOnClick == null) {
            myOnClick = itemsOnClick;
        }
        this.myOnClick = myOnClick;
        this.mType = type;
        initView();
        initListener();
    }

    public void setData(int gameId){
     this.mGameId = gameId;
    }

    public void show() {
        this.showAsDropDown(layoutView);
//        this.showAtLocation(parent, Gravity.LEFT, 0, 0);
        this.update();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(mType == 0){
            layoutView = inflater.inflate(R.layout.popup_menu_main, null);
        }else if(mType == 1){
            layoutView = inflater.inflate(R.layout.popup_menu_main_game_detail, null);
        }
        ll_game_publish = (LinearLayout) layoutView.findViewById(R.id.ll_game_publish);
        ll_create_team = (LinearLayout) layoutView.findViewById(R.id.ll_create_team);
        ll_find_team = (LinearLayout) layoutView.findViewById(R.id.ll_find_team);

        layoutView.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.fade_ins));
        layoutView.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_bottom_in_2));
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setWidth(w / 2);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setContentView(layoutView);

    }

    private void initListener() {
        ll_game_publish.setOnClickListener(myOnClick);
        ll_create_team.setOnClickListener(myOnClick);
        ll_find_team.setOnClickListener(myOnClick);
    }


    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_create_team:
                    if(mType == 0){
                        Intent createTeamIntent = new Intent(context, CreateTeamActivity.class);
                        context.startActivity(createTeamIntent);
                    }else if(mType == 1){
                        //do share
                    }
                    dismiss();
                    break;
                case R.id.ll_find_team:
                    if(mType == 0){
                        Intent findTeamIntent = new Intent(context, FindTeamActivity.class);
                        context.startActivity(findTeamIntent);
                    }else if(mType == 1){
                        doCancelGameAction();
                    }
                    dismiss();

                    break;
                case R.id.ll_game_publish:
                    if(mType == 0){
                        Intent publishGameIntent = new Intent(context, GamePublishActivity.class);
                        context.startActivity(publishGameIntent);
                    }else if(mType == 1){
                        //do cancel game

                    }
                    dismiss();
                    break;
                default:
                    break;
            }
        }

        private void doModifyGameAction(){
//            Map<String, Object> mParameters = new HashMap<String, Object>();
//            String queryUrl = MessageFormat.format(Constants.API_GAME_CANCEL_APPLICATION_URL, 1);
//            queryUrl = Utils.makeGetRequestUrl(queryUrl, mParameters);
//
//            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, queryUrl,
//                    EntClientResponse.class, null, null, joinGameResponseListener);
//            executeRequest(gsonRequest);
        }
        private void doCancelGameAction(){
            String queryUrl = MessageFormat.format(Constants.API_GAME_CANCEL_APPLICATION_URL, mGameId);

            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, queryUrl,
                    EntClientResponse.class, null, null, new ResponseListener<EntClientResponse>() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(context, Utils.parseErrorResponse(error).getCustomMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(EntClientResponse response) {
                    Toast.makeText(context, response.getCustomMessage(), Toast.LENGTH_SHORT).show();
                    context.setResult(GameManagementFragment.GAMEDETAILRESULTCODE);
                    context.finish();
                }
            });
            RequestManager.addRequest(gsonRequest,this);
        }

    };
}