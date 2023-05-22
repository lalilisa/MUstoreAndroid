package com.example.mustore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.adapter.UserAdapter;
import com.example.mustore.api.UserApi;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.response.UserView;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    ImageView back;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<UserView> userViewList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_chat_activity);
        init();
        fetch();
    }
    private void init(){
        recyclerView=findViewById(R.id.userRecyclerView);
        back=findViewById(R.id.imgBack);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
        userAdapter=new UserAdapter(this,this);
        userAdapter.setUserViewList(userViewList);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        back.setOnClickListener(view -> {
            Intent intent=new Intent(UserChatActivity.this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void fetch(){
        String token= Utils.getAccessToken(this);
        String bearerToken=Utils.createBearerToken(token);
        UserApi.userApi.getUsers(bearerToken).enqueue(new Callback<List<UserView>>() {
            @Override
            public void onResponse(Call<List<UserView>> call, Response<List<UserView>> response) {
                if(response.code()==200){
                    userViewList=response.body();
                    userAdapter.setUserViewList(userViewList);
                    System.out.println("ssss "+userViewList);
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<UserView>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch();
    }
}
