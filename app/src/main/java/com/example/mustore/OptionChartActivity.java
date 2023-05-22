package com.example.mustore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mustore.model.OptionChart;

import java.util.ArrayList;
import java.util.List;

public class OptionChartActivity extends AppCompatActivity {

    Spinner month;
    Spinner year;
    Spinner type;
    Spinner typeChart;
    OptionChart optionChart=new OptionChart();
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_static);
        init();
        onClick();
    }

    private void init(){
        month=findViewById(R.id.month);
        year=findViewById(R.id.year);
        type=findViewById(R.id.type);
        typeChart=findViewById(R.id.chart_type);
        button=findViewById(R.id.draw);
        List<String> monthL=new ArrayList<>();
        List<String> yearsL=new ArrayList<>();
        monthL.add("1");
        monthL.add("2");
        monthL.add("3");
        monthL.add("4");
        monthL.add("5");
        monthL.add("6");
        monthL.add("7");
        monthL.add("8");
        monthL.add("9");
        monthL.add("10");
        monthL.add("11");
        monthL.add("12");
        month.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_sort,monthL));
        yearsL.add("2023");
        yearsL.add("2022");
        yearsL.add("2021");
        yearsL.add("2020");
        yearsL.add("2019");
        yearsL.add("2018");
        year.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_sort,yearsL));
        List<String> types=new ArrayList<>();
        types.add("Số lượng bán");
        types.add("Tiền bán");
        List<String> typeCharts=new ArrayList<>();
        typeCharts.add("Cột");
        typeCharts.add("Phần trăm");
        type.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_sort,types));
        typeChart.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_sort,typeCharts));

    }

    private void onClick(){
        button.setOnClickListener(view -> {
            int m=Integer.parseInt(month.getSelectedItem().toString());
            int y=Integer.parseInt(year.getSelectedItem().toString());
            String typeS=type.getSelectedItem().toString();
            String typeCharts=typeChart.getSelectedItem().toString();
            optionChart=new OptionChart(m,y,typeS,typeCharts);
            Intent intent;
            if(optionChart.getTypeChart().equals("Cột"))
                intent =new Intent(OptionChartActivity.this,ChartActivity.class);
            else
                intent =new Intent(OptionChartActivity.this,ChartPieActivity.class);
            intent.putExtra("option",optionChart);
            startActivity(intent);
        });
    }
}
