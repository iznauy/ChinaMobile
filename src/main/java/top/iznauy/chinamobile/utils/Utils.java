package top.iznauy.chinamobile.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
public class Utils {

    public static Date getBeginDate(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        return calendar.getTime();
    }

    public static Date getBeginDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndDate(int year, int month) {
        if (month == 12) {
            year += 1;
            month = 1;
        } else
            month += 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 0, 23, 59, 59);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getBeginDate(2018, 10));
        System.out.println(getEndDate(2018, 10));
    }

}


