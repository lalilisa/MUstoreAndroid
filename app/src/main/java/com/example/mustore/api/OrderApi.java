package com.example.mustore.api;

import com.example.mustore.common.Constant;
import com.example.mustore.retrofit.request.FCMToken;
import com.example.mustore.retrofit.response.MyOrders;
import com.example.mustore.retrofit.response.Product;
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
import retrofit2.http.Query;

public interface OrderApi {

    Gson gson = new GsonBuilder().create();

    OrderApi orderApi = new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/order/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(OrderApi.class);



    @POST("create-order")
    Call<MyOrders> createOrder(@Header("Authorization") String token,
                               @Query("address") String address,
                               @Body List<Product> products);
    @GET("my-order")
    Call<List<MyOrders>> myOrders(@Header("Authorization") String token);
}
