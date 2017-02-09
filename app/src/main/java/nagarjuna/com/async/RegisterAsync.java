package nagarjuna.com.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import java.util.HashMap;
import nagarjuna.com.functions.WebServices;
import nagarjuna.com.nagarjunaapp.SessionManager;

/**
 * Created by User on 8/21/2016.
 */
public class RegisterAsync extends AsyncTask<String, Void, String> {
    private static final String TAG = "RegisterAsync";

    String register_id;
    String tu_register_id;
    String username;

    String code;
    String msg;

    Context context;
    SessionManager manager;
    HashMap<String,String> user;


    public RegisterAsync(Context context) {
        this.context = context;
        manager = new SessionManager(context);


    }
    @Override
    protected String doInBackground(String... params) {
        register_id = params[0];
        tu_register_id = params[1];
        user = manager.getUserDetails();
        Log.i(TAG, "doInBackground: register: "+register_id);
        Log.i(TAG, "doInBackground: tu_id: "+tu_register_id);
        username = user.get(SessionManager.KEY_USER_NAME);

        SoapObject request = new SoapObject(WebServices.nameSpace,WebServices.method_register);
        request.addProperty("register_id",register_id);
        request.addProperty("tu_register_id",tu_register_id);
        request.addProperty("username",username);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transportSE = new HttpTransportSE(WebServices.url);
        transportSE.debug = true;
        Log.i(TAG, "doInBackground: soap: "+WebServices.getSoapAction(WebServices.method_register));
        try {
            transportSE.call(WebServices.getSoapAction(WebServices.method_register),envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            code = response.getPropertyAsString("CODE");
            msg = response.getPropertyAsString("MSG");
            Log.i(TAG, "doInBackground: codde: "+code);
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Could not connect to Internet\n\n Details:\n"+e;
        }
        return code;
    }


}
