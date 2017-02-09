package nagarjuna.com.nagarjunaapp;

import android.graphics.Bitmap;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by dell on 5/13/2016.
 */
public class NoticeUtils {

    String noticeTitle;
    String noticeDescription;
    String image;
    String date;


    public NoticeUtils(String noticeTitle, String noticeDescription, String image, String date) {
        this.noticeTitle = noticeTitle;
        this.noticeDescription = noticeDescription;
        this.image = image;
        this.date = date;

    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeDescription() {
        return noticeDescription;
    }

    public void setNoticeDescription(String noticeDescription) {
        this.noticeDescription = noticeDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
