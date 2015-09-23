package com.qqzq.widget.time;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qqzq.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jie.xiao on 15/9/24.
 */
public class TimePickerWindow extends PopupWindow implements WheelView.onSelectListener, View.OnClickListener {

    private View mView; // PopupWindow 菜单布局
    private Activity context; // 上下文参数
    private View.OnClickListener myOnClick; // PopupWindow 菜单 空间单击事件

    private WheelView mWheelDay;
    private WheelView mWheelHour;
    private WheelView mWheelMinute;
    private TextView tv_time_picker_save;
    private TextView tv_time_picker_cancel;
    private EditText edt_time;

    int year, hour, minute, dayofweek, dayofyear;
    private static final String[] WEEK_LABEL = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分");

    public TimePickerWindow(Activity context, View.OnClickListener myOnClick) {
        super(context);
        this.context = context;

        if (myOnClick == null) {
            myOnClick = this;
        }
        this.myOnClick = myOnClick;
        initView();
        initListener();
        initData();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.popup_time_picker, null);
        mView.setVisibility(View.VISIBLE);
        mWheelDay = (WheelView) mView.findViewById(R.id.day);
        mWheelHour = (WheelView) mView.findViewById(R.id.hour);
        mWheelMinute = (WheelView) mView.findViewById(R.id.minute);
        tv_time_picker_save = (TextView) mView.findViewById(R.id.tv_time_picker_save);
        tv_time_picker_cancel = (TextView) mView.findViewById(R.id.tv_time_picker_cancel);

        // 导入布局
        this.setContentView(mView);
        // 设置动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置可触
        this.setFocusable(true);
        //设置透明背景
        ColorDrawable dw = new ColorDrawable(Color.argb(0, 0, 0, 0));
        this.setBackgroundDrawable(dw);
        // 单击弹出窗以外处 关闭弹出窗
        mView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mView.findViewById(R.id.ll_time_picker).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    private void initListener() {
        mWheelDay.setOnSelectListener(this);
        mWheelHour.setOnSelectListener(this);
        mWheelMinute.setOnSelectListener(this);
        tv_time_picker_save.setOnClickListener(this);
        tv_time_picker_cancel.setOnClickListener(this);
    }

    private void initData() {
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

    //为弹出窗口实现监听类
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time_picker_save:
                this.dismiss();
                Date date = getSelectedTime();
                String dateStr = simpleDateFormat.format(date);
                System.out.println("选中的时间为:" + dateStr);

                if (edt_time != null) {
                    edt_time.setText(dateStr);
                }

                break;
            case R.id.tv_time_picker_cancel:
                this.dismiss();
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
