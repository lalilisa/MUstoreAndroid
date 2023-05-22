package com.example.mustore.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.mustore.R;
import com.example.mustore.api.CartApi;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.request.OrderItem;
import com.example.mustore.retrofit.request.UpdateCart;
import com.example.mustore.retrofit.response.CartDetail;
import com.example.mustore.retrofit.response.Product;
import com.example.mustore.retrofit.response.ResponseMessage;

import org.checkerframework.checker.units.qual.C;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutHolder>{

    public interface OnItemCheckListener {
        void onItemCheck(OrderItem item);
        void onItemUncheck(OrderItem item);
    }

    @NonNull
    private OnItemCheckListener onItemClick;
    private List<Product>  products;
    private Context context;

    public CheckoutAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public CheckoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,parent,false);
        return new CheckoutHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull CheckoutHolder holder, int position) {
        Product product=products.get(position);

        if(product.getImg()!=null) {
            Glide.with(context)
                    .load(product.getImg())
                    .into(holder.img);
        }
        else {

            Glide.with(context).clear(holder.img);
        }

        holder.name.setText(product.getName());
        holder.price.setText(Double.toString(product.getPrice()));
        holder.quantity.setText("Số lượng: "+ product.getQuantity());
        holder.remove.setVisibility(View.INVISIBLE);
        holder.checkbox.setVisibility(View.INVISIBLE);
        holder.less.setVisibility(View.GONE);
        holder.more.setVisibility(View.GONE);
        holder.l1.setVisibility(View.GONE);
        holder.l2.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProducts(List<Product> products){
        this.products=products;
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemCheckListener onItemClick){
        this.onItemClick=onItemClick;
    }
    public class CheckoutHolder extends RecyclerView.ViewHolder{

        private TextView price;
        private ImageView img;
        private TextView name;
        private TextView quantity;
        private ImageView more;
        private ImageView less;

        private ImageView remove;
        private CheckBox checkbox;

        private LinearLayout l1;
        private LinearLayout l2;
        public CheckoutHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.cartPrice);
            img=itemView.findViewById(R.id.cartImage);
            name=itemView.findViewById(R.id.cartName);
            more=itemView.findViewById(R.id.cartMore);
            less=itemView.findViewById(R.id.cartLess);
            quantity=itemView.findViewById(R.id.quantityTvCart);
            checkbox=itemView.findViewById(R.id.check);
            remove=itemView.findViewById(R.id.cartClear);
            l1=itemView.findViewById(R.id.minusLayout);
            l2=itemView.findViewById(R.id.parentAdd);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
