package com.qqzq.subitem.game.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jie.xiao on 15/9/13.
 */
public class GameListViewAdapter extends BaseAdapter{

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    class ViewHolder {
        ImageView iv_logo;
        TextView tv_game_addres;
        TextView tv_game_time;
        TextView tv_game_type;
        TextView tv_game_publisher;
    }
}
