package com.example.mustore.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.OrderDetailActivity;
import com.example.mustore.R;
import com.example.mustore.retrofit.response.MyOrders;
import com.example.mustore.retrofit.response.Product;

import java.io.Serializable;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder>{

    List<MyOrders> myOrders;

    Context context;
    Activity activity;

    public OrderAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_summary_card,parent,false);
        return new OrderHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        MyOrders myOrders1=myOrders.get(position);
        int numbers= myOrders1.getProductViews().stream().mapToInt(Product::getQuantity).sum();
        holder.numberItem.setText(""+numbers);
        holder.total.setText(Double.toString(myOrders1.getTotalPrice()));
        holder.code.setText(myOrders1.getCode());
        holder.date.setText(myOrders1.getCreatedAt().toString());

        switch (myOrders1.getStatus()){
            case 0:{
                holder.status.setText("Chờ xác nhận");
                break;
            }
            case 1:{
                holder.status.setText("Đang giao");
                break;
            }
            case 2:{
                holder.status.setText("Đã hoàn tất");
                break;
            }
        }
        holder.cardView.setOnClickListener(view -> {
            Intent intent=new Intent(context, OrderDetailActivity.class);
            intent.putExtra("order_items", myOrders1);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setOrder(List<MyOrders> myOrders){
        this.myOrders=myOrders;
        notifyDataSetChanged();
    }
    public static class OrderHolder extends RecyclerView.ViewHolder {
        TextView code;
        TextView date;
        TextView status;
        TextView numberItem;
        TextView total;

        CardView cardView;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.order_summary_id_tv);
            date=itemView.findViewById(R.id.order_summary_date_tv);
            status=itemView.findViewById(R.id.order_summary_status_value_tv);
            total=itemView.findViewById(R.id.order_summary_total_amount_tv);
            numberItem=itemView.findViewById(R.id.order_summary_items_count_tv);
            cardView=itemView.findViewById(R.id.order_summary_card);
        }
    }
}
