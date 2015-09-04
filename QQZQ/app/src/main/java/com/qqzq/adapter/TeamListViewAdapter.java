package com.qqzq.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.entity.EntTeamListItem;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by syouketu on 15/9/5.
 */
public class TeamListViewAdapter extends BaseAdapter {

    private List<EntTeamListItem> mList;
    private Activity activity;

    public TeamListViewAdapter(Activity activity, List<EntTeamListItem> mList) {
        this.activity = activity;
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
        ListItemView listItemView;

        // 初始化item view
        if (convertView == null) {
            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
            convertView = LayoutInflater.from(activity).inflate(R.layout.team_list_item, null);
            // 实例化一个封装类ListItemView，并实例化它的所有域
            listItemView = new ListItemView();
            listItemView.iv_logo = (ImageView) convertView.findViewById(R.id.iv_team_list_logo);
            listItemView.tv_team_name = (TextView) convertView.findViewById(R.id.tv_team_name);
            listItemView.tv_team_captain = (TextView) convertView.findViewById(R.id.tv_team_captain);
            listItemView.tv_team_members = (TextView) convertView.findViewById(R.id.tv_team_members);
            listItemView.tv_team_establish_day = (TextView) convertView.findViewById(R.id.tv_team_establish_day);
            // 将ListItemView对象传递给convertView
            convertView.setTag(listItemView);
        } else {
            // 从converView中获取ListItemView对象
            listItemView = (ListItemView) convertView.getTag();
        }

        EntTeamListItem entTeamListItem = mList.get(position);
        if (entTeamListItem != null) {
            Drawable logo = entTeamListItem.getLogo();
            String teamName = entTeamListItem.getTeamName();
            String teamCaptain = entTeamListItem.getTeamCaptain();
            String teamMembers = entTeamListItem.getTeamMembers();
            String teamEstablishDay = entTeamListItem.getTeamEstablishDay();

            if (logo != null) {
                listItemView.iv_logo.setImageDrawable(logo);
            }
            listItemView.tv_team_name.setText(activity.getResources().getString(R.string.find_team_team_name) + teamName);
            listItemView.tv_team_captain.setText(activity.getResources().getString(R.string.find_team_team_captain) + teamCaptain);
            listItemView.tv_team_members.setText(activity.getResources().getString(R.string.find_team_team_members) + teamMembers);
            listItemView.tv_team_establish_day.setText(activity.getResources().getString(R.string.find_team_establish_day) + teamEstablishDay);
        }

        return convertView;
    }

    class ListItemView {
        ImageView iv_logo;
        TextView tv_team_name;
        TextView tv_team_captain;
        TextView tv_team_members;
        TextView tv_team_establish_day;
    }
}
