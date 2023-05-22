package com.example.mustore.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.MainActivity;
import com.example.mustore.OrderActivity;
import com.example.mustore.R;
import com.example.mustore.UserChatActivity;
import com.example.mustore.model.Notification;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotifiItemHolder>{

    private List<Notification> notifications=new ArrayList<>();

    private Context context;
    private Activity activity;

    public NotificationAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotifiItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new NotifiItemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotifiItemHolder holder, int position) {
        Notification notification=notifications.get(position);
        holder.cotent.setText(notification.getTitle() +"\n" + notification.getContent());
//        holder.cotent.setText(notification.getContent());

        holder.date.setText(notification.getCreatedAt().toString());
        holder.itemsParrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (notification.getType()){
                    case "CART":{
                        Intent intent=new Intent(activity,MainActivity.class);
                        intent.putExtra("REDIRECT","CART");
                        context.startActivity(intent);
                        break;
                    }
                    case "ORDER":{
                        Intent intent=new Intent(activity, OrderActivity.class);
                        context.startActivity(intent);

                        break;
                    }
                    case "CHAT":{
                        Intent intent=new Intent(activity, UserChatActivity.class);
                        context.startActivity(intent);
                        break;
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Notification> items){
        this.notifications=items;
        notifyDataSetChanged();
    }


    public class NotifiItemHolder extends RecyclerView.ViewHolder {
        private TextView cotent;
        private final TextView date;

        private LinearLayout itemsParrent;

        public NotifiItemHolder(@NonNull View itemView) {
            super(itemView);
            cotent=itemView.findViewById(R.id.content_noti);
            date=itemView.findViewById(R.id.date_noti);
            ShapeableImageView imageView = itemView.findViewById(R.id.logo);
            itemsParrent=itemView.findViewById(R.id.item_noticontet);

        }


    }
}
