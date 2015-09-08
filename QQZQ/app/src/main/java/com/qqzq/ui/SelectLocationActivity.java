package com.qqzq.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.qqzq.BaseActivity;
import com.qqzq.R;
import com.qqzq.adapter.LocationListViewAdapter;
import com.qqzq.common.Constants;
import com.qqzq.entity.EntLocationInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jie.xiao on 9/8/2015.
 */
public class SelectLocationActivity extends BaseActivity {

    private Context context = this;
    private ListView lv_location;
    private TextView tv_selected_location;
    private LocationListViewAdapter adapter;

    private EntLocationInfo[] locationInfos;
    private boolean isProvincePage = true;
    private String selectedProvince = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        init();
    }

    private void init() {
        tv_selected_location = (TextView) findViewById(R.id.tv_selected_location);
        lv_location = (ListView) findViewById(R.id.lv_location);
        lv_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (locationInfos != null) {
                    isProvincePage = TextUtils.isEmpty(tv_selected_location.getText()) ? true : false;
                    EntLocationInfo selectedLocationInfo = locationInfos[position];
                    String location = tv_selected_location.getText().toString();
                    location = location + " " + selectedLocationInfo.getLocation();
                    tv_selected_location.setText(location);

                    if (isProvincePage) {
                        selectedProvince = selectedLocationInfo.getId();
                        requestCityList(selectedProvince);
                    } else {
                        Intent intent = new Intent(context, RegisterActivity.class);
                        intent.putExtra(Constants.EXTRA_SELECTED_LOCATION, location);
                        startActivity(intent);
                    }
                }


            }
        });

        //请求地区数据-省
        requestProvinceList();
    }

    private void refreshLocationListView(EntLocationInfo[] locationInfos) {
        adapter = new LocationListViewAdapter(this,locationInfos);
        lv_location.setAdapter(adapter);
    }


    private void requestProvinceList() {
        Map<String, Object> mParameters = new HashMap<>();
        mParameters.put("offset", 0);
        mParameters.put("limit", Constants.UNLIMITED_PAGE_SIZE);
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_PROVINCE_URL, mParameters);
        System.out.println(queryUrl);
        GsonRequest gsonRequest = new GsonRequest<EntLocationInfo[]>(queryUrl, EntLocationInfo[].class,
                findLocationResponseListener);
        executeRequest(gsonRequest);
    }

    private void requestCityList(String provinceId) {
        Map<String, Object> mParameters = new HashMap<>();
        mParameters.put("offset", 0);
        mParameters.put("limit", Constants.UNLIMITED_PAGE_SIZE);
        mParameters.put("provinceId", provinceId);
        String queryUrl = Utils.makeGetRequestUrl(Constants.API_FIND_CITY_URL, mParameters);
        System.out.println(queryUrl);
        GsonRequest gsonRequest = new GsonRequest<EntLocationInfo[]>(queryUrl, EntLocationInfo[].class,
                findLocationResponseListener);
        executeRequest(gsonRequest);
    }

    ResponseListener findLocationResponseListener = new ResponseListener<EntLocationInfo[]>() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            System.out.println(new String(volleyError.networkResponse.data));
        }

        @Override
        public void onResponse(EntLocationInfo[] respLocationInfos) {
            locationInfos = respLocationInfos;
            refreshLocationListView(locationInfos);
        }
    };
}
