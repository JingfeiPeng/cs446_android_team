package com.example.cs446_meal_planner;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.cs446_meal_planner.model.MonthDay;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Utils {
    public static Bitmap getImage(Context context, String imageUrl){
        Uri selectedImage = Uri.parse(imageUrl);
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return BitmapFactory.decodeFile(picturePath);
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

