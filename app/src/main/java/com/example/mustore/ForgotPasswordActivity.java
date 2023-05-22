package com.example.mustore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mustore.api.AuthenApi;
import com.example.mustore.retrofit.response.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        email=findViewById(R.id.email);
        button=findViewById(R.id.change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthenApi.authenApi.fors(email.getText().toString()).enqueue(new Callback<ResponseMessage>() {
                    @Override
                    public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                        if (response.code()==200)
                            Toast.makeText(ForgotPasswordActivity.this,"Check email",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ForgotPasswordActivity.this,"email không tồn tại",Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<ResponseMessage> call, Throwable t) {
                        Toast.makeText(ForgotPasswordActivity.this,"email không tồn tại",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}
