package com.smartway.e_canteen.Common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.smartway.e_canteen.Model.Request;
import com.smartway.e_canteen.Model.ServerUser;
import com.smartway.e_canteen.Remote.APIService;
import com.smartway.e_canteen.Remote.FCMRetrofitClient;
import com.smartway.e_canteen.Remote.IGeoCoordinates;
import com.smartway.e_canteen.Remote.RetrofitClient;

/**
 * Created by djsma on 05-02-2018.
 */

public class ServerCommon {
    public static ServerUser currentUser;
    public static Request currentRequests;
    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";
    public static final int PICK_IMAGE_REQUEST = 71;
    private static final String baseUrl = "https://maps.googleapis.com";
    private static final String FCM_URL = "https://fcm.googleapis.com/";

    public static String convertCodeToStatus(String code){
        if(code.equals("0"))
            return "Placed";
        else if (code.equals("1"))
            return "Preparing";
        else
            return "Delivered";
    }
    public static APIService getFCMService(){
        return FCMRetrofitClient.getClient(FCM_URL).create(APIService.class);
    }
    public static IGeoCoordinates getGeoCodeService(){
        return RetrofitClient.getClient(baseUrl).create(IGeoCoordinates.class);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight){
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0, pivotY = 0;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY,pivotX,pivotY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
}
