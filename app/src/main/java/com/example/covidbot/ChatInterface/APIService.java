package com.example.covidbot.ChatInterface;

import com.example.covidbot.Notifications.MyResponse;
import com.example.covidbot.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA7hszpSA:APA91bEyj3v2599XS-t7DKMXViFA_MZgBOvSqSONSKyzCLd9I02MuYviBm3AoSThkJ5ByTLIaozlrQ8Ca_ZKqT2a_t4R_PYsGbgIP3c5StfysqZNPnywZlupFfDDdTB5iU1FOgls5ks8"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
