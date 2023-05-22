package com.example.mustore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mustore.api.UserApi;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.request.ChangePassword;
import com.example.mustore.retrofit.response.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldpass;

    EditText newpass;
    EditText confirmpass;

    Button changepass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        init();

    }

    private void init(){
        oldpass=findViewById(R.id.old_pass);
        newpass=findViewById(R.id.new_pass);
        changepass=findViewById(R.id.change);
        confirmpass=findViewById(R.id.confirm_password);
        changepass.setOnClickListener(view -> changePassword());
    }

    private void changePassword(){
        String token= Utils.getAccessToken(this);
        String bearerToken=Utils.createBearerToken(token);
        String old=oldpass.getText().toString() ;
        String news=newpass.getText().toString();
        String confirm=confirmpass.getText().toString() ;
        if(old.isEmpty()||
                confirm.isEmpty()||
                news.isEmpty()
        )
            Toast.makeText(this,"Thông tin không được để trống",Toast.LENGTH_SHORT).show();
        if(!confirm.equals(news))
            Toast.makeText(this,"Mật khẩu xác thực không chính xác ",Toast.LENGTH_SHORT).show();
        UserApi.userApi.changePassword(bearerToken,new ChangePassword(old,news)).enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                if(response.code()==200){
                    if(response.body().getMessage().equals("SUCCESS"))
                     Toast.makeText(ChangePasswordActivity.this,"Đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(ChangePasswordActivity.this,"Đổi mật khẩu thất bai",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ChangePasswordActivity.this,"Đổi mật khẩu thất bai",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {

            }
        });
    }
}
