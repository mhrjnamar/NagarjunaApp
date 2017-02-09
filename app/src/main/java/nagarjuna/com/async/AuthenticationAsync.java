package nagarjuna.com.async;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import nagarjuna.com.functions.WebServices;
import nagarjuna.com.nagarjunaapp.LoginActivity;
import nagarjuna.com.nagarjunaapp.MainActivity;
import nagarjuna.com.nagarjunaapp.NagarjunaSession;
import nagarjuna.com.nagarjunaapp.SessionManager;

/**
 * Created by User on 7/5/2016.
 */
public class AuthenticationAsync extends AsyncTask<String, Void, String> {
    private static final String TAG = "AuthenticationAsync";

    String userName = "";
    String password = "";
    String code = "";
    String msg = "";
    SessionManager manager;
    LoginActivity activity;
    HttpTransportSE transportSE;
    WebServices webServices;

    public AuthenticationAsync(Activity activity) {
        this.activity = (LoginActivity) activity;
        manager = new SessionManager(activity);
        webServices = new WebServices();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showLoading(true);
    }

    @Override
    protected String doInBackground(String... params) {
        userName = params[0];
        password = params[1];

        SoapObject request = new SoapObject(WebServices.nameSpace, WebServices.method_userAuth);
        request.addProperty("USERNAME", userName);
        request.addProperty("PASSWORD", password);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        transportSE = new HttpTransportSE(WebServices.url);
        transportSE.debug = true;

        try {
            transportSE.call(WebServices.getSoapAction(WebServices.method_userAuth), envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            code = response.getPropertyAsString(0);
            msg = response.getPropertyAsString(1);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Cannot connect to the Server  please check your Internet Connection and try\n\nDetails: \n" + e.toString();
        }

        return code;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        if (res.equals("0")) {
//            ((NagarjunaSession) activity.getApplication()).setPassword(password);
//            ((NagarjunaSession) activity.getApplication()).setUsername(userName);

            //set first Login true in the sharePreference in SessionManager class
            manager.setFirstLoginDone();
            manager.setLoggedIn();
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        } else {
            activity.showLoading(false);
            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setTitle("Login Failed!!!");
            alert.setMessage(msg);
            alert.setPositiveButton("Ok", null);
            alert.setCancelable(false);
            alert.create().show();
        }
    }
}
