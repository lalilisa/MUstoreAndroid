package com.example.mustore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mustore.adapter.LoginRegisterFragmentAdapter;
import com.example.mustore.common.Category;
import com.example.mustore.common.Constant;
import com.example.mustore.model.LoginSocketResponse;
import com.example.mustore.model.QRRawText;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class LoginActivity extends AppCompatActivity {

    TabLayout tableLayout;
    ViewPager viewPager;
    FloatingActionButton adidas;
    FloatingActionButton pl;
    FloatingActionButton teamview;

    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tableLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        adidas=findViewById(R.id.adidas);
        pl=findViewById(R.id.pl);
        teamview=findViewById(R.id.team_view);
        progressBar=findViewById(R.id.process_bar);
        tableLayout.addTab(tableLayout.newTab().setText("Login"));
        tableLayout.addTab(tableLayout.newTab().setText("Register"));
        tableLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        LoginRegisterFragmentAdapter adapter=new LoginRegisterFragmentAdapter(
                            getSupportFragmentManager(),
                            LoginActivity.this,tableLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        int p=300;
        teamview.setTranslationX(p);
        pl.setTranslationX(p);
        adidas.setTranslationX(p);
        tableLayout.setTranslationX(p);
        int v=0;
        tableLayout.setAlpha(v);
        int s=0;
        teamview.animate().translationX(s).alpha(1).setDuration(1000).setStartDelay(200).start();
        pl.animate().translationX(s).alpha(1).setDuration(1000).setStartDelay(400).start();
        adidas.animate().translationX(s).alpha(1).setDuration(1000).setStartDelay(600).start();
        tableLayout.animate().translationX(s).alpha(1).setDuration(1000).setStartDelay(800).start();
        tableLayout.setTabRippleColor(null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult!=null){
            String content=intentResult.getContents();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate();
            onConnectSocket();
            try {
                eventLoginQr(content);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private Socket socket;

    private void onConnectSocket(){
        {
            try {
                socket=  IO.socket(Constant.socketQr);
                socket.connect();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void eventLoginQr(String hash) throws JSONException {
        QRRawText qrRawText=new QRRawText(hash);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("hashCode",hash);
        socket.emit(Category.EventLoginQr.verifiQR.name,jsonObject);
        socket.on(Category.EventLoginQr.responseLoginQr.name, args -> runOnUiThread(() -> {

            ObjectMapper objectMapper=new ObjectMapper();

            LoginSocketResponse response= null;
            try {
                response = objectMapper.readValue(args[0].toString(), LoginSocketResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if(response.getStatus()==0) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Xác thực thật bại", Toast.LENGTH_LONG).show();
            }
            else {
                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("accessToken", response.getAccessToken()).apply();
                sharedPreferences.edit().putString("refreshToken", response.getRefreshToken()).apply();
                sharedPreferences.edit().putString("userId", response.getUserId().toString()).apply();
                sharedPreferences.edit().putString("role", response.getRole().name()).apply();
                sharedPreferences.edit().putString("expireAccessToken", String.valueOf(response.getExpireAccressToken().getTime())).apply();
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }));
    }
}
