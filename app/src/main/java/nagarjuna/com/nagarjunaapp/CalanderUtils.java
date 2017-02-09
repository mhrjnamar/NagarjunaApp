package nagarjuna.com.nagarjunaapp;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by dell on 5/5/2016.
 */
public class CalanderUtils {
    Calendar calendar;

    int year = 2016;
    int month = 0;
    int day = 1;

    public CalanderUtils() {
        calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DATE,day);
        calendar.set(Calendar.DAY_OF_MONTH,1);
    }

    /**
     *
     * @param month integer month
     * @return returns integer value of total days in the given month
     */
    public int getMaxDaysInCurrentMonth(int month){
        calendar.set(Calendar.MONTH,month);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     *
     * @param month integer month
     * @return returns integer first Day of the month
     */
    public int getFirstDayOfCurrentMonth(int month){
        calendar.set(Calendar.MONTH,month);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
