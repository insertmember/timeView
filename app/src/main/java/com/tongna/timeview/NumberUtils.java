package com.tongna.timeview;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by hxf on 2017/2/7.
 */
public class NumberUtils {
    private volatile static NumberUtils numberUtils = null;
    public NumberUtils() {
    }
    public static NumberUtils getInstance() {
        if (numberUtils == null) {
            synchronized (NumberUtils.class) {
                if (numberUtils == null) {
                    numberUtils = new NumberUtils();
                }
            }
        }
        return numberUtils;
    }
    protected String[]  listMd;
    protected ArrayList<Date> listHmd;
    SimpleDateFormat initTime ;
    SimpleDateFormat formatDate ;
    /**
     * @param showDateType  显示的时间格式
     * @param initDateType 格式化的时间格式
     * @return
     */
    public String[] getList(String showDateType,String initDateType) {
        initTime = new SimpleDateFormat(initDateType);
        formatDate = new SimpleDateFormat(showDateType);
        if (listMd == null) {
            listMd = new String[10];
        }
        if (listHmd == null) {
            listHmd = new ArrayList<>();
        } else {
            listHmd.clear();
        }

        for (int i = 0; i < 10; i++) {
            getNext(i);
        }
        return listMd;
    }

    public void getNext(int i) {
        Calendar c = Calendar.getInstance();
        //new Date().getTime();这个是获得当前电脑的时间，你也可以换成一个随意的时间
        c.setTimeInMillis(new Date().getTime());
        c.add(Calendar.DATE, i);//天后的日期
        Date date = new Date(c.getTimeInMillis()); //将c转换成Date
        listHmd.add(date);
        String time = formatDate.format(date);
        listMd[i] = time;
    }

    public String getSelectInitDate(int index){
        if (index > listHmd.size()) {
            return null;
        }
        Date date = listHmd.get(index);
        String time = initTime.format(date);
        return time;
    }
}
