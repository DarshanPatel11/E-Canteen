package com.smartway.e_canteen.Remote;

import com.smartway.e_canteen.Model.MyResponse;
import com.smartway.e_canteen.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Darshan Patel on 10-02-2018.
 */

public interface APIService {
    @Headers(
            {
                    "Content-type:application/json",
                    "Authorization:key=AAAAmf6gtZE:APA91bHB9TnGOqSEpW9YKgRQh-OeKB1nzEQcgK0uz4P4iLc1TJ5OtSqEzWkm_gMz8gCJmrwx80Z5VZrGQn75Ni3CmFhdjizrHX1U8Hy5Xs1NENRGoMiDbuT8vLu9rCV8obHB5JWUdf6m"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
