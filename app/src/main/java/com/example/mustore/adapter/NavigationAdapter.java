package com.example.mustore.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mustore.fragment.CartFragment;
import com.example.mustore.fragment.HomeFragment;
import com.example.mustore.fragment.NotificationFragment;
import com.example.mustore.fragment.SettingFragment;

public class NavigationAdapter extends FragmentStatePagerAdapter {
    private final int size;

    public NavigationAdapter(@NonNull FragmentManager fm, int behavior,int size) {
        super(fm, behavior);
        this.size=size;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new CartFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new SettingFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return size;
    }
}
