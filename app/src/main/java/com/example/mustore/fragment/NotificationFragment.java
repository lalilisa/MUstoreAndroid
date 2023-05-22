package com.example.mustore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.R;
import com.example.mustore.adapter.NotificationAdapter;
import com.example.mustore.api.NotificationsApi;
import com.example.mustore.common.Utils;
import com.example.mustore.model.Notification;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    private ShimmerFrameLayout shimmerFrameLayout;
    private NotificationAdapter adapter;
    private RecyclerView recyclerView;
    private View view;

    private List<Notification> notifications=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_notification,container,false);
//        Window window=requireActivity().getWindow();
//        new WindowInsetsControllerCompat(window,
//                window.getDecorView()).setAppearanceLightStatusBars(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fetch();
        adapter=new NotificationAdapter(getContext(),getActivity());
        adapter.setList(notifications);

//        fetch();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }
    private void init(View view){
        recyclerView=view.findViewById(R.id.notifi_recyler);
        shimmerFrameLayout=view.findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
    }

    private List<Notification> fakeData(){

        return new ArrayList<Notification>(){{
            add(new Notification(1L,"trimai",new Date()));
            add(new Notification(2L,"trimai",new Date()));
            add(new Notification(3L,"trimai",new Date()));
            add(new Notification(1L,"trimai",new Date()));
            add(new Notification(2L,"trimai",new Date()));
            add(new Notification(3L,"trimai",new Date()));
            add(new Notification(1L,"trimai",new Date()));
            add(new Notification(2L,"trimai",new Date()));
            add(new Notification(3L,"trimai",new Date()));
        }};
    }

    private void fetch(){
        String token= Utils.getAccessToken(getActivity());
        String bearerToken=Utils.createBearerToken(token);
//        bearerToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmltYWkiLCJyb2xlIjoiQURNSU4iLCJleHAiOjE3ODgxMjM4ODIsInVzZXJJZCI6MSwiaWF0IjoxNjg0NDQzODgyfQ.D-JKxfS8LIJ7Q9vd6xrVcsX30eB7c6hT1fN0CyUJpvfQPgcdJQ_-DeXmEPq1lCGaoY6-qaSbh9uL0iqO2ORrLw";
        System.out.println("bearerToken "+bearerToken);
        NotificationsApi.notificationsApi.getNotifi(bearerToken).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
               if(response.code()==200){
                   notifications=response.body();
                   adapter.setList(notifications!=null? notifications:new ArrayList<>());

                   shimmerFrameLayout.setVisibility(View.INVISIBLE);
                   shimmerFrameLayout.stopShimmerAnimation();
                   recyclerView.setVisibility(View.VISIBLE);
               }
               else {
                   Toast.makeText(getContext(),"SSS",Toast.LENGTH_SHORT).show();
                   adapter.setList(new ArrayList<>());
               }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                System.out.println("ERRR "+t.getMessage());
                adapter.setList(new ArrayList<>());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetch();
    }
}
