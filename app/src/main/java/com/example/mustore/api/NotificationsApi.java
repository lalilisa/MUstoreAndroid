package com.example.mustore.api;

import com.example.mustore.common.Constant;
import com.example.mustore.model.Notification;
import com.example.mustore.retrofit.request.FCMToken;
import com.example.mustore.retrofit.response.Category;
import com.example.mustore.retrofit.response.DeviceToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface NotificationsApi {

    Gson gson = new GsonBuilder().create();

    NotificationsApi notificationsApi = new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/notification/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NotificationsApi.class);

    @POST("fcm")
    Call<DeviceToken> sendFCMToken(@Header("Authorization") String token, @Body FCMToken fcmToken);

    @GET("me")
    Call<List<Notification>> getNotifi(@Header("Authorization") String token);
}
