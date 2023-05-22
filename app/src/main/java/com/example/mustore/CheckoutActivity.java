package com.example.mustore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mustore.adapter.CheckoutAdapter;
import com.example.mustore.api.OrderApi;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.response.MyOrders;
import com.example.mustore.retrofit.response.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    RecyclerView checkoutItems;

    CheckoutAdapter checkoutAdapter;
    List<Product> products=new ArrayList<>();

    EditText address;
    private Button payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        init();
        onPayment();
    }
    private void init(){
        payment=findViewById(R.id.payment);
        checkoutItems=findViewById(R.id.order_details_pro_recycler_view);
        address=findViewById(R.id.address);
        checkoutAdapter=new CheckoutAdapter(this);
//        Long id, String code, Integer quantity, Double discount, Double price, String name, String img, Integer view, Long categoryId, String description, String categoryName
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"ss","ss"));
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"Ss","ss"));
        Intent intent=getIntent();
        products= (List<Product>) intent.getSerializableExtra("order");
        checkoutAdapter.setProducts(products!=null?products:new ArrayList<>());
        checkoutItems.setAdapter(checkoutAdapter);
        checkoutItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    private void onPayment(){
        payment.setOnClickListener(view -> {
            String token= Utils.getAccessToken(this);
            String bearerToken=Utils.createBearerToken(token);
            System.out.println("sssss "+bearerToken);
            String addressReq=address.getText().toString();
            OrderApi.orderApi.createOrder(bearerToken,addressReq,products).enqueue(new Callback<MyOrders>() {
                @Override
                public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                    Intent intent=new Intent(CheckoutActivity.this,OrderActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<MyOrders> call, Throwable t) {

                }
            });
        });
    }
}