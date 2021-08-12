package com.cs446.cs446_meal_planner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ReportLunch implements ReportStrategy{
    @Override
    public LineGraphSeries generateReport(GraphView calorieGraph) {
        LineGraphSeries<DataPoint> series = Utils.generateSeries(ReportActivity.lunchList);
        return series;
    }
}
