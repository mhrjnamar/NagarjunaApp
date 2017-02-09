package nagarjuna.com.functions;

/**
 * Created by User on 7/5/2016.
 */
public class WebServices {

    public static final String url ="http://nagarjun.inficare.net.np/Service.asmx";
    public static final String nameSpace ="Nagarjuna";
    public static final String method_register ="Register";
    public static final String method_GetInformation ="GetInformation";
    public static final String method_userAuth ="UserAuthentication";

    public static String getSoapAction(String method){
        return nameSpace+"/"+method;
    }
}
