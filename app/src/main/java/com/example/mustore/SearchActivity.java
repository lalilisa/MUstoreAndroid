package com.example.mustore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.adapter.ProductItemGridAdapter;
import com.example.mustore.adapter.SpinnerAdapter;
import com.example.mustore.api.CategoryApi;
import com.example.mustore.api.ProductApi;
import com.example.mustore.common.Constant;
import com.example.mustore.model.HomeSearch;
import com.example.mustore.retrofit.response.Category;
import com.example.mustore.retrofit.response.Product;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {

    private ImageView filter;
    private RecyclerView recyclerView;
    private ProductItemGridAdapter productItemGridAdapter;
    private EditText editText;
    private ImageView searchIcon;
    private LinearLayout linearLayout;
    private SeekBar seekBar;
    private Spinner cate;
    private Spinner sort;
    TextView priceInRange;
    List<Category> categories=new ArrayList<>();
    SpinnerAdapter spinnerAdapter;
    private List<Product> products=new ArrayList<>();
    private Category category=new Category();
    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        init();
        onClickEvent();
        Intent  intent=getIntent();

        HomeSearch homeSearch= (HomeSearch) intent.getSerializableExtra("search");
        if(homeSearch!=null){
            Map<String,Object> searchMap=new HashMap<>(Constant.queryMap);
            searchMap.put("search",homeSearch.getKeyword());
            searchMap.put("filter",String.format("{\"categoryId\":\"%s\"}",homeSearch.getCategoryId()));

            ProductApi.productApi.getProduct(searchMap).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if(response.code()==200){
                        products=response.body();
                        productItemGridAdapter.setProduct(products);
                    }
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    shimmerFrameLayout.stopShimmerAnimation();
                    recyclerView.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });

        }
        else {
            fetchProduct();
        }
    }

    private void init(){
        filter=findViewById(R.id.filter);
        recyclerView=findViewById(R.id.search_product);
        searchIcon=findViewById(R.id.search_icon);
        editText=findViewById(R.id.search_view);
        linearLayout=findViewById(R.id.layout2);
        seekBar=findViewById(R.id.range_price);
        seekBar.setVisibility(View.GONE);
        cate=findViewById(R.id.spiner_cate);
        sort=findViewById(R.id.spiner_sort);
        priceInRange=findViewById(R.id.price);
        productItemGridAdapter=new ProductItemGridAdapter(this);
        productItemGridAdapter.setProduct(products);
        recyclerView.setHasFixedSize(true);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
        recyclerView.setAdapter(productItemGridAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        List<String> strings=new ArrayList<String>(){{add(Constant.SortCondition.PRICE_ASC);add(Constant.SortCondition.PRICE_DESC);};};
        spinnerAdapter=new SpinnerAdapter(this,categories);
        fetchCategory();
        cate.setAdapter(spinnerAdapter);
        sort.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_sort,strings));
        recyclerView.scrollToPosition(products.size());
    }

    private void onClickEvent(){
        filter.setOnClickListener(view -> {
            linearLayout.setVisibility(linearLayout.getVisibility()==View.GONE? View.VISIBLE:View.GONE);
        });
        cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category= (Category) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchIcon.setOnClickListener(view -> {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmerAnimation();
            recyclerView.setVisibility(View.INVISIBLE);
            Map<String,Object> searchMap=new HashMap<>(Constant.queryMap);
            String keyword=editText.getText().toString();
            String sortCondition =  sort.getSelectedItem().toString();
            searchMap.put("search",keyword);
            searchMap.put("filter",String.format("{\"categoryId\":\"%s\"}",category.getId()));
            switch (sortCondition){
                case Constant.SortCondition.PRICE_ASC :{
                        searchMap.put("sort","price-");
                        break;
                }
                case  Constant.SortCondition.PRICE_DESC:{
                     searchMap.put("sort","price+");
                     break;
                }
            }
            ProductApi.productApi.getProduct(searchMap).enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    if(response.code()==200){
                        products=response.body();
                        productItemGridAdapter.setProduct(products);
                    }
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                    shimmerFrameLayout.stopShimmerAnimation();
                    recyclerView.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {

                }
            });
        });
    }
    private void fetchCategory(){
        try {
            CategoryApi.categoryApi.getCategory().enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    categories=response.body();
                    spinnerAdapter.setCategories(categories);
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {

                }
            });

        }
        catch (Exception e){
            System.out.println(e);
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            categories=new ArrayList<>();
        }
    }
    private void fetchProduct(){

        ProductApi.productApi.getProduct(Constant.queryMap).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                System.out.println(response.body());
                products=response.body();
                if(products==null)
                    products=new ArrayList<>();
                productItemGridAdapter.setProduct(products);
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                shimmerFrameLayout.stopShimmerAnimation();
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
//                System.out.println(t.getMessage());
//                        Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        fetchProduct();
    }
}
