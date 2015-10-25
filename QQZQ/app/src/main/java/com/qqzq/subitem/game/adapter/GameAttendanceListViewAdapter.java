package com.qqzq.subitem.game.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.entity.EntGameInfo;
import com.qqzq.util.Utils;

import java.util.List;

/**
 * Created by syouketu on 15/10/25.
 */
public class GameAttendanceListViewAdapter extends BaseAdapter {

    private final static String TAG = "GameAttendanceListViewAdapter";
    private Context context;
    public List<EntGameInfo> mList;

    public GameAttendanceListViewAdapter(Context context, List<EntGameInfo> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // 初始化item view
        if (convertView == null) {
            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_game_attendance_manage, null);
            // 实例化一个封装类ListItemView，并实例化它的所有域
            viewHolder = new ViewHolder();
            viewHolder.tv_game_name = (TextView) convertView.findViewById(R.id.tv_game_name);
            viewHolder.tv_game_date = (TextView) convertView.findViewById(R.id.tv_game_date);
            viewHolder.tv_signup_count = (TextView) convertView.findViewById(R.id.tv_signup_count);
            viewHolder.tv_attendance_count = (TextView) convertView.findViewById(R.id.tv_attendance_count);
            // 将ListItemView对象传递给convertView
            convertView.setTag(viewHolder);
        } else {
            // 从converView中获取ListItemView对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntGameInfo entGameInfo = mList.get(position);
        if (entGameInfo != null) {
            String gameName = entGameInfo.getActtitle();
            String gameDate = Utils.getFormatedSimpleDate(entGameInfo.getActdate());
            int signupCount = entGameInfo.getSignupcount();
            int attendanceCount = entGameInfo.getAttendancecount();

            viewHolder.tv_game_name.setText(gameName);
            viewHolder.tv_game_date.setText(gameDate);
            viewHolder.tv_signup_count.setText(signupCount + "");
            viewHolder.tv_attendance_count.setText(attendanceCount + "");
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_game_name;
        TextView tv_game_date;
        TextView tv_signup_count;
        TextView tv_attendance_count;
    }
}
