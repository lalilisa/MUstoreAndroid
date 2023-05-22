package com.example.mustore.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public static String getAccessToken(Activity activity){
        SharedPreferences sharedPref = activity.getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
        return sharedPref.getString("accessToken","");
    }

    public static String createBearerToken(String token){
        return String.format("Bearer %s",token);
    }
    public static Long getUserId(Activity activity){
        SharedPreferences sharedPref = activity.getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
        return  Long.parseLong(sharedPref.getString("userId","-1L")) ;
    }

    public static String getRole(Activity activity){
        SharedPreferences sharedPref = activity.getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
        return sharedPref.getString("role","");
    }
}
