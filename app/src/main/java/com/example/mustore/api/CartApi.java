package com.example.mustore.api;

import android.content.SharedPreferences;

import com.example.mustore.common.Constant;
import com.example.mustore.retrofit.request.UpdateCart;
import com.example.mustore.retrofit.response.CartDetail;
import com.example.mustore.retrofit.response.Product;
import com.example.mustore.retrofit.response.ResponseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CartApi {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();


    CartApi cart = new Retrofit.Builder()
            .baseUrl(Constant.baseUrl + "/api/cart/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CartApi.class);


    @GET("all")
    Call<List<Product>> getMyCart(@Header("Authorization") String accToken);

    @POST("add-to-cart")
    Call<CartDetail> addToCart(@Header("Authorization") String accToken,@Query("status") Integer status , @Body UpdateCart updateCart);

    @PUT("decrement-item")
    Call<CartDetail> decrementToMyCart(@Header("Authorization") String accToken, @Body UpdateCart updateCart);

    @DELETE("remove-item")
    Call<ResponseMessage> delete(@Header("Authorization") String accToken, @Query("productId") Long productId);
}
