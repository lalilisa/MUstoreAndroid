package com.example.mustore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mustore.R;
import com.example.mustore.retrofit.response.Category;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    Context context;

    List<Category> categories;
    LayoutInflater inflter;

    public SpinnerAdapter(Context applicationContext,  List<Category> categories) {
        this.context = applicationContext;
        this.categories = categories;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_item, null);
        ImageView icon = view.findViewById(R.id.imageView);
        TextView names =  view.findViewById(R.id.textView);
        icon.setImageResource(R.drawable.mu_ball);
        names.setText(categories.get(i).getName());
        return view;
    }
}
