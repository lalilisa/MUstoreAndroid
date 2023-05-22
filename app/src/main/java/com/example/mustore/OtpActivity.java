package com.example.mustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mustore.api.AuthenApi;
import com.example.mustore.retrofit.request.PhonenumberRequest;
import com.example.mustore.retrofit.response.SendOtpView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends Activity {
    Button button;
    EditText phonenumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        init();
        onEvent();
    }

    private void init(){
        button=findViewById(R.id.buttonGetOTP);
        phonenumber=findViewById(R.id.inputMobile);
    }
    private void onEvent(){
        button.setOnClickListener(view -> {
            PhonenumberRequest request=new PhonenumberRequest(phonenumber.getText().toString());
            AuthenApi.authenApi.sendOtp(request).enqueue(new Callback<SendOtpView>() {
                @Override
                public void onResponse(Call<SendOtpView> call, Response<SendOtpView> response) {
                    if(response.code()==200){
                        SendOtpView sendOtpView=response.body();
                        Intent intent=new Intent(OtpActivity.this, VerifiActivity.class);
                        intent.putExtra("otp",sendOtpView);
                        startActivity(intent);
                    }
                    else {
                        Intent intent=new Intent(OtpActivity.this, VerifiActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<SendOtpView> call, Throwable t) {

                }
            });


        });
    }
}
