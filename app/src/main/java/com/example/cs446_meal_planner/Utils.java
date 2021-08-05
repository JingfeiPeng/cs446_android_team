package com.example.cs446_meal_planner;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import com.example.cs446_meal_planner.model.MonthDay;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Utils {
    public static Bitmap getImage(Context context, String imageUrl){
        Uri selectedImage = Uri.parse(imageUrl);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
        } catch (IOException e) {

        }
        return bitmap;
    }

    public static LineGraphSeries <DataPoint> generateSeries(Map<MonthDay, Double> list){
        List<DataPoint> datapoints = new ArrayList<>();

        //datapoints.add(new DataPoint(0, 0));

        for(int i = 0; i < ReportActivity.days; i++){
            Date tempDate = MainActivity.reportStartDate.plusDays(i).toDate();

            MonthDay temp = MonthDay.builder()
                    .month(MainActivity.reportStartDate.plusDays(i).getMonthOfYear())
                    .day(MainActivity.reportStartDate.plusDays(i).getDayOfMonth())
                    .build();

            Double calorie = 0.0;

            if(list.containsKey(temp)){
                calorie = list.get(temp);
            }

            datapoints.add(new DataPoint(tempDate, calorie));
        }

        DataPoint [] temp = new DataPoint[datapoints.size()];

        for(int i = 0; i < datapoints.size(); i++){
            temp[i] = datapoints.get(i);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(temp);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        return series;
    }

}

