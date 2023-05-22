package com.example.mustore.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.ChatActivity;
import com.example.mustore.R;
import com.example.mustore.UserChatActivity;
import com.example.mustore.common.Category;
import com.example.mustore.common.Constant;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.response.UserView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{

    private List<UserView> userViewList;

    private Context context;
    private Activity activity;
    public UserAdapter(Context context, Activity activity) {
        this.context=context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UserView userView=userViewList.get(position);
        System.out.println("ADMIN "+userView.getRole());
        String role= Utils.getRole(activity);
        holder.avatar.setImageResource(role.equals(Category.Role.ADMIN.name()) ? R.drawable.user_image:R.drawable.admin);
        holder.name.setText(userView.getFullname()==null? userView.getUsername():userView.getFullname());
        holder.userLayout.setOnClickListener(view -> {
            Intent intent=new Intent(context, ChatActivity.class);
            intent.putExtra("user_chat",userView);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userViewList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUserViewList(List<UserView> userViewList){

        this.userViewList=userViewList;
        notifyDataSetChanged();
    }
    public static class UserHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView temp;
        private ImageView avatar;

        private LinearLayout userLayout;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.userImage);
            name=itemView.findViewById(R.id.userName);
            temp=itemView.findViewById(R.id.temp);
            userLayout=itemView.findViewById(R.id.layoutUser);
        }
    }
}
