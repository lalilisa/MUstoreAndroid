package com.example.mustore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.R;
import com.example.mustore.SearchActivity;
import com.example.mustore.common.Constant;
import com.example.mustore.model.HomeSearch;
import com.example.mustore.retrofit.response.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private List<com.example.mustore.retrofit.response.Category> categories;


    private final Context  context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
            Category category=categories.get(position);
            holder.name.setText(category.getName());

            holder.linearLayout.setOnClickListener(view -> {
                Intent intent=new Intent(context, SearchActivity.class);
                intent.putExtra("search",new HomeSearch("",category.getId()));
                context.startActivity(intent);
            });
            switch (category.getCode()){
                case Constant.CodeCategory.BALL : {
                    holder.img.setImageResource(R.drawable.mu_ball);
                    holder.linearLayout.setBackground(ContextCompat.getDrawable(holder.linearLayout.getContext(),R.drawable.cate_backgraound_ball));
                    break;
                }
                case Constant.CodeCategory.HAT:  {
                    holder.img.setImageResource(R.drawable.sport_hat);
                    holder.linearLayout.setBackground(ContextCompat.getDrawable(holder.linearLayout.getContext(),R.drawable.cate_backgraound_hat));
                    break;
                }
                case Constant.CodeCategory. FOOTBALLSHIRT: {
                    holder.img.setImageResource(R.drawable.mu_cloth);
                    holder.linearLayout.setBackground(ContextCompat.getDrawable(holder.linearLayout.getContext(),R.drawable.cate_backgraound_ao));
                    break;
                }
                case Constant.CodeCategory.SHOE : {
                    holder.img.setImageResource(R.drawable.shoe_icon);
                    holder.linearLayout.setBackground(ContextCompat.getDrawable(holder.linearLayout.getContext(),R.drawable.cate_backgraound_shoe));
                    break;
                }
                case Constant.CodeCategory.POSTER: {
                    holder.img.setImageResource(R.drawable.poster);
                    holder.linearLayout.setBackground(ContextCompat.getDrawable(holder.linearLayout.getContext(),R.drawable.cate_backgraound_poster));
                    break;
                }
            }

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCategories(List<com.example.mustore.retrofit.response.Category> categories){
        this.categories=categories;
        notifyDataSetChanged();
    }
    static class CategoryHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayout;
        private ImageView img;
        private TextView name;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.itemImage);
            name=itemView.findViewById(R.id.tvTitle);
            linearLayout=itemView.findViewById(R.id.main_layout_category);
        }
    }
}
