package com.example.mustore.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.CheckoutActivity;
import com.example.mustore.R;
import com.example.mustore.adapter.CartAdapter;
import com.example.mustore.api.CartApi;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.request.OrderItem;
import com.example.mustore.retrofit.response.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    private List<Product> currentSelectedItems = new ArrayList<>();
    private View view;

    private List<Product> products=new ArrayList<>();
    private RecyclerView recyclerView;

    private Button checkout;
    private CartAdapter cartAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView totalPrice;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view=inflater.inflate(R.layout.fragment_cart,container,false);
            return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        onCheckout();
    }

    private void init(View view){
        recyclerView=view.findViewById(R.id.cartRecView);
        checkout=view.findViewById(R.id.checkOut_BagPage);
        totalPrice=view.findViewById(R.id.totalPriceBagFrag);
        cartAdapter=new CartAdapter(getContext(),getActivity());
        shimmerFrameLayout=view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"",""));
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"",""));
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"",""));
//
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"",""));
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"",""));
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"",""));
        fetchCart();
        cartAdapter.setProducts(products);
        cartAdapter.setOnItemClick(new CartAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Product item) {
            currentSelectedItems.add(item);

            }
            @Override
            public void onItemUncheck(Product  item) {
                currentSelectedItems.remove(item);
            }
        });
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }


    private void fetchCart(){
        String token= Utils.getAccessToken(getActivity());
        String bearerToken=Utils.createBearerToken(token);
//        bearerToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmltYWkiLCJyb2xlIjoiQURNSU4iLCJleHAiOjE3ODgxMjM4ODIsInVzZXJJZCI6MSwiaWF0IjoxNjg0NDQzODgyfQ.D-JKxfS8LIJ7Q9vd6xrVcsX30eB7c6hT1fN0CyUJpvfQPgcdJQ_-DeXmEPq1lCGaoY6-qaSbh9uL0iqO2ORrLwmm";
        CartApi.cart.getMyCart(bearerToken).enqueue(new Callback<List<Product>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                System.out.println("sssss "+response.code());
                if(response.code()==200){
                    products=response.body();
                    cartAdapter.setProducts(products);
                    double total=  products.stream().mapToDouble(product -> (1-product.getDiscount()/100)*product.getPrice()).sum();
                    totalPrice.setText(Double.toString(total));
                }
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println();
            }
        });
    }
    private void onCheckout(){
        checkout.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), CheckoutActivity.class);
            intent.putExtra("order",(Serializable) currentSelectedItems);
            startActivity(intent);
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        recyclerView.setVisibility(View.INVISIBLE);
        fetchCart();
    }
}
