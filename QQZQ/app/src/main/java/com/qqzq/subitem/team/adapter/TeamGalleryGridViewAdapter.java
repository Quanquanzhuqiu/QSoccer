package com.qqzq.subitem.team.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.entity.EntTeamOperation;

import java.util.List;

/**
 * Created by jie.xiao on 15/9/13.
 */
public class TeamGalleryGridViewAdapter extends BaseAdapter {

    private final Context mContext;
    public List<EntTeamOperation> mList;

    public TeamGalleryGridViewAdapter(Context context, List<EntTeamOperation> list) {
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
        return mList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gv_team_gallery, parent, false);
            holder = new ViewHolder();
            holder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EntTeamOperation entTeamOperation = mList.get(position);
        if (entTeamOperation != null) {
            Drawable logo = entTeamOperation.getLogo();
            holder.iv_logo.setImageDrawable(logo);
        }
        return convertView;
    }


    class ViewHolder {
        ImageView iv_logo;
    }
}
