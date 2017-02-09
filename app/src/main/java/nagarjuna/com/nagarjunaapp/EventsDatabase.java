package nagarjuna.com.nagarjunaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by dell on 5/5/2016.
 */
public class EventsDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_events";
    public static final String EVENT_TABLE_NAME = "event_tbl_name";
    public static final String EVENT_COLUMN_ID = "event_column_id";
    public static final String EVENT_COLUMN_TITLE = "event_column_title";
    public static final String EVENT_COLUMN_DESCRIPTION = "event_column_description";
    public static final String EVENT_COLUMN_YEAR = "event_column_year";
    public static final String EVENT_COLUMN_MONTH = "event_column_month";
    public static final String EVENT_COLUMN_DAY = "event_column_day";
    public static final String EVENT_COLUMN_LOCATION = "event_column_location";
    public static final String NOTICE_TABLE_NAME = "notice_tbl_name";
    public static final String NOTICE_COLUMN_ID = "notice_column_id";
    public static final String NOTICE_COLUMN_TITLE = "notice_column_title";
    public static final String NOTICE_COLUMN_DESCRIPTION = "notice_column_description";
    public static final String NOTICE_COLUMN_IMAGE = "notice_column_image";
    public static final String NOTICE_ISSUE_DATE = "notice_issue_date";
    public static final String NOTIFICATION_TABLE_NAME = "notify_tbl_name";
    public static final String NOTIFY_COLUMN_ID = "notify_col_id";
    public static final String NOTIFY_COLUMN_MSG = "notify_col_msg";


    private static final String TAG = "EventsDatabase";


    public EventsDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create event table
        db.execSQL("CREATE TABLE " + EVENT_TABLE_NAME + " (" + EVENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENT_COLUMN_TITLE + " VARCHAR, " + EVENT_COLUMN_DESCRIPTION + " VARCHAR, " + EVENT_COLUMN_YEAR + " INTEGER, " + EVENT_COLUMN_MONTH + " INTEGER, " + EVENT_COLUMN_DAY + " INTEGER, " + EVENT_COLUMN_LOCATION + " VARCHAR)");
        db.execSQL("CREATE TABLE " + NOTICE_TABLE_NAME + " (" + NOTICE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTICE_ISSUE_DATE + " VARCHAR, " + NOTICE_COLUMN_TITLE + " VARCHAR, " + NOTICE_COLUMN_DESCRIPTION + " VARCHAR, " + NOTICE_COLUMN_IMAGE + " VARCHAR)");
        db.execSQL("CREATE TABLE " + NOTIFICATION_TABLE_NAME + " (" + NOTIFY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTIFY_COLUMN_MSG + " VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //update tabe if there is change in the number of columns in table by calling onCreate method with new sql statement by dropping previous one
        db.execSQL("DROP TABLE IF EXIST " + EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + NOTICE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + NOTIFICATION_TABLE_NAME);
        onCreate(db);
    }

    public void insertNotifyMsg(String msg) {
        Log.i(TAG, "insertNotifyMsg: databaseMsg "+msg);
        ContentValues cv = new ContentValues();
        cv.put(NOTIFY_COLUMN_MSG, msg);
       long notify = getWritableDatabase().insert(NOTIFICATION_TABLE_NAME, null, cv);
        Log.i(TAG, "insertNotifyMsg: notify "+notify);
    }

    public ArrayList<String> getNotifyMsg() {
        ArrayList<String> msgs = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + NOTIFICATION_TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                msgs.add(cursor.getString(cursor.getColumnIndex(NOTIFY_COLUMN_MSG)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        Log.i(TAG, "getNotifyMsg: msgsCount:"+msgs.size());
        return msgs;
    }

    /**
     * @param events CalenderEvents object to store event in the database
     * @return returns true if insert is successful else false
     */
    public Boolean createEvents(CalenderEvents events) {
        ContentValues cv = new ContentValues();
        cv.put(EVENT_COLUMN_TITLE, events.getTitle());
        cv.put(EVENT_COLUMN_DESCRIPTION, events.getDescription());
        cv.put(EVENT_COLUMN_YEAR, events.getYear());
        cv.put(EVENT_COLUMN_MONTH, events.getMonth());
        cv.put(EVENT_COLUMN_DAY, events.getDate());
        cv.put(EVENT_COLUMN_LOCATION, events.getLocation());
        return getWritableDatabase().insert(EVENT_TABLE_NAME, null, cv) != -1;
    }

    /**
     * @param cursor for query
     * @return events for cursor
     */
    public ArrayList<CalenderEvents> getEvents(Cursor cursor) {
        ArrayList<CalenderEvents> events = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(EVENT_COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(EVENT_COLUMN_DESCRIPTION));
                int year = cursor.getInt(cursor.getColumnIndex(EVENT_COLUMN_YEAR));
                int month = cursor.getInt(cursor.getColumnIndex(EVENT_COLUMN_MONTH));
                int date = cursor.getInt(cursor.getColumnIndex(EVENT_COLUMN_DAY));
                String location = cursor.getString(cursor.getColumnIndex(EVENT_COLUMN_LOCATION));
                events.add(new CalenderEvents(title, year + "/" + month + "/" + date, description, false, year, month, date, location, ""));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return events;
    }

    /**
     * @param month int month eg(jan = 0)
     * @return events as per month
     */
    public ArrayList<CalenderEvents> getEventForMonth(int month) {
        String query = "SELECT * FROM " + EVENT_TABLE_NAME + " WHERE " + EVENT_COLUMN_MONTH + " = " + month;
        return getEvents(getWritableDatabase().rawQuery(query, null));
    }

    public ArrayList<CalenderEvents> getAllEvents() {
        String query = "SELECT * FROM " + EVENT_TABLE_NAME;
        return getEvents(getWritableDatabase().rawQuery(query, null));
    }

    /**
     * @param noticeUtils object of NoticeUtils class
     * @return true if inserted successfully else false
     */
    public Boolean createNotice(NoticeUtils noticeUtils) {
        Log.i(TAG, "createNotice: ");
        ContentValues cv = new ContentValues();
        cv.put(NOTICE_COLUMN_TITLE, noticeUtils.getNoticeTitle());
        cv.put(NOTICE_COLUMN_DESCRIPTION, noticeUtils.getNoticeDescription());
        cv.put(NOTICE_COLUMN_IMAGE, noticeUtils.getImage());
        cv.put(NOTICE_ISSUE_DATE, noticeUtils.getDate());
        return getWritableDatabase().insert(NOTICE_TABLE_NAME, null, cv) != -1;
    }

    /**
     * @return ArrayList of type NoticeUtils containing different info about notices eg. title, des, img
     */
    public ArrayList<NoticeUtils> getNotices() {
        ArrayList<NoticeUtils> notices = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + NOTICE_TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndex(NOTICE_COLUMN_TITLE));
                String des = cursor.getString(cursor.getColumnIndex(NOTICE_COLUMN_DESCRIPTION));
                String img = cursor.getString(cursor.getColumnIndex(NOTICE_COLUMN_IMAGE));
                String date = cursor.getString(cursor.getColumnIndex(NOTICE_ISSUE_DATE));
                notices.add(new NoticeUtils(title, des, img, date));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return notices;

    }

}
