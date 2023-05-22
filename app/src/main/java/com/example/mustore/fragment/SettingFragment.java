package com.example.mustore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;

import com.example.mustore.ChangePasswordActivity;
import com.example.mustore.ChartActivity;
import com.example.mustore.CheckoutActivity;
import com.example.mustore.LoginActivity;
import com.example.mustore.OptionChartActivity;
import com.example.mustore.OrderActivity;
import com.example.mustore.ProfileActivity;
import com.example.mustore.R;
import com.example.mustore.UserChatActivity;
import com.example.mustore.common.Utils;
import com.example.mustore.retrofit.request.ChangePassword;

public class SettingFragment extends Fragment {

    private View view;

    private TextView profile,changepass,order,statics,helper,logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_setting,container,false);
//        Window window=requireActivity().getWindow();
//        new WindowInsetsControllerCompat(window,
//                window.getDecorView()).setAppearanceLightStatusBars(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        onClickEvent();

    }

    private void init(){
        profile=view.findViewById(R.id.detail_profile);
        changepass=view.findViewById(R.id.change_password);
        order=view.findViewById(R.id.orders);
        statics=view.findViewById(R.id.cart_setting);
        helper=view.findViewById(R.id.chat_setting);
        logout=view.findViewById(R.id.log_out);
        if(Utils.getRole(getActivity()).equals("USER"))
            statics.setVisibility(View.GONE);
        statics.setText("Thống kê");
    }
    private void onClickEvent(){
        profile.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });
        changepass.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        statics.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), OptionChartActivity.class);
            startActivity(intent);
        });
        order.setOnClickListener(view -> {
                Intent intent=new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
        });
        logout.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
        helper.setOnClickListener(view -> {
            Intent intent=new Intent(getActivity(), UserChatActivity.class);
            startActivity(intent);
        });
    }
}
