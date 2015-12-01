package com.qqzq.game.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.network.RequestManager;
import com.qqzq.team.dto.EntTeamMemberDTO;

import java.util.List;

/**
 * Created by jie.xiao on 15/10/25.
 */
public class AttendanceStatisticListViewAdapter extends BaseAdapter {

    private final static String TAG = "AttendStatisticLvAdp";
    private Context context;
    public List<EntTeamMemberDTO> mList;

    public AttendanceStatisticListViewAdapter(Context context, List<EntTeamMemberDTO> mList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_game_attendance_statistic, null);
            // 实例化一个封装类ListItemView，并实例化它的所有域
            viewHolder = new ViewHolder();
            viewHolder.iv_user_logo = (ImageView) convertView.findViewById(R.id.iv_user_logo);
            viewHolder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            viewHolder.tv_attendance_count = (TextView) convertView.findViewById(R.id.tv_attendance_count);
            viewHolder.tv_attendance_rate = (TextView) convertView.findViewById(R.id.tv_attendance_rate);
            // 将ListItemView对象传递给convertView
            convertView.setTag(viewHolder);
        } else {
            // 从converView中获取ListItemView对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntTeamMemberDTO entTeamMember = mList.get(position);
        if (entTeamMember != null) {
            String userLogo = entTeamMember.getUserLogo();
            String userName = entTeamMember.getUsername();
            int attendanceCount = entTeamMember.getAttendancecount();
            int attendanceRate = entTeamMember.getAttendancerate();

            displayUrlImg(viewHolder.iv_user_logo, userLogo);
            viewHolder.tv_user_name.setText(userName);
            viewHolder.tv_attendance_count.setText(attendanceCount + "");
            viewHolder.tv_attendance_rate.setText(attendanceRate + "%");
        }

        return convertView;
    }

    public void displayUrlImg(ImageView imageView, String url) {

        String logoUrl = Constants.FILE_SERVER_HOST + url;
        Log.i(TAG, logoUrl);

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_default_team_log, R.drawable.ic_default_team_log);
        RequestManager.getImageLoader().get(logoUrl, listener);
    }

    class ViewHolder {
        ImageView iv_user_logo;
        TextView tv_user_name;
        TextView tv_attendance_count;
        TextView tv_attendance_rate;
    }
}
