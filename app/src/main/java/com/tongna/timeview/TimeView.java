package com.tongna.timeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hxf on 2017/2/7.
 */

public class TimeView extends LinearLayout {
    private String date;
    private String time;

    String showDateType;
    String initDateType;
    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        LinearLayout.inflate(context, R.layout.timeview_item, this);
        NumberPickerView mMd = (NumberPickerView) findViewById(R.id.np_md);
        NumberPickerView mHm = (NumberPickerView) findViewById(R.id.np_hm);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
        showDateType =  a.getString(R.styleable.TimeView_showDateType);
        initDateType =  a.getString(R.styleable.TimeView_initDateType);
        if(TextUtils.isEmpty(showDateType)){
            showDateType = "MM月dd日";
        }
        if(TextUtils.isEmpty(initDateType)){
            initDateType = "yyyy年MM月dd日";
        }
        String[] strMd = NumberUtils.getInstance().getList(showDateType,initDateType);
        mMd.setDisplayedValues(strMd);
        mMd.setMinValue(0);
        mMd.setMaxValue(strMd.length - 1);
        final String[] strings = context.getResources().getStringArray(R.array.time);
        mHm.setDisplayedValues(strings);
        mHm.setMinValue(0);
        mHm.setMaxValue(strings.length - 1);
        date = NumberUtils.getInstance().getSelectInitDate(0);
        time = strings[0];

        mHm.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                time = strings[newVal];
                Toast.makeText(context,getTimeString(),Toast.LENGTH_SHORT).show();
            }
        });
        mMd.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                date = NumberUtils.getInstance().getSelectInitDate(newVal);
                Toast.makeText(context,getTimeString(),Toast.LENGTH_SHORT).show();
            }
        });
        a.recycle();
    }
    public String getTimeString(){
        return date+time;
    }
    public Long getTimeLong(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        Date date = new Date();
        try {
            date = dateFormat.parse(getTimeString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
}
