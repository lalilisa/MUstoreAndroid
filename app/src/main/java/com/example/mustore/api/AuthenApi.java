package com.example.mustore.api;

import com.example.mustore.common.Constant;
import com.example.mustore.retrofit.request.ChangePassword;
import com.example.mustore.retrofit.request.LoginRequest;
import com.example.mustore.retrofit.request.PhonenumberRequest;
import com.example.mustore.retrofit.request.RegisterRequest;
import com.example.mustore.retrofit.response.LoginResponse;
import com.example.mustore.retrofit.response.ResponseMessage;
import com.example.mustore.retrofit.response.SendOtpView;
import com.example.mustore.retrofit.response.UserView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthenApi {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    AuthenApi authenApi = new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/auth/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AuthenApi.class);


    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("send-otp")
    Call<SendOtpView> sendOtp(@Body PhonenumberRequest request);

    @POST("verifi-otp")
    Call<LoginResponse> verifiOtp(@Body SendOtpView request);


    @POST("register")
    Call<UserView> reigster(@Body RegisterRequest registerRequest);


    @POST("change-password")
    Call<ResponseMessage> changpassWord(@Header("Authorization")String token, @Body ChangePassword changePassword);

    @POST("forget-password")
    Call<ResponseMessage>fors(@Query("email") String email);
}
