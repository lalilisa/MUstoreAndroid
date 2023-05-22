package com.example.mustore;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mustore.model.User;

import java.util.ArrayList;

public class IntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        Handler handler=new Handler();
        handler.postDelayed(() -> {
            Intent intent=new Intent(IntroduceActivity.this,LoginActivity.class);
            startActivity(intent);
        },2000);
    }

    @Override
    public void onBackPressed() {

    }
}