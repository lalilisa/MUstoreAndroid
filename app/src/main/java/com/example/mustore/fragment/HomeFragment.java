package com.example.mustore.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mustore.R;
import com.example.mustore.SearchActivity;
import com.example.mustore.adapter.CategoryAdapter;
import com.example.mustore.adapter.ProductItemAdapter;
import com.example.mustore.adapter.ProductItemGridAdapter;
import com.example.mustore.adapter.SliderAdapter;
import com.example.mustore.api.CategoryApi;
import com.example.mustore.api.ProductApi;
import com.example.mustore.common.Constant;
import com.example.mustore.model.HomeSearch;
import com.example.mustore.model.SlideItem;
import com.example.mustore.retrofit.response.Category;
import com.example.mustore.retrofit.response.Product;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View view;
    private ViewPager2 viewPager2;

    private CategoryAdapter categoryAdapter;

    private SliderAdapter adapter;

    private ProductItemAdapter productItemAdapter;

    private RecyclerView recyclerViewCate;
    private RecyclerView recyclerViewPro;
    List<SlideItem> items=new ArrayList<>();
    List<Category> categories=new ArrayList<>();
    List<Product> products=new ArrayList<>();
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerViewPopular;

    private ShimmerFrameLayout shimmerFrameLayout;
    private ImageView searchView;

    private EditText searchInput;
    private ProductItemGridAdapter productItemGridAdapter;

    private ConstraintLayout parentData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initSlider();
        initCategory();
        onClickEvent();


    }

    private void init(View views) {
        viewPager2=views.findViewById(R.id.viewPager2);
        recyclerViewCate=views.findViewById(R.id.recycler_view_category);
        recyclerViewPro=views.findViewById(R.id.recycler_view_product);
        recyclerViewPopular=views.findViewById(R.id.product_popular);
        searchView=views.findViewById(R.id.search_icon);
        searchInput=views.findViewById(R.id.search_view);
        items=new ArrayList<>();
        shimmerFrameLayout=views.findViewById(R.id.shimmer_view_container);
        parentData=views.findViewById(R.id.layout_data);
        items.add(new SlideItem(R.drawable.banner5));
        items.add(new SlideItem(R.drawable.banner4));
        items.add(new SlideItem(R.drawable.banner6));
        items.add(new SlideItem(R.drawable.banner7));
        shimmerFrameLayout.startShimmerAnimation();

    }

    private void initCategory(){
        categoryAdapter=new CategoryAdapter(getContext());
        productItemAdapter=new ProductItemAdapter(getContext());
        productItemGridAdapter=new ProductItemGridAdapter(getContext());
        fetchCategory();
        fetchProduct();
        fetchProductPopular();
//        fetchProduct();
//            public Product(Long id, String code, Integer quantity, Double discount, Double price, String name, String img, Integer view, Long categoryId) {

//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L));
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L));

        categoryAdapter.setCategories(categories);
        productItemAdapter.setProduct(products);
        productItemGridAdapter.setProduct(new ArrayList<>());
        recyclerViewCate.setAdapter(categoryAdapter);
        recyclerViewCate.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerViewPro.setAdapter(productItemAdapter);
        recyclerViewPro.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerViewPopular.setAdapter(productItemGridAdapter);
        gridLayoutManager=new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerViewPopular.setLayoutManager(gridLayoutManager);
        recyclerViewPopular.setItemAnimator(new DefaultItemAnimator());

    }
    private void initSlider(){
        adapter=new SliderAdapter(items,viewPager2);
        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(true);
        viewPager2.setOffscreenPageLimit(items.size());
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.SCROLL_STATE_IDLE);
        viewPager2.setPageTransformer(null);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHander.removeCallbacks(slideRunnable);
                slideHander.postDelayed(slideRunnable,3000);
            }
        });
    }
    private final Handler slideHander=new Handler();
    private final Runnable slideRunnable=new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem()==items.size()-1 ){
                try {
                    Thread.sleep(1000);
                    viewPager2.setCurrentItem(0,false);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else
              viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slideHander.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHander.postDelayed(slideRunnable,2500);
        fetchCategory();
        fetchProduct();
        fetchProductPopular();
    }

    private void fetchProduct(){
        System.out.println("Map");
        System.out.println(Constant.queryMap);
        ProductApi.productApi.getProduct(Constant.queryMap).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                products=response.body();
                if(products==null)
                    products=new ArrayList<>();
                productItemAdapter.setProduct(products);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
//                System.out.println(t.getMessage());
//                        Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void fetchCategory(){
        try {
            CategoryApi.categoryApi.getCategory().enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    categories=response.body();
                    categoryAdapter.setCategories(categories);
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {

                }
            });

        }
        catch (Exception e){
            System.out.println(e);
            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
            categories=new ArrayList<>();
        }
    }

    private void fetchProductPopular(){
        ProductApi.productApi.getProduct(Constant.queryMap).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> popular=response.body();
                if(popular==null)
                    popular=new ArrayList<>();

                productItemGridAdapter.setProduct(popular);
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                parentData.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t.getMessage());
//                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    };

   private void onClickEvent(){
       searchView.setOnClickListener(view -> {
           Intent intent=new Intent(getActivity(), SearchActivity.class);
           intent.putExtra("search",new HomeSearch(searchInput.getText().toString(),-1L));
           startActivity(intent);
       });
   }
}
