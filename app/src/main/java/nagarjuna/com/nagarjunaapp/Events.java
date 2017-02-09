package nagarjuna.com.nagarjunaapp;

/**
 * Created by User on 7/18/2016.
 */
public class Events extends Items {

    String location;

    public Events(String title, String date, String description, Boolean isNew, String location,String img) {
        super(title, date, description, isNew,img);
        this.location = location;

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
