package com.qqzq.common.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qqzq.R;
import com.qqzq.base.BaseActivity;
import com.qqzq.common.adapter.LocationListViewAdapter;
import com.qqzq.common.dto.EntLocation;
import com.qqzq.me.activity.MePersonalSettingActivity;
import com.qqzq.user.activity.RegisterActivity;
import com.qqzq.config.Constants;
import com.qqzq.db.LocationDao;
import com.qqzq.team.activity.CreateTeamActivity;

import java.util.List;

/**
 * Created by jie.xiao on 9/8/2015.
 */
public class SelectLocationActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "SelectLocationActivity";
    private Context context = this;
    private ListView lv_location;
    private TextView tv_selected_location;
    private LocationListViewAdapter adapter;

    private List<EntLocation> locationInfos;
    private boolean isProvincePage = true;
    private int selectedProvinceCode;
    private int selectedCityCode;
    private String prevPageName = "";

    private LocationDao locationDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        tv_selected_location = (TextView) findViewById(R.id.tv_selected_location);
        lv_location = (ListView) findViewById(R.id.lv_location);


    }

    private void initListener() {
        lv_location.setOnItemClickListener(this);
    }

    private void initData() {
        Bundle extras = this.getIntent().getExtras();
        if (extras.containsKey(Constants.EXTRA_PREV_PAGE_NAME)) {
            prevPageName = extras.getString(Constants.EXTRA_PREV_PAGE_NAME);
        }

        locationDao = new LocationDao(context);
        //请求地区数据-省
        requestProvinceList();
    }

    private void refreshLocationListView(List<EntLocation> locationInfos) {
        adapter = new LocationListViewAdapter(this, locationInfos);
        lv_location.setAdapter(adapter);
    }


    private void requestProvinceList() {
        locationInfos = locationDao.findLoactionByParent("1");
        refreshLocationListView(locationInfos);
    }

    private void requestCityList(int provinceId) {
        locationInfos = locationDao.findLoactionByParent(provinceId + "");
        refreshLocationListView(locationInfos);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (locationInfos != null) {
            isProvincePage = TextUtils.isEmpty(tv_selected_location.getText()) ? true : false;
            EntLocation selectedLocationInfo = locationInfos.get(position);
            String location = tv_selected_location.getText().toString();
            location = location + " " + selectedLocationInfo.getLocation();
            tv_selected_location.setText(location);

            if (isProvincePage) {
                selectedProvinceCode = Integer.valueOf(selectedLocationInfo.getId());
                requestCityList(selectedProvinceCode);
            } else {
                selectedCityCode = Integer.valueOf(selectedLocationInfo.getId());

                if (TextUtils.isEmpty(prevPageName)) {
                    return;
                }

                Intent intent = null;
                if ("CreateTeamActivity".equals(prevPageName)) {
                    intent = new Intent(context, CreateTeamActivity.class);
                } else if ("RegisterActivity".equals(prevPageName)) {
                    intent = new Intent(context, RegisterActivity.class);
                } else if ("MePersonalSettingActivity".equals(prevPageName)) {
                    intent = new Intent(context, MePersonalSettingActivity.class);
                }

                if (intent != null) {
                    intent.putExtra(Constants.EXTRA_SELECTED_LOCATION, location);
                    intent.putExtra(Constants.EXTRA_SELECTED_PROVINCE_CODE, selectedProvinceCode);
                    intent.putExtra(Constants.EXTRA_SELECTED_CITY_CODE, selectedCityCode);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

}
