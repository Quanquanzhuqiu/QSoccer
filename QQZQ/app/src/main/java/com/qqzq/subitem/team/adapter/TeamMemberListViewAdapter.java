package com.qqzq.subitem.team.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamMember;
import com.qqzq.network.RequestManager;
import com.qqzq.util.Utils;

import java.util.List;

/**
 * Created by jie.xiao on 15/9/27.
 */
public class TeamMemberListViewAdapter extends BaseAdapter {

    private Context context;
    public List<EntTeamMember> mList;

    public TeamMemberListViewAdapter(Context context, List<EntTeamMember> mList) {
        this.context = context;
        this.mList = mList;
    }

    /**
     * 返回item的个数
     */
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
        return position;
    }

    /**
     * 返回item的视图
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // 初始化item view
        if (convertView == null) {
            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_team_member, null);
            // 实例化一个封装类ListItemView，并实例化它的所有域
            viewHolder = new ViewHolder();
            viewHolder.iv_memeber_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
            viewHolder.tv_member_name = (TextView) convertView.findViewById(R.id.tv_member_name);
            viewHolder.tv_member_attendance_count = (TextView) convertView.findViewById(R.id.tv_member_attendance_count);
            viewHolder.tv_member_personal_score = (TextView) convertView.findViewById(R.id.tv_member_personal_score);
            viewHolder.tv_member_join_date = (TextView) convertView.findViewById(R.id.tv_member_join_date);
            viewHolder.cbox_memeber_selected = (CheckBox) convertView.findViewById(R.id.cbox_memeber_selected);
            // 将ListItemView对象传递给convertView
            convertView.setTag(viewHolder);
        } else {
            // 从converView中获取ListItemView对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntTeamMember entTeamMember = mList.get(position);
        if (entTeamMember != null) {
//            Drawable logo = entTeamMember.getLogo();
//            String logoUrl = entTeamMember.getLogoUrl();
            Drawable logo = null;
            String logoUrl = null;
            String name = entTeamMember.getUsername();
            String attendanceCount = entTeamMember.getAttendancecount() + "";
            String personalscore = entTeamMember.getPersonalscore() + "";
            String joinDate = Utils.getFormatedSimpleDate(entTeamMember.getJointime());

            if (logo != null) {
                viewHolder.iv_memeber_logo.setImageDrawable(logo);
            } else if (!TextUtils.isEmpty(logoUrl)) {
                displayUrlImg(viewHolder.iv_memeber_logo, logoUrl);
            }
            viewHolder.tv_member_name.setText(name);
            viewHolder.tv_member_attendance_count.setText(attendanceCount);
            viewHolder.tv_member_personal_score.setText(personalscore);
            viewHolder.tv_member_join_date.setText(joinDate);
        }

        return convertView;
    }

    public void displayUrlImg(ImageView imageView, String url) {

        String logoUrl = Constants.FILE_SERVER_HOST + url;
        System.out.println(logoUrl);

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_default_team_log, R.drawable.ic_default_team_log);
        RequestManager.getImageLoader().get(logoUrl, listener);
    }

    class ViewHolder {
        ImageView iv_memeber_logo;
        TextView tv_member_name;
        TextView tv_member_attendance_count;
        TextView tv_member_personal_score;
        TextView tv_member_join_date;
        CheckBox cbox_memeber_selected;
    }
}
