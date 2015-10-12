package com.qqzq.subitem.game.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntGameInfo;
import com.qqzq.entity.EntTeamListItem;
import com.qqzq.network.RequestManager;
import com.qqzq.util.Utils;

import java.util.List;

/**
 * Created by jie.xiao on 15/9/13.
 */
public class GameListViewAdapter extends BaseAdapter {

    private final static String TAG = "GameListViewAdapter";

    private Context context;
    public List<EntGameInfo> mList;
    public int pageIdx = 0;
    public boolean hasDetail = true;

    public GameListViewAdapter(Context context, List<EntGameInfo> mList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_game, null);
            // 实例化一个封装类ListItemView，并实例化它的所有域
            viewHolder = new ViewHolder();
            viewHolder.iv_logo = (ImageView) convertView.findViewById(R.id.iv_game_logo);
            viewHolder.tv_game_name = (TextView) convertView.findViewById(R.id.tv_game_name);
            viewHolder.tv_game_address = (TextView) convertView.findViewById(R.id.tv_game_address);
            viewHolder.tv_game_date = (TextView) convertView.findViewById(R.id.tv_game_date);
            viewHolder.tv_soccer_person = (TextView) convertView.findViewById(R.id.tv_soccer_person);
            viewHolder.btn_game_join = (Button) convertView.findViewById(R.id.btn_game_join);
            // 将ListItemView对象传递给convertView
            convertView.setTag(viewHolder);
        } else {
            // 从converView中获取ListItemView对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntGameInfo entGameInfo = mList.get(position);
        if (entGameInfo != null) {
            Drawable logo = null;
//            Drawable logo = entGameInfo.getLogo();
//            String logoUrl = entTeamListItem.getLogoUrl();
            String gameName = entGameInfo.getActtitle();
            String gameAddress = entGameInfo.getActaddress();
            String gameDate = Utils.getFormatedSimpleDate(entGameInfo.getActdate());
            String soccerPerson = entGameInfo.getSoccerpersonnum() + "人制";

            if (logo != null) {
                viewHolder.iv_logo.setBackgroundResource(R.drawable.ic_default_team_log);
            } else {
//                displayUrlImg(viewHolder.iv_logo, logoUrl);
            }
            viewHolder.tv_game_name.setText(gameName);
            viewHolder.tv_game_address.setText(gameAddress);
            viewHolder.tv_game_date.setText(gameDate);
            viewHolder.tv_soccer_person.setText(soccerPerson);
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
        ImageView iv_logo;
        TextView tv_game_name;
        TextView tv_game_address;
        TextView tv_game_date;
        TextView tv_soccer_person;
        Button btn_game_join;
    }
}
