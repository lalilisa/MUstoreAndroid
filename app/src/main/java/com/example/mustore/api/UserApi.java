package com.example.mustore.api;

import com.example.mustore.common.Constant;
import com.example.mustore.retrofit.request.ChangePassword;
import com.example.mustore.retrofit.request.UpdateUser;
import com.example.mustore.retrofit.response.ResponseMessage;
import com.example.mustore.retrofit.response.UserView;
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
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserApi {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    UserApi userApi = new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/user/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserApi.class);

    @GET("user-info")
    Call<UserView> getUserInfo(@Header("Authorization") String accToken);

    @PUT("user-info")
    Call<UserView> updateUserInfo(@Header("Authorization") String accToken, @Body UpdateUser updateUser);

    @PUT("change-password")
    Call<ResponseMessage> changePassword(@Header("Authorization") String accToken, @Body ChangePassword changePassword);

    @GET("list-user")
    Call<List<UserView> >getUsers(@Header("Authorization") String accToken);


}
