package com.example.mustore.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mustore.fragment.LoginTabFragment;
import com.example.mustore.fragment.RegisterTabFragment;

public class LoginRegisterFragmentAdapter extends FragmentPagerAdapter {

    Context context;
    Integer totalTab;
    public LoginRegisterFragmentAdapter(@NonNull FragmentManager fragmentManager, Context context, Integer totalTab) {
        super(fragmentManager);
        this.context=context;
        this.totalTab=totalTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        System.out.println("---------"+position);
        switch (position){
            case 0:
                System.out.println("TAB LOGIN");
                return new LoginTabFragment();

            case 1:
                System.out.println("TAB REGISTER");
                return new RegisterTabFragment();

            default:
                return new LoginTabFragment();
        }

    }

    @Override
    public int getCount() {
        return totalTab;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position){
            case 0:
                title = "Login";
                return title;
            case 1:
                title = "Resigter";
                return title;

            default:
                return title;
        }

    }
}
