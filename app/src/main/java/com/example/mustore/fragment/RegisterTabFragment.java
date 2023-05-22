package com.example.mustore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mustore.R;
import com.example.mustore.api.AuthenApi;
import com.example.mustore.retrofit.request.RegisterRequest;
import com.example.mustore.retrofit.response.UserView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterTabFragment extends Fragment {

    EditText username,password,email,confirmPassword;

    ProgressBar progressBar;
    Button create;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_tab_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        onClick();
    }

    private void init(View view){
        username= view.findViewById(R.id.username);
        password= view.findViewById(R.id.password);
        email= view.findViewById(R.id.email);
        confirmPassword= view.findViewById(R.id.confirm_password);
        create= view.findViewById(R.id.register_btn);
        progressBar=view.findViewById(R.id.process_bar);
    }
    private void onClick(){
        create.setOnClickListener(view -> {
            String userRe=username.getText().toString();
            String passwordRe=password.getText().toString();
            String emailRe=email.getText().toString();
            String confirmPass=confirmPassword.getText().toString();
            if(userRe.isEmpty()||
                    passwordRe.isEmpty()||
                    emailRe.isEmpty()||
                    confirmPass.isEmpty()
            )
                Toast.makeText(getContext(),"Thông tin không được để trống",Toast.LENGTH_SHORT).show();
            if(!passwordRe.equals(confirmPass))
                Toast.makeText(getContext(),"Mật khẩu xác thực không chính xác ",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate();
            AuthenApi.authenApi.reigster(new RegisterRequest(emailRe,userRe,passwordRe)).enqueue(new Callback<UserView>() {
                @Override
                public void onResponse(Call<UserView> call, Response<UserView> response) {
                    if(response.code()==200){
                        Toast.makeText(getContext(),"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        email.setText("");
                        confirmPassword.setText("");
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Toast.makeText(getContext(),"username hoặc email đã tồn tạo",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<UserView> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        });
    }
}
