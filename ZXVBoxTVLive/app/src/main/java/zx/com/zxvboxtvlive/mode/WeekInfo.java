package zx.com.zxvboxtvlive.mode;

/**
 * User: ShaudXiao
 * Date: 2017-04-11
 * Time: 11:54
 * Company: zx
 * Description:
 * FIXME
 */


public class WeekInfo {
    private int week;
    private String weekOfDay;
    private String date;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getWeekOfDay() {
        return weekOfDay;
    }

    public void setWeekOfDay(String weekOfDay) {
        this.weekOfDay = weekOfDay;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "WeekInfo{" +
                "week=" + week +
                ", weekOfDay='" + weekOfDay + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
