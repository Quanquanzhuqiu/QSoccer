package com.qqzq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.qqzq.R;
import com.qqzq.adapter.LocationListViewAdapter;
import com.qqzq.config.Constants;
import com.qqzq.entity.EntLocationInfo;
import com.qqzq.network.GsonRequest;
import com.qqzq.network.ResponseListener;
import com.qqzq.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie.xiao on 9/8/2015.
 */
public class SelectLocationActivity extends BaseActivity {

    private Context context = this;
    private TextView tv_title;
    private ImageView iv_back;
    private ListView lv_location;
    private TextView tv_selected_location;
    private LocationListViewAdapter adapter;

    private EntLocationInfo[] locationInfos;
    private boolean isProvincePage = true;
    private String selectedProvinceCode = "";
    private String selectedCityCode = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        init();
    }

    private void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("选择地区");
        iv_back = (ImageView) findViewById(R.id.iv_back);
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
                        selectedProvinceCode = selectedLocationInfo.getId();
                        requestCityList(selectedProvinceCode);
                    } else {
                        selectedCityCode = selectedLocationInfo.getId();

                        Intent intent = new Intent(context, RegisterActivity.class);
                        intent.putExtra(Constants.EXTRA_SELECTED_LOCATION, location);
                        intent.putExtra(Constants.EXTRA_SELECTED_PROVINCE_CODE, selectedProvinceCode);
                        intent.putExtra(Constants.EXTRA_SELECTED_CITY_CODE, selectedCityCode);
                        startActivity(intent);
                    }
                }


            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectLocationActivity.this.finish();
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
