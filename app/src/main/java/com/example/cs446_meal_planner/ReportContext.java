package com.example.cs446_meal_planner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ReportContext {
    private ReportStrategy reportStrategy;

    public ReportContext(ReportStrategy reportStrategy){
        this.reportStrategy = reportStrategy;
    }

    public LineGraphSeries executeStrategy(GraphView graphView){
        return reportStrategy.generateReport(graphView);
    }
}
