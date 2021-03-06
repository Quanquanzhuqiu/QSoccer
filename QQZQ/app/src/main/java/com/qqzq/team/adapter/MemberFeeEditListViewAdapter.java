package com.qqzq.team.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.qqzq.R;
import com.qqzq.config.Constants;
import com.qqzq.network.RequestManager;
import com.qqzq.team.dto.EntTeamMemberDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 11/4/2015.
 */
public class MemberFeeEditListViewAdapter extends BaseAdapter {

    private Context context;
    public List<EntTeamMemberDTO> mList;
    Map<Integer, Float> balanceMap = new HashMap<Integer, Float>();

    public MemberFeeEditListViewAdapter(Context context, List<EntTeamMemberDTO> mList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // 初始化item view
        if (convertView == null) {
            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_member_fee_edit, null);
            // 实例化一个封装类ListItemView，并实例化它的所有域
            viewHolder = new ViewHolder();
            viewHolder.iv_member_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
            viewHolder.tv_member_name = (TextView) convertView.findViewById(R.id.tv_member_name);
            viewHolder.edt_member_fee = (EditText) convertView.findViewById(R.id.edt_member_fee);
            // 将ListItemView对象传递给convertView
            convertView.setTag(viewHolder);
        } else {
            // 从converView中获取ListItemView对象
            viewHolder = (ViewHolder) convertView.getTag();
        }

        EntTeamMemberDTO entTeamMember = mList.get(position);
        if (entTeamMember != null) {
//            Drawable logo = entTeamMember.getLogo();
//            String logoUrl = entTeamMember.getLogoUrl();
            Drawable logo = null;
            String logoUrl = null;
            final int memberId = entTeamMember.getUserid();
            String name = entTeamMember.getUsername();
            String balance = entTeamMember.getPersonalbalance() + "";

            if (logo != null) {
                viewHolder.iv_member_logo.setImageDrawable(logo);
            } else if (!TextUtils.isEmpty(logoUrl)) {
                displayUrlImg(viewHolder.iv_member_logo, logoUrl);
            }
            viewHolder.tv_member_name.setText(name);

            //为editText设置TextChangedListener，每次改变的值设置到hashMap
            //我们要拿到里面的值根据position拿值
            viewHolder.edt_member_fee.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //将editText中改变的值设置的HashMap中
                    balanceMap.put(memberId, Float.valueOf(s.toString()));
                }
            });
        }

        return convertView;
    }

    public void displayUrlImg(ImageView imageView, String url) {

        String logoUrl = Constants.FILE_SERVER_HOST + url;
        System.out.println(logoUrl);

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_default_team_log, R.drawable.ic_default_team_log);
        RequestManager.getImageLoader().get(logoUrl, listener);
    }

    public Map<Integer, Float> getSelectedData() {
        return balanceMap;
    }

    class ViewHolder {
        ImageView iv_member_logo;
        TextView tv_member_name;
        EditText edt_member_fee;
    }

}
