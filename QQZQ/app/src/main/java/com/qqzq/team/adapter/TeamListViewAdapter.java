package com.qqzq.team.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.qqzq.entity.EntTeamListItem;
import com.qqzq.network.RequestManager;

import java.util.List;

/**
 * Created by jie.xiao on 15/9/5.
 */
public class TeamListViewAdapter extends BaseAdapter {

    private final static String TAG = "TeamListViewAdp";
    private Context context;
    public List<EntTeamListItem> mList;
    public int pageIdx = 0;
    public boolean hasDetail = true;

    public TeamListViewAdapter(Context context, List<EntTeamListItem> mList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_team, null);
            // 实例化一个封装类ListItemView，并实例化它的所有域
            viewHolder = new ViewHolder();
            viewHolder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_team_list_logo);
            viewHolder.tv_team_name = (TextView) convertView.findViewById(R.id.tv_team_name);
            viewHolder.tv_team_captain = (TextView) convertView.findViewById(R.id.tv_team_captain);
            viewHolder.tv_team_members = (TextView) convertView.findViewById(R.id.tv_team_members);
            viewHolder.tv_team_establish_day = (TextView) convertView.findViewById(R.id.tv_team_establish_day);
            viewHolder.iv_team_detail = (ImageView) convertView.findViewById(R.id.iv_team_detail);
            // 将ListItemView对象传递给convertView
            convertView.setTag(viewHolder);
        } else {
            // 从converView中获取ListItemView对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntTeamListItem entTeamListItem = mList.get(position);
        if (entTeamListItem != null) {
            Drawable logo = entTeamListItem.getLogo();
            String logoUrl = entTeamListItem.getLogoUrl();
            String teamName = entTeamListItem.getTeamName();
            String teamCaptain = entTeamListItem.getTeamCaptain();
            String teamMembers = entTeamListItem.getTeamMembers();
            String teamEstablishDay = entTeamListItem.getTeamEstablishDay();

            if (logo != null) {
                viewHolder.iv_logo.setImageDrawable(logo);
            } else {
                displayUrlImg(viewHolder.iv_logo, logoUrl);
            }
            viewHolder.tv_team_name.setText(teamName);
            viewHolder.tv_team_captain.setText(teamCaptain);
            viewHolder.tv_team_members.setText(teamMembers);
            viewHolder.tv_team_establish_day.setText(teamEstablishDay);
            viewHolder.iv_team_detail.setVisibility(hasDetail ? View.VISIBLE : View.GONE);
        }

        return convertView;
    }

    public void addItem(EntTeamListItem item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    public void setHasDetail(boolean hasDetail) {
        this.hasDetail = hasDetail;
    }

    public void displayUrlImg(ImageView imageView, String url) {

        String logoUrl = Constants.FILE_SERVER_HOST + url;
        Log.i(TAG, logoUrl);

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_default_team_log, R.drawable.ic_default_team_log);
        RequestManager.getImageLoader().get(logoUrl, listener);
    }


    class ViewHolder {
        ImageView iv_logo;
        TextView tv_team_name;
        TextView tv_team_captain;
        TextView tv_team_members;
        TextView tv_team_establish_day;
        ImageView iv_team_detail;
    }
}
