package nagarjuna.com.nagarjunaapp;

import java.io.Serializable;

/**
 * Created by User on 7/18/2016.
 */
public class Items implements Serializable {
    String title;
    String date;
    String description;
    Boolean isNew;
    String img;



    public Items(String title, String date, String description, Boolean isNew, String img) {

        this.title = title;
        this.date = date;
        this.description = description;
        this.isNew = isNew;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
