package com.example.mustore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mustore.adapter.NavigationAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView  navigation;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        onSelectedViewPager();
        onSelectedNavigation();
        onClickEvent();
        onSwichFragment();
    }

    private void init(){
        navigation=findViewById(R.id.home_bottom_nav);
        viewPager=findViewById(R.id.home_viewpager);
    }

    private void onSwichFragment(){
        if(getIntent()!=null) {
            String value = getIntent().getStringExtra("REDIRECT");
            if(value==null)
                return;
            switch (value) {
                case "CART": {
                    navigation.getMenu().findItem(R.id.cart_item).setChecked(true);
                    viewPager.setCurrentItem(1);
                    break;
                }
                case "NOTIFICATIONS": {
                    navigation.getMenu().findItem(R.id.home_item).setChecked(true);
                    viewPager.setCurrentItem(0);
                    break;
                }
                case "HOME": {
                    navigation.getMenu().findItem(R.id.notification_item).setChecked(true);
                    viewPager.setCurrentItem(2);
                    break;
                }
            }
        }
    }
    @SuppressLint("NonConstantResourceId")
    private void onSelectedNavigation(){
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home_item: {
                    viewPager.setCurrentItem(0);
                    break;
                }
                case R.id.cart_item: {
                    viewPager.setCurrentItem(1);
                    break;
                }
                case R.id.notification_item: {
                    viewPager.setCurrentItem(2);
                    break;
                }
                case R.id.setting_item: {
                    viewPager.setCurrentItem(3);
                    break;
                }
            }
            return true;
        });
    }
    private void onSelectedViewPager(){
        NavigationAdapter navigationAdapter=new NavigationAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,4);
        viewPager.setAdapter(navigationAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: {
                        navigation.getMenu().findItem(R.id.home_item).setChecked(true);
                        break;
                    }
                    case 1: {
                        navigation.getMenu().findItem(R.id.cart_item).setChecked(true);
                        break;
                    }
                    case 2: {
                        navigation.getMenu().findItem(R.id.notification_item).setChecked(true);
                        break;
                    }
                    case 3: {
                        navigation.getMenu().findItem(R.id.setting_item).setChecked(true);
                        break;
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void onClickEvent(){

    }

    @Override
    public void onBackPressed() {

    }
}