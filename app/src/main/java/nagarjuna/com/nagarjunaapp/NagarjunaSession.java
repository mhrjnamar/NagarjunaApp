package nagarjuna.com.nagarjunaapp;

import android.app.Application;

/**
 * Created by User on 9/4/2016.
 */
public class NagarjunaSession extends Application {
    private String username;
    private String passeord;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasseord() {
        return passeord;
    }

    public void setPassword(String passeord) {
        this.passeord = passeord;
    }
}
