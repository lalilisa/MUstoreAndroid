package com.example.mustore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mustore.ProductDetailActivity;
import com.example.mustore.R;
import com.example.mustore.retrofit.response.Product;

import java.util.List;

public class ProductItemGridAdapter extends RecyclerView.Adapter<ProductItemGridAdapter.ProductHolder>{
    private List<Product> products;

    private final Context context;

    public ProductItemGridAdapter(Context context){
        this.context=context;
    }

    @NonNull
    @Override
    public ProductItemGridAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_single_product,parent,false);
        return new ProductHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductItemGridAdapter.ProductHolder holder, int position) {

        Product product=products.get(position);

        double price= product.getPrice()-product.getPrice()*product.getDiscount()/100;
        holder.price.setText(String.format("Giá: %sđ",price));
        holder.name.setText(product.getName());
        holder.discount.setText(product.getDiscount().toString());
        holder.cate.setText(String.format("Loại : %s",product.getCategoryName()));
        if(product.getImg()!=null) {
            Glide.with(context)
                    .load(product.getImg())
                    .into(holder.img);
        }
        else {
            Glide.with(context).clear(holder.img);
        }
        holder.constraintLayout.setOnClickListener(view -> {
            Intent intent=new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product",product);
            context.startActivity(intent);
        });
//        holder.ratingBar.setRating(3F);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    @SuppressLint("NotifyDataSetChanged")
    public  void setProduct(List<Product> products){
        this.products=products;
        notifyDataSetChanged();
    }
    public static final class ProductHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView price;
        private TextView name;
        private TextView cate;

        RatingBar ratingBar;
        private TextView discount;

        private ConstraintLayout constraintLayout;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.productImage_singleProduct);
            ratingBar = itemView.findViewById(R.id.productRating_singleProduct);
            cate= itemView.findViewById(R.id.productBrandName_singleProduct);
            discount = itemView.findViewById(R.id.discountTv_singleProduct);
            name = itemView.findViewById(R.id.productName_singleProduct);
            price = itemView.findViewById(R.id.productPrice_singleProduct);
            constraintLayout=itemView.findViewById(R.id.cl);
//            val discount_singleProduct = itemView.findViewById<LinearLayout>(R.id.discount_singleProduct);
        }


    }
}
