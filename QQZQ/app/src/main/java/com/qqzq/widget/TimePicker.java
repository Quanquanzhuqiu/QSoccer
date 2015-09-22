package com.qqzq.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.qqzq.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jie.xiao on 15/9/22.
 */
public class TimePicker extends LinearLayout implements WheelView.onSelectListener {
    private WheelView mWheelMonth;
    private WheelView mWheelDay;
    private WheelView mWheelDayOfWeek;
    private WheelView mWheelHour;
    private WheelView mWheelMinute;

    // 添加大小月月份并将其转换为list,方便之后的判断
    private static final Integer[] BIG_MONTH = {1, 3, 5, 7, 8, 10, 12};
    private static final Integer[] LITTLE_MONTH = {4, 6, 9, 11};
    private static final List<Integer> LIST_BIG_MONTH = Arrays.asList(BIG_MONTH);
    private static final List<Integer> LIST_LITTLE_MONTH = Arrays.asList(LITTLE_MONTH);
    int year, month, day, hour, minute, dayofweek;

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.time_picker, this);
        mWheelMonth = (WheelView) findViewById(R.id.month);
        mWheelDay = (WheelView) findViewById(R.id.day);
        mWheelDayOfWeek = (WheelView) findViewById(R.id.dayOfWeek);
        mWheelHour = (WheelView) findViewById(R.id.hour);
        mWheelMinute = (WheelView) findViewById(R.id.minute);

        mWheelMonth.setOnSelectListener(this);
        mWheelDay.setOnSelectListener(this);
        mWheelDayOfWeek.setOnSelectListener(this);
        mWheelHour.setOnSelectListener(this);
        mWheelMinute.setOnSelectListener(this);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        dayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        System.out.println("系统时间:" + calendar);

        mWheelMonth.setData(getMonthData());
        mWheelDay.setData(getDayData());
        mWheelDayOfWeek.setData(getDayOfWeekData());
        mWheelHour.setData(getNumericData(3, 23));
        mWheelMinute.setData(getNumericData(4, 59));

        mWheelMonth.setSelected(month - 1);
        mWheelDay.setSelected(day - 1);
        mWheelDayOfWeek.setSelected(dayofweek - 1);
        mWheelHour.setSelected(hour);
        mWheelMinute.setSelected(minute);


    }

    @Override
    public void onSelect(PickerItem pickerItem) {

        Calendar calendar = Calendar.getInstance();
        System.out.println("当前选中项为" + pickerItem);
        switch (pickerItem.getType()) {
            case 0://月
                month = pickerItem.getId();
                mWheelDay.setData(getDayData());
                mWheelDay.setSelected(0);
                mWheelDayOfWeek.setSelected(getDayofWeek());
                break;
            case 1://日
                day = pickerItem.getId();
                mWheelDayOfWeek.setSelected(getDayofWeek());
                break;
            case 2://星期X
                dayofweek = pickerItem.getId();
                break;
            case 3://时
                hour = pickerItem.getId();
                break;
            case 4://分
                minute = pickerItem.getId();
                break;
            default:
                break;
        }

    }

    private List<PickerItem> getMonthData() {
        List<PickerItem> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            PickerItem picker = new PickerItem(0, i, i + "月");
            list.add(picker);
        }
        return list;
    }

    private List<PickerItem> getDayData() {
        List<PickerItem> dayData = new ArrayList<PickerItem>();
        System.out.println("现在是" + month + "月");
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (LIST_BIG_MONTH.contains(month)) {
            dayData = getDayData(31);
        } else if (LIST_LITTLE_MONTH.contains(month)) {
            dayData = getDayData(30);
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                dayData = getDayData(29);
            } else {
                dayData = getDayData(28);
            }
        }
        return dayData;
    }

    private List<PickerItem> getDayData(int lastDayOfMonth) {
        List<PickerItem> list = new ArrayList<>();
        for (int i = 1; i <= lastDayOfMonth; i++) {
            PickerItem picker = new PickerItem(1, i, i + "日");
            list.add(picker);
        }
        return list;
    }

    private List<PickerItem> getDayOfWeekData() {
        List<PickerItem> list = new ArrayList<>();
        String[] values = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        for (int i = 1; i <= 7; i++) {
            PickerItem picker = new PickerItem(2, i, values[i - 1]);
            list.add(picker);
        }
        return list;
    }

    private List<PickerItem> getNumericData(int type, int last) {
        List<PickerItem> list = new ArrayList<>();
        for (int i = 0; i <= last; i++) {
            PickerItem picker = new PickerItem(type, i, i + "");
            list.add(picker);
        }
        return list;
    }

    private int getDayofWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getSelectedTime());
        System.out.println(calendar);
        System.out.println("现在是星期" + (calendar.get(Calendar.DAY_OF_WEEK) - 1));
        return calendar.get(Calendar.DAY_OF_WEEK) - 2;
    }


    public Date getSelectedTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return calendar.getTime();
    }
}