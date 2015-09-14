package com.qqzq.subitem.team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntTeamInfo;
import com.qqzq.network.RequestManager;

import java.util.List;

/**
 * Created by jie.xiao on 15/9/13.
 */
public class TeamGridViewAdapter extends BaseAdapter {

    private final Context mContext;
    public List<EntTeamInfo> mList;

    public TeamGridViewAdapter(Context context, List<EntTeamInfo> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        System.out.println("===========>getView " + position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_team_with_logo, parent, false);
            holder = new ViewHolder();
            holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_team_logo);
            holder.tv_team_name = (TextView) convertView.findViewById(R.id.tv_team_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EntTeamInfo entTeamInfo = mList.get(position);
        if (entTeamInfo != null) {
            String logoUrl = entTeamInfo.getTeamlogo();
            String teamName = entTeamInfo.getTeamname();
            System.out.println(logoUrl);
            System.out.println(teamName);
            holder.tv_team_name.setText(teamName);
            displayUrlImg(holder.iv_logo, logoUrl);
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
        ImageView iv_logo;
        TextView tv_team_name;
    }
}
