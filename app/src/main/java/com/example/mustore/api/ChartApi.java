package com.example.mustore.api;

import com.example.mustore.common.Constant;
import com.example.mustore.retrofit.response.DataCategoryChart;
import com.example.mustore.retrofit.response.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ChartApi {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();


    ChartApi chartApi= new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/chart/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ChartApi.class);


    @GET("data")
    Call<List<DataCategoryChart>> getCategoryChart(@Query("month") Integer month);
}
