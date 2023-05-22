package com.example.mustore.api;

import com.example.mustore.common.Constant;
import com.example.mustore.retrofit.response.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ProductApi {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    ProductApi productApi = new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/product/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ProductApi.class);

    @GET("query")
    Call<List<Product>> getProduct(@QueryMap(encoded = true)Map<String, Object> queryMap);

    @GET("{Id}")
    Call<Product> getOne(@Path("Id") Long id);
}
