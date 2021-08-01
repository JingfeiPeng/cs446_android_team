package com.example.cs446_meal_planner;

import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

public interface ReportStrategy {
    public LineGraphSeries generateReport(GraphView calorieGraph);
}
