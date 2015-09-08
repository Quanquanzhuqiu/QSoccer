package com.qqzq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.entity.EntLocationInfo;

import java.util.List;

/**
 * Created by jie.xiao on 15/9/8.
 */
public class LocationListViewAdapter extends ArrayAdapter<EntLocationInfo> {

    protected LayoutInflater mInflater;
    private static final int mLayout = R.layout.location_list_item;

    public LocationListViewAdapter(Context context, EntLocationInfo[] locationInfos) {
        super(context, mLayout, locationInfos);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv_locaiton;

        View view;
        if (convertView == null) {
            view = mInflater.inflate(mLayout, null);
        } else {
            view = convertView;
        }

        tv_locaiton = (TextView) view.findViewById(R.id.tv_location_name);
        tv_locaiton.setText(getItem(position).getLocation());

        return view;
    }
}
