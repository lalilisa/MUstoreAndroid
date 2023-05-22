package com.example.mustore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mustore.adapter.ProductItemAdapter;
import com.example.mustore.api.CartApi;
import com.example.mustore.api.ProductApi;
import com.example.mustore.common.Constant;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.request.UpdateCart;
import com.example.mustore.retrofit.response.CartDetail;
import com.example.mustore.retrofit.response.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView back;
    private ImageView detailImage;
    private TextView name;
    private TextView price;
    private TextView descrption;
    private TextView category;
    private TextView discount;
    private TextView afterDiscount;
    private RecyclerView recyclerView;
    private Button addToCart;
    private ProductItemAdapter productItemAdapter;
    private TextView quantity;
    Product  product;
    ImageView searchIcon;
    private ProgressBar progressBar;
    private LinearLayout bgSucess;
    private ImageView success;
    private List<Product> products=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
        fillData();
        onClickEvent();
    }

    private void init(){
        back=findViewById(R.id.backIv_ProfileFrag);
        detailImage=findViewById(R.id.productImage_ProductDetailsPage);
        name=findViewById(R.id.productName_ProductDetailsPage);
        price=findViewById(R.id.productPrice_ProductDetailsPage);
        descrption=findViewById(R.id.productDes_ProductDetailsPage);
        recyclerView=findViewById(R.id.RecomRecView_ProductDetailsPage);
        addToCart=findViewById(R.id.addToCart_ProductDetailsPage);
        category=findViewById(R.id.category);
        discount=findViewById(R.id.discount);
        success=findViewById(R.id.success_image);
        bgSucess=findViewById(R.id.check_success);
        progressBar=findViewById(R.id.progess_bar);
        progressBar.setVisibility(View.INVISIBLE);
        bgSucess.setVisibility(View.INVISIBLE);
        success.setVisibility(View.INVISIBLE);
        afterDiscount=findViewById(R.id.after_discount);
        quantity=findViewById(R.id.quantity);
        searchIcon=findViewById(R.id.searchIv_ProfileFrag);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDetailActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }
    private void fillData(){

        Intent  intent=getIntent();
        product= (Product) intent.getSerializableExtra("product");
        productItemAdapter=new ProductItemAdapter(this);
        productItemAdapter.setProduct(products);
        fetchRelationProduct(product.getCategoryId());
        fetchDetailProduct(product.getId());
        recyclerView.setAdapter(productItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

    }

    private void onClickEvent(){
        back.setOnClickListener(view -> {
            finish();
        });

        addToCart.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate();
            String token= com.example.mustore.common.Utils.getAccessToken(ProductDetailActivity.this);
            String bearerToken= Utils.createBearerToken(token);
            CartApi.cart.addToCart(bearerToken,1,new UpdateCart(product.getId(),1)).enqueue(new Callback<CartDetail>() {
                @Override
                public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                    progressBar.setVisibility(View.INVISIBLE);
                    bgSucess.setVisibility(View.VISIBLE);
                    success.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(() -> {
                        bgSucess.setVisibility(View.INVISIBLE);
                        success.setVisibility(View.INVISIBLE);
                    },1500);
                }

                @Override
                public void onFailure(Call<CartDetail> call, Throwable t) {

                }
            });
        });
    }

    private  void fetchRelationProduct(Long categoryId){
        Map<String,Object> query= new HashMap<>(Constant.queryMap);
        query.put("filter",String.format("{\"categoryId\":\"%s\"}",categoryId));
        ProductApi.productApi.getProduct(query).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products=response.body();
                productItemAdapter.setProduct(products);
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void fetchDetailProduct(Long id){
        ProductApi.productApi.getOne(id).enqueue(new Callback<Product>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product=response.body();
                if(product!=null) {
                    double discountPrice =  product.getPrice()*(1-product.getDiscount()/100);
                    name.setText(product.getName());
                    price.setText(product.getPrice().toString());
                    descrption.setText(product.getDescription());
                    category.setText("Phân loại : "+product.getCategoryName());
                    discount.setText("( Giảm "+ product.getDiscount()+" % )");
                    afterDiscount.setText(Double.toString(discountPrice));
                    if(product.getImg()!=null) {
                        Glide.with(getApplicationContext())
                                .load(product.getImg())
                                .into(detailImage);
                    }
                    else {
                        Glide.with(getApplicationContext()).clear(detailImage);
                    }
                    quantity.setText("Số lượng : "+product.getQuantity().toString());
                    price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                }

            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDetailProduct(product.getId());
        fetchRelationProduct(product.getCategoryId());
    }
}