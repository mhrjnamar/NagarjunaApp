package nagarjuna.com.nagarjunaapp;

/**
 * Created by dell on 5/5/2016.
 */
public class CalenderEvents extends Items {
    //    String title;
//    String description;
    int year;
    int month;
    int day;
    String location;


//    public CalenderEvents(String title, String description, int year, int month, int day, String location) {
////        this.title = title;
////        this.description = description;
//        this.year = year;
//        this.month = month;
//        this.day = day;
//        this.location = location;


//}

    public CalenderEvents(String title, String date, String description, Boolean isNew, int year, int month, int day, String location, String img) {
        super(title, date, description, isNew,img);
        this.year = year;
        this.month = month;
        this.day = day;
        this.location = location;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}