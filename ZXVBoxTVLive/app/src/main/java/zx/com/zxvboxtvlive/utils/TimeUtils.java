package zx.com.zxvboxtvlive.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import zx.com.zxvboxtvlive.mode.WeekInfo;

/**
 * User: ShaudXiao
 * Date: 2017-04-11
 * Time: 11:46
 * Company: zx
 * Description:
 * FIXME
 */


public class TimeUtils {

    public static String getCurrentDate() {
        String mYear;
        String mMonth;
        String mDay;
        String mWay;

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));

        if (Integer.valueOf(mMonth) < 10) {
            mMonth = "0" + mMonth;
        }
        return mYear + mMonth + mDay;
    }

    public static Calendar getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(monday);

        return c;
    }

    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    public static List<WeekInfo> getCurrentWeekInfoList() {
        int mYear;
        int mMonth;
        int mDay;

        List<WeekInfo> weekInfos = new ArrayList<>();
        Calendar now = Calendar.getInstance();

        Calendar mondayDate = getCurrentMonday();//本周星期一的Calendar

        WeekInfo weekInfo = new WeekInfo();
        mYear = mondayDate.get(Calendar.YEAR);
        mMonth = mondayDate.get(Calendar.MONTH) + 1;
        mDay = mondayDate.get(Calendar.DAY_OF_MONTH);


        weekInfo.setWeek(1);
        weekInfo.setWeekOfDay(mMonth + "月" + mDay + "日");
        weekInfo.setDate("" + mYear + (mMonth < 10  ? "0" + mMonth : mMonth) + mDay);
        weekInfos.add(weekInfo);

        mondayDate.add(Calendar.DAY_OF_MONTH, 1);
        WeekInfo weekInfo2 = new WeekInfo();

        mMonth = mondayDate.get(Calendar.MONTH) + 1;
        mDay = mondayDate.get(Calendar.DAY_OF_MONTH);

        weekInfo2.setWeek(2);
        weekInfo2.setWeekOfDay(mMonth + "月" + mDay + "日");
        weekInfo2.setDate("" + mYear + (mMonth < 10  ? "0" + mMonth : mMonth) + mDay);
        weekInfos.add(weekInfo2);


        mondayDate.add(Calendar.DAY_OF_MONTH, 1);
        WeekInfo weekInfo3 = new WeekInfo();
        mMonth = mondayDate.get(Calendar.MONTH) + 1;
        mDay = mondayDate.get(Calendar.DAY_OF_MONTH);


        weekInfo3.setWeek(3);
        weekInfo3.setWeekOfDay(mMonth + "月" + mDay + "日");
        weekInfo3.setDate("" + mYear + (mMonth < 10  ? "0" + mMonth : mMonth) + mDay);
        weekInfos.add(weekInfo3);

        mondayDate.add(Calendar.DAY_OF_MONTH, 1);
        WeekInfo weekInfo4 = new WeekInfo();
        mMonth = mondayDate.get(Calendar.MONTH) + 1;
        mDay = mondayDate.get(Calendar.DAY_OF_MONTH);


        weekInfo4.setWeek(4);
        weekInfo4.setWeekOfDay(mMonth + "月" + mDay + "日");
        weekInfo4.setDate("" + mYear + (mMonth < 10  ? "0" + mMonth : mMonth) + mDay);
        weekInfos.add(weekInfo4);

        mondayDate.add(Calendar.DAY_OF_MONTH, 1);
        WeekInfo weekInfo5 = new WeekInfo();
        mMonth = mondayDate.get(Calendar.MONTH) + 1;
        mDay = mondayDate.get(Calendar.DAY_OF_MONTH);


        weekInfo5.setWeek(5);
        weekInfo5.setWeekOfDay(mMonth + "月" + mDay + "日");
        weekInfo5.setDate("" + mYear + (mMonth < 10  ? "0" + mMonth : mMonth) + mDay);
        weekInfos.add(weekInfo5);

        mondayDate.add(Calendar.DAY_OF_MONTH, 1);
        WeekInfo weekInfo6 = new WeekInfo();
        mMonth = mondayDate.get(Calendar.MONTH) + 1;
        mDay = mondayDate.get(Calendar.DAY_OF_MONTH);


        weekInfo6.setWeek(6);
        weekInfo6.setWeekOfDay(mMonth + "月" + mDay + "日");
        weekInfo6.setDate("" + mYear + (mMonth < 10  ? "0" + mMonth : mMonth) + mDay);
        weekInfos.add(weekInfo6);

        mondayDate.add(Calendar.DAY_OF_MONTH, 1);
        WeekInfo weekInfo7 = new WeekInfo();
        mMonth = mondayDate.get(Calendar.MONTH) + 1;
        mDay = mondayDate.get(Calendar.DAY_OF_MONTH);


        weekInfo7.setWeek(7);
        weekInfo7.setWeekOfDay(mMonth + "月" + mDay + "日");
        weekInfo7.setDate("" + mYear + (mMonth < 10  ? "0" + mMonth : mMonth) + mDay);
        weekInfos.add(weekInfo7);

        return weekInfos;
    }

    public static int getDayOfWeekPosition() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(day == 1) {
            return 7;
        } else {
            return day - 1;
        }
    }

    public static boolean compareUpdateTime(String date) {
        if (date == null) {
            return false;
        }
        String[] dateArr = date.split("-");
        int month = Integer.valueOf(dateArr[1]);
        int day = Integer.valueOf(dateArr[2]);
        Logger.getLogger().i("date = " + date + " Month " + month + " day = " + day);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Logger.getLogger().i("currentMonth = " + currentMonth + " currentDay =  " + currentDay);
        return month == currentMonth && day == currentDay;
    }
}
