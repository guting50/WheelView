package com.gt.wheelview.use;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gt.wheelview.LoopItemSelectedListener;
import com.gt.wheelview.LoopView;
import com.gt.wheelview.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeSelectorUtils {

    private PopupWindow birthPop;
    private View popView;
    private View blankView;
    private boolean outsideTouchable = false;

    /*最小值*/
    private int minStartYear;
    private int minStartMonth;
    private int minStartDay;
    private int minStartHour;
    private int minStartMinute;
    /*初始显示的年份*/
    private int defaultStartYear;
    private int defaultStartMonth;
    private int defaultStartDay;
    private int defaultStartHour;
    private int defaultStartMinute;
    private int currentYear;
    /*结束的年份*/
    private int endYear = 0;
    /*=====================*/
    private TextView tv_cancle;
    private LoopView yearLoopView;
    private LoopView dayLoopView;
    private LoopView monthLoopView;
    private LoopView hourLoopView;
    private LoopView minuteLoopView;
    private TextView tv_sure;
    private ArrayList<String> yearList;
    private ArrayList<String> monthList;
    private ArrayList<String> dayList;
    private ArrayList<String> hourList;
    private ArrayList<String> minuteList;
    private int minInterval = 5;

    private Context context;
    private String resultFormat = "yyyy-MM-dd HH:mm:ss";

    public TimeSelectorUtils(Context context, YMDCallBack ymdCallBack) {
        this(context, "yyyy-MM-dd HH:mm:ss", new Date(), ymdCallBack);
    }

    public TimeSelectorUtils(Context context, String resultFormat, YMDCallBack ymdCallBack) {
        this(context, resultFormat, new Date(), ymdCallBack);
    }

    public TimeSelectorUtils(Context context, Date defaultDate, YMDCallBack ymdCallBack) {
        this(context, "yyyy-MM-dd HH:mm:ss", defaultDate, ymdCallBack);
    }

    public TimeSelectorUtils(Context context, String resultFormat, Date defaultDate, YMDCallBack ymdCallBack) {
        this(context, new Date(), defaultDate, resultFormat, ymdCallBack);
    }

    public TimeSelectorUtils(Context context, Date minDate, Date defaultDate, String resultFormat, YMDCallBack ymdCallBack) {
        this(context, minDate, defaultDate, 0, resultFormat, ymdCallBack);
    }

    public TimeSelectorUtils(Context context, Date minDate, Date defaultDate, int endYear, String resultFormat, YMDCallBack ymdCallBack) {
        this(context, minDate, defaultDate, endYear, resultFormat, 5, ymdCallBack);
    }

    /**
     * @param context
     * @param minDate      最小可以选择的时间，默认不能选择今天以前的
     * @param defaultDate  当前选中的时间，默认为今天
     * @param endYear      最大可以选择的年份，比如2020年，默认比当前年大一年
     * @param resultFormat 返回的时间格式，默认为 yyyy-MM-dd HH:mm:ss
     * @param minInterval  分钟最小的间隔时间，默认为5分钟
     * @param ymdCallBack  点击确定回调接口
     */
    public TimeSelectorUtils(Context context, Date minDate, Date defaultDate, int endYear, String resultFormat, int minInterval, YMDCallBack ymdCallBack) {
        this.context = context;
        this.resultFormat = resultFormat;
        this.ymdCallBack = ymdCallBack;
        this.minInterval = minInterval;
        Calendar calendar = Calendar.getInstance();
        if (endYear == 0) {
            this.endYear = calendar.get(Calendar.YEAR) + 1;
        } else {
            this.endYear = endYear;
        }
        calendar.setTime(minDate);
        minStartYear = calendar.get(Calendar.YEAR);
        minStartMonth = calendar.get(Calendar.MONTH);
        minStartDay = calendar.get(Calendar.DAY_OF_MONTH);
        minStartHour = calendar.get(Calendar.HOUR_OF_DAY);
        minStartMinute = calendar.get(Calendar.MINUTE);
        calendar.setTime(defaultDate);
        currentYear = defaultStartYear = calendar.get(Calendar.YEAR);
        defaultStartMonth = calendar.get(Calendar.MONTH);
        defaultStartDay = calendar.get(Calendar.DAY_OF_MONTH);
        defaultStartHour = calendar.get(Calendar.HOUR_OF_DAY);
        defaultStartMinute = calendar.get(Calendar.MINUTE);
        initYmdPop();
    }

    private void initYmdPop() {
        popView = LayoutInflater.from(context).inflate(R.layout.pop_ymdpicker, null);
        blankView = popView.findViewById(R.id.view_cancle);
        tv_cancle = popView.findViewById(R.id.tv_cancle);
        yearLoopView = popView.findViewById(R.id.loop_year);
        monthLoopView = popView.findViewById(R.id.loop_month);
        dayLoopView = popView.findViewById(R.id.loop_day);
        hourLoopView = popView.findViewById(R.id.loop_hour);
        minuteLoopView = popView.findViewById(R.id.loop_minute);
        tv_sure = popView.findViewById(R.id.tv_sure);

        yearList = new ArrayList<>();
        monthList = new ArrayList<>();
        dayList = new ArrayList<>();
        hourList = new ArrayList<>();
        minuteList = new ArrayList<>();
        //设置年份数据
        for (int i = minStartYear; i <= endYear; i++) {
            yearList.add(i + "");
        }
        yearLoopView.setItems(yearList);

        //设置月份数据 此数据是死数据 不变化
        for (int i = minStartMonth; i <= 11; i++) {
            monthList.add((i < 9 ? "0" : "") + (i + 1) + "月");
        }
        monthLoopView.setItems(monthList);
        setDaysList(minStartYear, minStartMonth);

        //设置小时数据
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                hourList.add("0" + i + "时");
            } else {
                hourList.add(i + "时");
            }
        }
        hourLoopView.setItems(hourList);

        //设置分钟数据
        for (int i = 0; i < 60; i++) {
            if (i % minInterval == 0) {
                if (i < 10) {
                    minuteList.add("0" + i + "分");
                } else {
                    minuteList.add(i + "分");
                }
            }
        }
        minuteLoopView.setItems(minuteList);

        /*设置初始显示的位置*/
        yearLoopView.setInitPosition(yearList.indexOf(defaultStartYear + ""));
        monthLoopView.setInitPosition(monthList.indexOf((defaultStartMonth < 9 ? "0" : "") + (defaultStartMonth + 1) + "月"));
        dayLoopView.setInitPosition(dayList.indexOf((defaultStartDay < 10 ? "0" : "") + defaultStartDay + "日"));
        hourLoopView.setInitPosition(hourList.indexOf((defaultStartHour < 10 ? "0" : "") + defaultStartHour + "时"));
        minuteLoopView.setInitPosition(minuteList.indexOf((defaultStartMinute < 10 ? "0" : "") + defaultStartMinute + "分"));

        yearLoopView.setListener(new LoopItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int currentMonth;
                currentYear = minStartYear + index;
                /**如果当前的时间是当前所在的年份*/
                if (index == 0) {
                    monthList.clear();
                    //设置月份数据 此数据是死数据 不变化
                    for (int i = minStartMonth; i <= 11; i++) {
                        monthList.add(i + 1 + "月");
                    }
                    monthLoopView.setItems(monthList);
                    currentMonth = minStartMonth + monthLoopView.getSelectedItem();
                } else {
                    monthList.clear();
                    //设置月份数据 此数据是死数据 不变化
                    for (int i = 1; i <= 12; i++) {
                        monthList.add(i + "月");
                    }
                    monthLoopView.setItems(monthList);
                    currentMonth = monthLoopView.getSelectedItem();
                }
                setDaysList(currentYear, currentMonth);
            }
        });
        monthLoopView.setListener(new LoopItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int currentMonth;
                if (currentYear == minStartYear) {
                    currentMonth = minStartMonth + index;
                } else {
                    currentMonth = index;
                }
                setDaysList(currentYear, currentMonth);
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String birthDay = yearList.get(yearLoopView.getSelectedItem()) + "年"
                        + monthList.get(monthLoopView.getSelectedItem())
                        + dayList.get(dayLoopView.getSelectedItem())
                        + hourList.get(hourLoopView.getSelectedItem())
                        + minuteList.get(minuteLoopView.getSelectedItem());
                if (null != ymdCallBack) {
                    try {
                        birthDay = birthDay.replace("年", "-")
                                .replace("月", "-")
                                .replace("日", " ")
                                .replace("时", ":")
                                .replace("分", ":");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date date = sdf.parse(birthDay);
                        SimpleDateFormat sdf1 = new SimpleDateFormat(resultFormat);
                        ymdCallBack.setDate(birthPop, sdf1.format(date));
                    } catch (Exception e) {
                        e.printStackTrace();
                        ymdCallBack.setDate(birthPop, e.getMessage());
                    }
                }
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthPop.dismiss();
            }
        });
        blankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (outsideTouchable)
                    birthPop.dismiss();
            }
        });
    }

    public void setOutsideTouchable(boolean outsideTouchable) {
        this.outsideTouchable = outsideTouchable;
    }

    public PopupWindow getYmdPop() {
        birthPop = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        birthPop.setBackgroundDrawable(new BitmapDrawable());
        birthPop.setFocusable(true);
        birthPop.setOutsideTouchable(true);
        birthPop.setAnimationStyle(R.style.PopWDInAndOutStyle);
        birthPop.update();
        return birthPop;
    }

    /*建立一个判断闰年的方法*/
    private boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            /*非闰年*/
            if (year % 100 == 0 && year % 400 != 0) {
                return false;
            }
            /*闰年*/
            else {
                return true;
            }
        } else {
            return false;
        }
    }

    /*建立一个获取当年当月天数的方法*/
    private int getDays(boolean isLeapYear, int month) {
        int days = 0;
        switch (month) {
            case 0:
                days = 31;
                break;
            case 1:
                if (isLeapYear) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;
            case 2:
                days = 31;
                break;
            case 3:
                days = 30;
                break;
            case 4:
                days = 31;
                break;
            case 5:
                days = 30;
                break;
            case 6:
                days = 31;
                break;
            case 7:
                days = 31;
                break;
            case 8:
                days = 30;
                break;
            case 9:
                days = 31;
                break;
            case 10:
                days = 30;
                break;
            case 11:
                days = 31;
                break;
        }
        return days;
    }

    /*创建一个方法对天数的集合设值并添加到视图*/
    private void setDaysList(int currentYear, int currentMonth) {
        ArrayList<String> cacheList = new ArrayList<>();
        int totalDays = 0;
        if (isLeapYear(currentYear)) {
            totalDays = getDays(true, currentMonth);
        } else {
            totalDays = getDays(false, currentMonth);
        }
        if (minStartYear == currentYear && minStartMonth == currentMonth) {
            for (int i = minStartDay; i <= totalDays; i++) {
                cacheList.add(i + "日");
            }
        } else {
            for (int i = 1; i <= totalDays; i++) {
                cacheList.add(i + "日");
            }
        }
        dayList.clear();
        dayList.addAll(cacheList);
        dayLoopView.setItems(dayList);
    }

    private YMDCallBack ymdCallBack;

    public interface YMDCallBack {
        void setDate(PopupWindow popupWindow, String date);
    }
}
