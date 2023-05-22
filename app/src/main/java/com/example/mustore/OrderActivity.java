package com.example.mustore;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.adapter.CheckoutAdapter;
import com.example.mustore.adapter.OrderAdapter;
import com.example.mustore.api.OrderApi;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.response.MyOrders;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;

    private List<MyOrders> myOrders=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_orders);
        init();
    }

    private void init(){
        recyclerView=findViewById(R.id.order_all_orders_recycler_view);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
        orderAdapter=new OrderAdapter(this,OrderActivity.this);
        orderAdapter.setOrder(myOrders);
        fetch();
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

    }
    private void fetch(){
        String token= Utils.getAccessToken(this);
        String bearerToken=Utils.createBearerToken(token);
        OrderApi.orderApi.myOrders(bearerToken).enqueue(new Callback<List<MyOrders>>() {
            @Override
            public void onResponse(Call<List<MyOrders>> call, Response<List<MyOrders>> response) {
                myOrders=response.body();
                orderAdapter.setOrder(myOrders);
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<MyOrders>> call, Throwable t) {

            }
        });
    }
}
