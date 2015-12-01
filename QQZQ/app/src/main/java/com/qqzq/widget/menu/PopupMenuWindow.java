package com.qqzq.widget.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.qqzq.R;
import com.qqzq.common.activity.SelectTeamActivity;
import com.qqzq.game.activity.GamePublishActivity;
import com.qqzq.team.activity.CreateTeamActivity;
import com.qqzq.team.activity.FindTeamActivity;

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

    public PopupMenuWindow(Activity context, View.OnClickListener myOnClick) {
        super(context);
        this.context = context;

        if (myOnClick == null) {
            myOnClick = itemsOnClick;
        }
        this.myOnClick = myOnClick;
        initView();
        initListener();
    }

    public void show() {
        this.showAsDropDown(layoutView);
//        this.showAtLocation(parent, Gravity.LEFT, 0, 0);
        this.update();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutView = inflater.inflate(R.layout.popup_menu_main, null);
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
                    Intent createTeamIntent = new Intent(context, CreateTeamActivity.class);
                    context.startActivity(createTeamIntent);
                    break;
                case R.id.ll_find_team:
                    Intent findTeamIntent = new Intent(context, FindTeamActivity.class);
                    context.startActivity(findTeamIntent);
                    break;
                case R.id.ll_game_publish:
                    Intent publishGameIntent = new Intent(context, GamePublishActivity.class);
                    context.startActivity(publishGameIntent);
                    break;
                default:
                    break;
            }
        }
    };
}