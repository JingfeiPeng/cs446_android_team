package com.cs446.cs446_meal_planner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

public interface ReportStrategy {
    public LineGraphSeries generateReport(GraphView calorieGraph);
}
