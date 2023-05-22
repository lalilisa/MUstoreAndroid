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
import android.widget.TextView;
import android.widget.Toast;

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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>{

   public interface OnItemCheckListener {
        void onItemCheck(Product item);
        void onItemUncheck(Product item);
    }

    @NonNull
    private OnItemCheckListener onItemClick;
    private List<Product>  products;
    private Context context;
    private Activity activity;
    public CartAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,parent,false);
        return new CartHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
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
         holder.quantity.setText(Integer.toString(product.getQuantity()));
         holder.more.setOnClickListener(view -> {
             System.out.println("IDIDIDIDIDI  "+product.getId());
             String token= Utils.getAccessToken(activity);
             String bearerToken=Utils.createBearerToken(token);
             CartApi.cart.addToCart(bearerToken,0,new UpdateCart(product.getId(),1)).enqueue(new Callback<CartDetail>() {
                 @SuppressLint("NotifyDataSetChanged")
                 @Override
                 public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                     System.out.println("addd "+response.code());
                     if(response.code()==200){
                         product.setQuantity(response.body().getQuantity());
                         products.set(holder.getAdapterPosition(),product);
                         notifyDataSetChanged();
                     }
                     else
                         Toast.makeText(context,"Vượt quá số lượng",Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onFailure(Call<CartDetail> call, Throwable t) {
                     System.out.println("THrow "+t.getMessage());
                     Toast.makeText(context,"Vượt quá số lượng",Toast.LENGTH_SHORT).show();
                 }
             });
         });
         holder.less.setOnClickListener(view -> {
             String token= Utils.getAccessToken(activity);
             String bearerToken=Utils.createBearerToken(token);
             CartApi.cart.decrementToMyCart(bearerToken,new UpdateCart(product.getId(),1)).enqueue(new Callback<CartDetail>() {
                 @SuppressLint("NotifyDataSetChanged")
                 @Override
                 public void onResponse(Call<CartDetail> call, Response<CartDetail> response) {
                     if(response.code()==200){
                         product.setQuantity(response.body().getQuantity());
                         products.set(holder.getAdapterPosition(),product);
                         notifyDataSetChanged();
                     }
                     else {
                         Toast.makeText(context,"Vượt quá số lượng",Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void onFailure(Call<CartDetail> call, Throwable t) {
                     Toast.makeText(context,"Vượt quá số lượng",Toast.LENGTH_SHORT).show();
                 }
             });
         });
         holder.remove.setOnClickListener(view -> {
             String token= Utils.getAccessToken(activity);
             String bearerToken=Utils.createBearerToken(token);
             CartApi.cart.delete(bearerToken,product.getId()).enqueue(new Callback<ResponseMessage>() {
                 @Override
                 public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {

                 }

                 @Override
                 public void onFailure(Call<ResponseMessage> call, Throwable t) {

                 }
             });
             products.remove(product);
             notifyDataSetChanged();
         });
         holder.checkbox.setOnClickListener(view -> {

             if (holder.checkbox.isChecked()) {
                 onItemClick.onItemCheck(product);
             } else {
                 onItemClick.onItemUncheck(product);
             }
         });
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
    public class CartHolder extends RecyclerView.ViewHolder{

        private TextView price;
        private ImageView img;
        private TextView name;
        private TextView quantity;
        private ImageView more;
        private ImageView less;

        private ImageView remove;
        private CheckBox checkbox;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.cartPrice);
            img=itemView.findViewById(R.id.cartImage);
            name=itemView.findViewById(R.id.cartName);
            more=itemView.findViewById(R.id.cartMore);
            less=itemView.findViewById(R.id.cartLess);
            quantity=itemView.findViewById(R.id.quantityTvCart);
            checkbox=itemView.findViewById(R.id.check);
            remove=itemView.findViewById(R.id.cartClear);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
