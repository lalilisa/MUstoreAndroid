package com.example.mustore.api;

import com.example.mustore.common.Constant;
import com.example.mustore.retrofit.response.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface CategoryApi {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    CategoryApi categoryApi = new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/category/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CategoryApi.class);

    @GET("all")
    Call<List<Category>> getCategory();
}
