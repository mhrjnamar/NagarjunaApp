package nagarjuna.com.nagarjunaapp;

/**
 * Created by dell on 5/5/2016.
 */
public class DaysUtils {
    int day;
    String dayName;
    CalenderEvents events;

    public DaysUtils(int day, String dayName) {
        this.day = day;
        this.dayName = dayName;
    }

    /**
     *
     * @return gets Day
     */

    public int getDay() {
        return day;
    }

    /**
     *
     * @param day sets Day
     */

    public void setDay(int day) {
        this.day = day;
    }

    /**
     *
     * @return returns Day
     */
    public String getDayName() {
        return dayName;
    }

    /**
     *
     * @param dayName sets Day Name
     */

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public CalenderEvents getEvents() {
        return events;
    }

    /**
     *
     * @param events sets the event
     */
    public void setEvents(CalenderEvents events) {
        this.events = events;
    }
}
