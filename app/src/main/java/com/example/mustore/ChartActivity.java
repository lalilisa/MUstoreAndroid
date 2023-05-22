package com.example.mustore;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mustore.api.ChartApi;
import com.example.mustore.model.OptionChart;
import com.example.mustore.retrofit.response.DataCategoryChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {

    BarChart chart;

    OptionChart optionChart;

    List<BarEntry> barEntries=new ArrayList<>();
    List<BarEntry> prices=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_bard);
        init();
    }

    private void  init(){
        chart=findViewById(R.id.bard);
        optionChart= (OptionChart) getIntent().getSerializableExtra("option");
        fetchChart();
    }

    private void fetchChart(){
        ChartApi.chartApi.getCategoryChart(5).enqueue(new Callback<List<DataCategoryChart>>() {
            @Override
            public void onResponse(Call<List<DataCategoryChart>> call, Response<List<DataCategoryChart>> response) {

                List<String> lables=new ArrayList<>();
                AtomicInteger i= new AtomicInteger(0);
                response.body().forEach(dataCategoryChart -> {
                    barEntries.add(new BarEntry((float) i.get(),dataCategoryChart.getTotalQuantity()));
                    prices.add(new BarEntry((float) i.get(),dataCategoryChart.getTotalPrice().floatValue()));
                    i.getAndIncrement();
                    lables.add(dataCategoryChart.getCategoryName());
                });
                Toast.makeText(ChartActivity.this,lables.size()+"",Toast.LENGTH_LONG).show();
                if(optionChart.getType().equals("Tiền bán"))
                    create_graph(lables,prices);
                else
                 create_graph(lables,barEntries);
//                chart.setDrawBarShadow(false);
//                chart.setDrawValueAboveBar(true);
//                chart.getDescription().setEnabled(false);
//                chart.setPinchZoom(false);
//                chart.setDrawGridBackground(false);
//
//                XAxis xAxis = chart.getXAxis();
//                xAxis.setGranularity(1f);
//                xAxis.setGranularityEnabled(true);
//                xAxis.setCenterAxisLabels(true);
//                xAxis.setDrawGridLines(true);
//                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                xAxis.setValueFormatter(new IndexAxisValueFormatter(lables));
//                BarDataSet barDataSet=new BarDataSet(barEntries,"sssss");
//                barDataSet.setValueTextSize(15f);
//                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//                barDataSet.setValueTextColor(Color.BLACK);
//                BarData barData=new BarData(barDataSet);
//                chart.setFitBars(true);
//                chart.setData(barData);
            }

            @Override
            public void onFailure(Call<List<DataCategoryChart>> call, Throwable t) {

            }
        });
    }

    public void create_graph(List<String> graph_label, List<BarEntry> barEntries) {

        try {
            chart.setDrawBarShadow(false);
            chart.setDrawValueAboveBar(true);
            chart.getDescription().setEnabled(false);
            chart.setPinchZoom(false);

            chart.setDrawGridBackground(false);


            YAxis yAxis = chart.getAxisLeft();
            yAxis.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return String.valueOf((int) value);
                }
            });

            yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


            yAxis.setGranularity(1f);
            yAxis.setGranularityEnabled(true);
            yAxis.setCenterAxisLabels(true);
            chart.getAxisLeft().setEnabled(true);


            XAxis xAxis = chart.getXAxis();
            xAxis.setGranularity(0.4f);
            xAxis.setGranularityEnabled(true);
//            xAxis.setCenterAxisLabels(true);
            xAxis.setDrawGridLines(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAvoidFirstLastClipping(false);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(graph_label));
            List<BarEntry> yVals1 = new ArrayList<BarEntry>();
            xAxis.setLabelCount(graph_label.size());


            BarDataSet set1;

            if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
                set1.setValues(yVals1);
                chart.getData().notifyDataChanged();
                chart.notifyDataSetChanged();
            } else {
                // create 2 datasets with different types
                set1 = new BarDataSet(barEntries, "SCORE");
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                set1.setValueTextColor(Color.BLACK);


                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                chart.setData(data);


            }

            chart.setFitBars(true);

            Legend l = chart.getLegend();
            l.setFormSize(12f); // set the size of the legend forms/shapes
            l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used


            l.setTextSize(10f);
            l.setTextColor(Color.BLACK);
//            l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
//            l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

            chart.invalidate();

            chart.animateY(2000);

        } catch (Exception ignored) {
        }
    }
}