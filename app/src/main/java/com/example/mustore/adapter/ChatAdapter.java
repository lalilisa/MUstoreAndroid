package com.example.mustore.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.R;
import com.example.mustore.common.Category;
import com.example.mustore.common.Utils;
import com.example.mustore.model.ChatData;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    List<ChatData> chatData;

    private static final int ME=1;
    private static final int OTHER=0;

    private final Context context;

    private final Activity activity;


    public ChatAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity=activity;

    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        System.out.println("viewType "+viewType);
        if(viewType==ME)
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_right,parent,false);
        else
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left,parent,false);
        return new ChatHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setChatData(List<ChatData> chatData){
        this.chatData=chatData;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        ChatData chatData1=chatData.get(position);
        String role=Utils.getRole(activity);

        if(getItemViewType(position)==ME)
          holder.avatar.setImageResource(role.equals(Category.Role.ADMIN.name()) ? R.drawable.admin:R.drawable.user_image);
        else
          holder.avatar.setImageResource(role.equals(Category.Role.ADMIN.name()) ? R.drawable.user_image:R.drawable.admin);

        holder.message.setText(chatData1.getContent());

    }

    @Override
    public int getItemCount() {
        return chatData.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatData chatData1=chatData.get(position);
        Long meId= Utils.getUserId(activity);
        if(chatData1.getSenderId().equals(meId)) {

            return ME;

        }
        return OTHER;
    }

    public final class ChatHolder extends RecyclerView.ViewHolder{
        private ImageView avatar;
        private TextView message;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.userImage);
            message=itemView.findViewById(R.id.tvMessage);
        }
    }
}
