package com.example.mustore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mustore.adapter.ProductItemGridAdapter;
import com.example.mustore.api.UserApi;
import com.example.mustore.common.Category;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.request.UpdateUser;
import com.example.mustore.retrofit.response.Product;
import com.example.mustore.retrofit.response.UserView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText fullname;
    private TextInputEditText phone;
    private TextInputEditText address;
    private TextInputEditText dob;

    private ImageView img;

    private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        onClickEvent();

    }

    private void init(){
        email=findViewById(R.id.email_input);
        fullname=findViewById(R.id.full_name_input);
        phone=findViewById(R.id.phone_input);
        address=findViewById(R.id.address_input);
        dob=findViewById(R.id.dob_input);
        update=findViewById(R.id.btn_update_profile);
        img=findViewById(R.id.profile_image);
        if(Utils.getRole(this).equals(Category.Role.USER.name()))
            img.setImageResource(R.drawable.user_image);
        else
            img.setImageResource(R.drawable.admin);
        fetchProfile();
    }

    private void onClickEvent(){
        dob.setOnClickListener(view -> {
            final Calendar calendar=Calendar.getInstance();
            int year= calendar.get(Calendar.YEAR);
            int day= calendar.get(Calendar.DAY_OF_MONTH);
            int month= calendar.get(Calendar.MONTH);
            @SuppressLint("SimpleDateFormat") DatePickerDialog dialog=new DatePickerDialog(ProfileActivity.this, (datePicker, y, m, d) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(y, m, d);
                dob.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
            },year,month,day);
            dialog.show();
        });
        update.setOnClickListener(view -> {
            try {
                updateProfile();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void fetchProfile(){
        String token= Utils.getAccessToken(this);
        String bearerToken=Utils.createBearerToken(token);
        UserApi.userApi.getUserInfo(bearerToken).enqueue(new Callback<UserView>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<UserView> call, Response<UserView> response) {
                if(response.code()==200){
                    UserView userView=response.body();
                    email.setText(userView.getEmail());
                    fullname.setText(userView.getFullname());
                    address.setText(userView.getAddress());
                    if(userView.getDob()!=null)
                     dob.setText(new SimpleDateFormat("dd/MM/yyyy").format(userView.getDob()));
                    phone.setText(userView.getPhonnumber());
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<UserView> call, Throwable t) {
                System.out.println("ttt "+t.getMessage());
            }
        });

    }
    @SuppressLint("SimpleDateFormat")
    private void updateProfile() throws ParseException {
        String token= Utils.getAccessToken(this);
        String bearerToken=Utils.createBearerToken(token);
        UpdateUser updateUser=new UpdateUser();
        updateUser.setAddress(address.getText().toString());
        updateUser.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(dob.getText().toString()));
        updateUser.setName(fullname.getText().toString());
        updateUser.setEmail(email.getText().toString());
        updateUser.setPhonenumber(phone.getText().toString());
        UserApi.userApi.updateUserInfo(bearerToken,updateUser).enqueue(new Callback<UserView>() {
            @Override
            public void onResponse(Call<UserView> call, Response<UserView> response) {
                    if(response.code()==200){
                        Toast.makeText(ProfileActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(ProfileActivity.this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();                    }


            @Override
            public void onFailure(Call<UserView> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();                    }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProfile();
    }
}