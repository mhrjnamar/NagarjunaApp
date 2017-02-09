package nagarjuna.com.nagarjunaapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by dell on 5/8/2016.
 */
public class SessionManager {
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FACULTY = "faculty";
    public static final String KEY_SEMESTER = "sem";
    public static final String KEY_REG_ID = "reg_id";
    public static final String KEY_ROLL = "roll";
    public static final String KEY_FIRST_USE = "first_use";
    public static final String KEY_LOGGED_IN = "logged_in";
    public static final String KEY_NOTIFICATION_MSG = "notify_msg";
    private static final String TAG = "SessionManager";

    //required to create sharedPreference
    private static final int MODE = 0;
    private static final String NAME = "user_manager";
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(NAME, MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void storeNotificationMsg(String msg) {
        Log.i(TAG, "storeNotificationMsg: msg " + msg);
        editor.putString(KEY_NOTIFICATION_MSG, msg);
        editor.apply();
    }

    public String getNotificationMsg() {
        Log.i(TAG, "getNotificationMsg: returnMsg " + pref.getString(KEY_NOTIFICATION_MSG, "NO Notification"));
        return pref.getString(KEY_NOTIFICATION_MSG, "NO Notification");
    }

    /**
     * Used to store user info in shared preference
     *
     * @param faculty  string faculty eg("BIM"
     * @param semester string semester eg("1")
     * @param reg_id   string regestration_no
     * @param roll     string roll eg(4448)
     */
    public void storeUser(String faculty, String semester, String reg_id, String roll) {
        editor.putString(KEY_FACULTY, faculty);
        editor.putString(KEY_SEMESTER, semester);
        editor.putString(KEY_REG_ID, reg_id);
        editor.putString(KEY_ROLL, roll);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));
        return user;
    }

    /**
     * Use to store user's user_name and password
     *
     * @param user_name string UserName eg("ned")
     * @param password  password
     */
    public void addUsers(String user_name, String password) {
        editor.putString(KEY_USER_NAME, user_name);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    /**
     * @param user_name
     * @param password
     * @return true if user name and password are correct else false
     */
    public Boolean isValidUser(String user_name, String password) {
        if (user_name.equals(pref.getString(KEY_USER_NAME, "")) && password.equals(pref.getString(KEY_PASSWORD, ""))) {
            editor.putBoolean(KEY_LOGGED_IN, true);
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    public void setLoggedIn() {
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
    }

    /**
     * @return false as default else Boolen value against KEY_LOGGED_IN
     */
    public Boolean isLoggedIn() {
        return pref.getBoolean(KEY_LOGGED_IN, false);
    }


    /**
     * puts Boolen value true in KEY_FIRST_USE
     */
    public void setFirstLoginDone() {
        editor.putBoolean(KEY_FIRST_USE, true);
        editor.commit();
    }

    /**
     * @return false as default else Boolen value stored against KEY_FIRST_USE
     */
    public Boolean isFirstLogin() {
        return !pref.getBoolean(KEY_FIRST_USE, false);
    }

    /**
     * sets false to KEY_LOGGED_IN
     */
    public void logOutUser() {
        editor.putBoolean(KEY_LOGGED_IN, false);
        editor.commit();

    }


}
