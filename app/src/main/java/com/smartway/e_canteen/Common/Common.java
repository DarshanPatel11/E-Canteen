package com.smartway.e_canteen.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.smartway.e_canteen.Model.User;
import com.smartway.e_canteen.Remote.APIService;
import com.smartway.e_canteen.Remote.RetrofitClient;

/**
 * Created by djsma on 04-02-2018.
 */

public class Common {
    public static User currentUser;
    private static final String BASE_URL = "https://fcm.googleapis.com/";
    public static APIService getFCMService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
    public static final String DELETE = "Delete";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";
    public static String convertCodeToStatus(String code){
        if(code.equals("0"))
            return "Placed";
        else if (code.equals("1"))
            return "Preparing";
        else
            return "Delivered";
    }
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null){
                for (int i=0;i<info.length;i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}
