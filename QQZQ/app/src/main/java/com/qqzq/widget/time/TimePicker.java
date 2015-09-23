package com.qqzq.widget.time;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qqzq.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jie.xiao on 15/9/22.
 */
public class TimePicker extends RelativeLayout implements WheelView.onSelectListener, View.OnClickListener {
    private WheelView mWheelDay;
    private WheelView mWheelHour;
    private WheelView mWheelMinute;
    private TextView tv_time_picker_save;
    private TextView tv_time_picker_cancel;
    private EditText edt_time;

    int year, hour, minute, dayofweek, dayofyear;
    private static final String[] WEEK_LABEL = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.popup_time_picker, this);
        mWheelDay = (WheelView) findViewById(R.id.day);
        mWheelHour = (WheelView) findViewById(R.id.hour);
        mWheelMinute = (WheelView) findViewById(R.id.minute);
        tv_time_picker_save = (TextView) findViewById(R.id.tv_time_picker_save);
        tv_time_picker_cancel = (TextView) findViewById(R.id.tv_time_picker_cancel);

        mWheelDay.setOnSelectListener(this);
        mWheelHour.setOnSelectListener(this);
        mWheelMinute.setOnSelectListener(this);
        tv_time_picker_save.setOnClickListener(this);
        tv_time_picker_cancel.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        dayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        dayofyear = calendar.get(Calendar.DAY_OF_YEAR) - 1;


        System.out.println("系统时间:" + calendar);

        mWheelDay.setData(getDayOfOneYear());
        mWheelHour.setData(getNumericData(1, 23));
        mWheelMinute.setData(getNumericData(2, 59));

        mWheelDay.setSelected(dayofyear);
        mWheelHour.setSelected(hour);
        mWheelMinute.setSelected(minute);


    }

    @Override
    public void onSelect(PickerItem pickerItem) {

        System.out.println("当前选中项为" + pickerItem);
        switch (pickerItem.getType()) {
            case 0://月+日+星期几
                dayofyear = pickerItem.getId();
                break;
            case 1://时
                hour = pickerItem.getId();
                break;
            case 2://分
                minute = pickerItem.getId();
                break;
            default:
                break;
        }

        getSelectedTime();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time_picker_save:
                setVisibility(View.GONE);
                Date date = getSelectedTime();
                String dateStr = simpleDateFormat.format(date);
                System.out.println("选中的时间为:" + dateStr);

                if (edt_time != null) {
                    edt_time.setText(dateStr);
                }

                break;
            case R.id.tv_time_picker_cancel:
                setVisibility(View.GONE);
                break;
        }
    }


    private List<PickerItem> getNumericData(int type, int last) {
        List<PickerItem> list = new ArrayList<>();
        for (int i = 0; i <= last; i++) {
            PickerItem picker = new PickerItem(type, i, i + "");
            list.add(picker);
        }
        return list;
    }


    public Date getSelectedTime() {

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_YEAR, dayofyear + 1);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public void setEditText(EditText edt_time) {
        this.edt_time = edt_time;
    }

    private List<PickerItem> getDayOfOneYear() {
        List<PickerItem> list = new ArrayList<>();

        Calendar calendarStar = new GregorianCalendar();//年初
        Calendar calendarEnd = new GregorianCalendar();//年末

        calendarStar.set(Calendar.YEAR, year);
        calendarStar.set(Calendar.MONTH, 0);
        calendarStar.set(Calendar.DAY_OF_MONTH, 1);//设置年初为1月1日

        calendarEnd.set(Calendar.YEAR, year);
        calendarEnd.set(Calendar.MONTH, 11);


        int i = 0;
        String time = "";
        int month, day, dayofweek;
        while (calendarStar.getTime().getTime() <= calendarEnd.getTime().getTime()) {//用一整年的日期循环

            month = calendarStar.get(Calendar.MONTH) + 1;
            day = calendarStar.get(Calendar.DAY_OF_MONTH);
            dayofweek = calendarStar.get(Calendar.DAY_OF_WEEK) - 1;
            time = month + " 月 " + day + " 日 " + WEEK_LABEL[dayofweek];
//            System.out.println(time);
            PickerItem pickerItem = new PickerItem(0, i, time);
            list.add(pickerItem);
            i++;
            calendarStar.add(Calendar.DAY_OF_MONTH, 1);//日期+1
        }
        return list;
    }

}