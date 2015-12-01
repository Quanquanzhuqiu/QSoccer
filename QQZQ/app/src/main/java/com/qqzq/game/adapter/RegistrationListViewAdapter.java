package com.qqzq.game.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.team.dto.EntTeamMemberDTO;

import java.util.List;

/**
 * Created by jie.xiao on 15/10/14.
 */
public class RegistrationListViewAdapter extends BaseAdapter {

    private final static String TAG = "RegistrationListViewAdapter";

    private Context context;
    public List<EntTeamMemberDTO> mList;

    public RegistrationListViewAdapter(Context context, List<EntTeamMemberDTO> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * 返回item的内容
     */
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }


    /**
     * 返回item的id
     */
    @Override
    public long getItemId(int position) {
        return mList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // 初始化item view
        if (convertView == null) {
            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_game_registration, null);
            // 实例化一个封装类ListItemView，并实例化它的所有域
            viewHolder = new ViewHolder();
            viewHolder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
            viewHolder.tv_gamer_name = (TextView) convertView.findViewById(R.id.tv_gamer_name);
            viewHolder.tv_gamer_attendance_count = (TextView) convertView.findViewById(R.id.tv_gamer_attendance_count);
            viewHolder.tv_gamer_points = (TextView) convertView.findViewById(R.id.tv_gamer_points);
            // 将ListItemView对象传递给convertView
            convertView.setTag(viewHolder);
        } else {
            // 从converView中获取ListItemView对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntTeamMemberDTO entTeamMember = mList.get(position);
        String gamerName = entTeamMember.getUsernickname();
        int attendanceCount = entTeamMember.getAttendancecount();
        int personalScore = entTeamMember.getPersonalscore();

        if (entTeamMember != null) {
            viewHolder.tv_gamer_name.setText(gamerName);
            viewHolder.tv_gamer_attendance_count.setText(attendanceCount);
            viewHolder.tv_gamer_points.setText(personalScore);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView iv_logo;
        TextView tv_gamer_name;
        TextView tv_gamer_attendance_count;
        TextView tv_gamer_points;
    }
}
