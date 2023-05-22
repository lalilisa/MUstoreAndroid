package com.example.mustore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.adapter.CheckoutAdapter;
import com.example.mustore.retrofit.response.MyOrders;
import com.example.mustore.retrofit.response.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    RecyclerView orderDetailItems;

    CheckoutAdapter orderDetailAdapter;
    List<Product> products=new ArrayList<>();
    MyOrders myOrders=new MyOrders();

    private TextView dateShip;
    private TextView address;
    private TextView status;

    private TextView totalItem;
    private TextView totalPice;
    private TextView shipFree;

    private TextView charge;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_details);
        init();
    }
    @SuppressLint("SetTextI18n")
    private void init(){
        orderDetailItems=findViewById(R.id.order_details_pro_recycler_view);
        dateShip=findViewById(R.id.ship_date_value_tv);
        address=findViewById(R.id.ship_add_value_tv);
        status=findViewById(R.id.ship_curr_status_value_tv);
        totalItem=findViewById(R.id.price_items_amount_tv);
        shipFree=findViewById(R.id.price_shipping_amount_tv);
        charge=findViewById(R.id.price_charges_amount_tv);
        totalPice=findViewById(R.id.price_total_amount_tv);
        orderDetailAdapter=new CheckoutAdapter(this);
//        Long id, String code, Integer quantity, Double discount, Double price, String name, String img, Integer view, Long categoryId, String description, String categoryName
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"ss","ss"));
//        products.add(new Product(1L,"12",12,(double)121,(double)1212,"Sss","sss",121,1L,"Ss","ss"));
        Intent intent=getIntent();
        myOrders= (MyOrders) intent.getSerializableExtra("order_items");
        orderDetailAdapter.setProducts(myOrders!=null?myOrders.getProductViews():new ArrayList<>());
        orderDetailItems.setAdapter(orderDetailAdapter);
        orderDetailItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        address.setText(myOrders.getAddress());
        int numbers= myOrders.getProductViews().stream().mapToInt(Product::getQuantity).sum();
        totalItem.setText(""+numbers);
        totalPice.setText(Double.toString(myOrders.getTotalPrice()));
        charge.setText(Double.toString(myOrders.getTotalPrice()));
        shipFree.setText("0");
        switch (myOrders.getStatus()){
            case 0:{
                status.setText("Chờ xác nhận");

                break;
            }
            case 1:{
                status.setText("Đang giao");
                String dates="";
                dates=simpleDateFormat.format(myOrders.getCreatedAt());
                dateShip.setText(dates);
                break;
            }
            case 2:{
                status.setText("Đã hoàn tất");
                String dates="";
                dates=simpleDateFormat.format(myOrders.getCreatedAt());
                dateShip.setText(dates);
                break;
            }
        }

    }
}
