package com.example.mustore.api;

import com.example.mustore.common.Constant;
import com.example.mustore.model.ChatData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChatApi {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();


    ChatApi chatApi = new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/chat/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ChatApi.class);

    @GET("room")
    Call<List<ChatData> >getChatMessage(@Query("u1") Long u1, @Query("u2") Long u2);
}
