package com.example.mustore;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mustore.api.ChartApi;
import com.example.mustore.model.OptionChart;
import com.example.mustore.retrofit.response.DataCategoryChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartPieActivity extends AppCompatActivity {

    PieChart chart;
    OptionChart  optionChart;
    List<PieEntry> pieChartsP=new ArrayList<>();
    List<PieEntry> pieChartsQ=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_pie);
        init();
    }

    private void init(){
        chart=findViewById(R.id.pie);
        optionChart= (OptionChart) getIntent().getSerializableExtra("option");
        fetchChart();
    }
    private void fetchChart(){
        ChartApi.chartApi.getCategoryChart(optionChart.getMonth()).enqueue(new Callback<List<DataCategoryChart>>() {
            @Override
            public void onResponse(Call<List<DataCategoryChart>> call, Response<List<DataCategoryChart>> response) {

                List<String> lables=new ArrayList<>();
                AtomicInteger i= new AtomicInteger(0);
                response.body().forEach(dataCategoryChart -> {

                    pieChartsP.add(new PieEntry(dataCategoryChart.getPercentPrice().floatValue(),dataCategoryChart.getCategoryName()));
                    pieChartsQ.add(new PieEntry(dataCategoryChart.getPercentQuantity().intValue(),dataCategoryChart.getCategoryName()));

                    lables.add(dataCategoryChart.getCategoryName());
                });
                PieDataSet pieDataSet;
                if(optionChart.getType().equals("Tiền bán")){
                     pieDataSet=new PieDataSet(pieChartsP,"Price");
                    pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                }

                else
                {
                    pieDataSet =new PieDataSet(pieChartsQ,"Số lượng");
                    pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);
                    pieDataSet.setValueTextSize(16f);
                }
                PieData pieData=new PieData(pieDataSet);
                chart.setData(pieData);
                chart.animateY(1500);
            }

            @Override
            public void onFailure(Call<List<DataCategoryChart>> call, Throwable t) {

            }
        });
    }
}
