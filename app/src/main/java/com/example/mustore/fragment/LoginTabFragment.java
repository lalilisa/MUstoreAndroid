package com.example.mustore.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mustore.ForgotPasswordActivity;
import com.example.mustore.MainActivity;
import com.example.mustore.OtpActivity;
import com.example.mustore.R;
import com.example.mustore.api.AuthenApi;
import com.example.mustore.api.NotificationsApi;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.request.FCMToken;
import com.example.mustore.retrofit.request.LoginRequest;
import com.example.mustore.retrofit.response.DeviceToken;
import com.example.mustore.retrofit.response.LoginResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {
    EditText username,password;
    Button loginBtn;
    ImageView qrLogin;
    ProgressBar progressBar;

    TextView forgotPassword;
    private TextView otpLogin;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        onEventActivity();
        onLogin();
    }

    private void init(View view){
        otpLogin=view.findViewById(R.id.otp_tab);
        qrLogin=view.findViewById(R.id.login_qr);
        int p=800;
        username=view.findViewById(R.id.username_login);
        password=view.findViewById(R.id.password_login);
        loginBtn=view.findViewById(R.id.login_btn);
        progressBar=view.findViewById(R.id.process_bar);
        forgotPassword=view.findViewById(R.id.forgot_pasword);
        username.setTranslationX(p);
        password.setTranslationX(p);
        loginBtn.setTranslationX(p);
        int s=0;
        username.animate().translationX(s).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(s).alpha(1).setDuration(800).setStartDelay(500).start();
        loginBtn.animate().translationX(s).alpha(1).setDuration(800).setStartDelay(700).start();

        forgotPassword.setOnClickListener(view1 -> {
            Intent intent=new Intent(getActivity(), ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void onEventActivity(){
        otpLogin.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), OtpActivity.class);
            startActivity(intent);
        });
        qrLogin.setOnClickListener(view -> {
            IntentIntegrator intentIntegrator=new IntentIntegrator(getActivity());
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.setPrompt("Scan QR");
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            intentIntegrator.setBarcodeImageEnabled(true);
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(true);
//            intentIntegrator.setCaptureActivity(new CaptureActivity());
            intentIntegrator.initiateScan();
        });
    }

    private void onLogin(){
        loginBtn.setOnClickListener(view -> {
            String usernameReq=username.getText().toString();
            String passwordReq=password.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate();
            LoginRequest loginRequest=new LoginRequest(usernameReq,passwordReq);
            AuthenApi.authenApi.login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.code()==200) {
                        LoginResponse loginResponse = response.body();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("accessToken", loginResponse.getAccessToken()).apply();
                        sharedPreferences.edit().putString("refreshToken", loginResponse.getRefreshToken()).apply();
                        sharedPreferences.edit().putString("userId", loginResponse.getUserId().toString()).apply();
                        sharedPreferences.edit().putString("role", loginResponse.getRole().name()).apply();
                        sharedPreferences.edit().putString("expireAccessToken", String.valueOf(loginResponse.getExpireAccressToken().getTime())).apply();

                        if(loginResponse.getIsNotifi()==0){
                            String token = Utils.getAccessToken(getActivity());

                            sendFCMToken(Utils.createBearerToken(token));
                        }
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        progressBar.setVisibility(View.VISIBLE);
                        startActivity(intent);
                    }
                    else {
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"Username hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressBar.setVisibility(View.VISIBLE);
                    System.out.println(t.getMessage());
                    Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        });
    }

    private void sendFCMToken(String accessToken){
        String TAG="";
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    NotificationsApi.notificationsApi.sendFCMToken(accessToken,new FCMToken(token)).enqueue(new Callback<DeviceToken>() {
                        @Override
                        public void onResponse(Call<DeviceToken> call, Response<DeviceToken> response) {

                        }

                        @Override
                        public void onFailure(Call<DeviceToken> call, Throwable t) {

                        }
                    });

                    // Log and toast
                    String msg = "";
                    Log.d(TAG, msg);
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                });
    }

}
