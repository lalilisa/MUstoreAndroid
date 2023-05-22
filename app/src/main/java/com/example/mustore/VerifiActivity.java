package com.example.mustore;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mustore.api.AuthenApi;
import com.example.mustore.boardcast.SmsBoardcast;
import com.example.mustore.retrofit.response.LoginResponse;
import com.example.mustore.retrofit.response.SendOtpView;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifiActivity extends AppCompatActivity {

    private EditText inputCode1, inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    private TextView textMobile;
    private Button buttonVerify;
    private ProgressBar progressBar;
    private String verificationId;
    private SendOtpView sendOtpView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifi_otp);
        init();
        setupOTPInputs();
//        fillOtp();
        onEvent();
        startSmartUserConsent();

    }
    private void init(){
        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);
        buttonVerify = findViewById(R.id.buttonVerify);
        progressBar = findViewById(R.id.process_bar);
        Intent intent=getIntent();
        sendOtpView= (SendOtpView) intent.getSerializableExtra("otp");
    }

    private void onEvent(){
        buttonVerify.setOnClickListener(view -> {
            System.out.println("s's'ssssssssssssssssssssssss");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate();


            AuthenApi.authenApi.verifiOtp(sendOtpView).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.code()==200) {

                        LoginResponse loginResponse = response.body();
                        SharedPreferences sharedPreferences = VerifiActivity.this.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("accessToken", loginResponse.getAccessToken()).apply();
                        sharedPreferences.edit().putString("refreshToken", loginResponse.getRefreshToken()).apply();
                        sharedPreferences.edit().putString("userId", loginResponse.getUserId().toString()).apply();
                        sharedPreferences.edit().putString("role", loginResponse.getRole().name()).apply();
                        sharedPreferences.edit().putString("expireAccessToken", String.valueOf(loginResponse.getExpireAccressToken().getTime())).apply();
                        Intent intent = new Intent(VerifiActivity.this, MainActivity.class);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    }
                    else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(VerifiActivity.this,"OTP không hợp lệ",Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    System.out.println(t.getMessage());
                }
            });
        });
    }
    private void fillOtp(){

            String [] singleInput=sendOtpView.getOtp().split("");
            inputCode1.setText(singleInput[0]);
            inputCode2.setText(singleInput[1]);
            inputCode3.setText(singleInput[2]);
            inputCode4.setText(singleInput[3]);
            inputCode5.setText(singleInput[4]);
            inputCode6.setText(singleInput[5]);

    }

    /** When the edittext1 (inputCode1) was inserted, the cursor will be jump to the
     * next edittext (in this case it would be "inputCode2")*/
    private void setupOTPInputs(){
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    SmsBoardcast smsBroadcastReceiver;
    private static final int REQ_USER_CONSENT = 200;
    private void startSmartUserConsent() {

        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_USER_CONSENT){

            if ((resultCode == RESULT_OK) && (data != null)){

                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);


            }
            else
                return;

        }
        else
            return;

    }

    private void getOtpFromMessage(String message) {

        Pattern otpPattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()){

           fillOtp();

        }


    }

    private void registerBroadcastReceiver(){

        smsBroadcastReceiver = new SmsBoardcast();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBoardcast.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {

                startActivityForResult(intent,REQ_USER_CONSENT);

            }

            @Override
            public void onFailure() {

            }
        };

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver,intentFilter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }
}
