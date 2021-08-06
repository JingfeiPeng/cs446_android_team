package com.example.cs446_meal_planner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs446_meal_planner.model.CalendarBooking;
import com.example.cs446_meal_planner.model.CalendarDate;
import com.example.cs446_meal_planner.model.MonthDay;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ReportActivity extends CalorieReportActivity {

    private TextView textViewRecipe;
    private TextView textViewIngredient;
    private TextView textViewCalorie;
    private Button buttonViewCalorie;
    private static CalendarDBHelper calenderDB;

    public static Map<MonthDay, Double> breakfastList;
    public static Map<MonthDay, Double> lunchList;
    public static Map<MonthDay, Double> dinnerList;
    public static Map<MonthDay, Double> totalList;
    private DateTime startDate;
    public static int days;



    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_report);

        calenderDB = CalendarDBHelper.getInstance(this);
        textViewRecipe = findViewById(R.id.textView_number_of_recipes);
        textViewIngredient = findViewById(R.id.textView_favoured_ingredient);
        textViewCalorie = findViewById(R.id.textView_consumed_calories);

        breakfastList = new LinkedHashMap<>();
        lunchList = new LinkedHashMap<>();
        dinnerList = new LinkedHashMap<>();
        totalList = new LinkedHashMap<>();
        startDate = new DateTime(MainActivity.reportStartDate);

        days = (int) ChronoUnit.DAYS.between(MainActivity.reportStartDate.toDate().toInstant(), MainActivity.reportEndDate.toDate().toInstant())+1;

        setAllTextView();

        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAllTextView(){
        List <String> ingredientList = new ArrayList<>();
        Double breakfastAverage = 0.0;
        Double lunchAverage = 0.0;
        Double dinnerAverage = 0.0;
        int countRecipe = 0;

        int total = days;


        for(int i = 0; i < total ; i++) {
            DateTime cur = startDate.plusDays(i);
            Double totalCalorie = 0.0;

            CalendarBooking breakfast = calenderDB.getMealBookingOnDate(new CalendarDate(cur), "breakfast");
            CalendarBooking lunch = calenderDB.getMealBookingOnDate(new CalendarDate(cur), "lunch");
            CalendarBooking dinner = calenderDB.getMealBookingOnDate(new CalendarDate(cur), "dinner");

            MonthDay temp = MonthDay.builder()
                    .month(cur.getMonthOfYear())
                    .day(cur.getDayOfMonth())
                    .build();

            //average calorie
            if(breakfast != null) {
                Double calorie = breakfast.getBookedRecipe().getCalorie();

                if(calorie != null){
                    breakfastAverage += calorie;
                }
                else{
                    calorie = 0.0;
                }

                ++countRecipe;
                ingredientList.addAll(getIngredients(breakfast.getBookedRecipe().getIngredients()));
                breakfastList.put(temp, calorie);
                totalCalorie += calorie;
            }

            if(lunch != null) {
                Double calorie = lunch.getBookedRecipe().getCalorie();

                if(calorie != null){
                    lunchAverage += calorie;
                }
                else{
                    calorie = 0.0;
                }

                ++countRecipe;
                ingredientList.addAll(getIngredients(lunch.getBookedRecipe().getIngredients()));
                lunchList.put(temp, calorie);
                totalCalorie += calorie;
            }

            if(dinner != null) {
                Double calorie = dinner.getBookedRecipe().getCalorie();

                if(calorie != null){
                    dinnerAverage += calorie;
                }
                else{
                    calorie = 0.0;
                }

                ++countRecipe;
                ingredientList.addAll(getIngredients(dinner.getBookedRecipe().getIngredients()));
                dinnerList.put(temp, calorie);
                totalCalorie += calorie;
            }

            totalList.put(temp, totalCalorie);
        }

        breakfastAverage /= total;
        lunchAverage /= total;
        dinnerAverage /= total;

        setTextViewRecipe(countRecipe);
        setTextViewIngredient(ingredientList);
        setTextViewCalorie(breakfastAverage, lunchAverage, dinnerAverage);


    }

    private void setTextViewRecipe(int num){
        textViewRecipe.setText("You Have Cooked " + num + " Recipes This Week");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTextViewIngredient(List<String> ingredients){
        List<String> result = getMostFrequentIngredients(ingredients);

        if(result.size() == 0){
            textViewIngredient.setText("You Do Not Have a Favourite Ingredient");
        }
        else if(result.size() == 1){
            textViewIngredient.setText("You Most Favoured Ingredient is " + result.get(0));
        }
        else{
            String temp = "You Most Favoured Ingredients are ";
            for(String ingredient : result){
                temp += ingredient;
                temp += ", ";
            }
            temp = temp.substring(0, temp.length()-2);
            textViewIngredient.setText(temp);
        }
    }

    private void setTextViewCalorie(Double breakfast, Double lunch, Double dinner){

        DecimalFormat df = new DecimalFormat("####0.00");
        Double totalAverage = (breakfast + lunch + dinner)/3;
        textViewCalorie.setText("You consumed " + df.format(totalAverage) + " calories on average this week. Keep it up!");



    }

    private List<String> getIngredients(String ingredients) {
        String[] ingredientGramList = ingredients.split("#");
        List<String> result = new ArrayList<>();

        for (int i = 0; i < ingredientGramList.length; i++) {
            // ingredient at index 0 and weight at index 1
            String[] ingredientGram = ingredientGramList[i].split("%");
            String ingredientName = ingredientGram[0];
            result.add(ingredientName);
        }
        return result;
    }


//    private DateTime startDateOfWeek(Date date){
//        DateTime dateTime = new DateTime(date);
//        return dateTime.dayOfWeek().withMinimumValue();
//    }
//
//    private DateTime endDateOfWeek(Date date){
//        DateTime dateTime = new DateTime(date);
//        return dateTime.dayOfWeek().withMaximumValue();
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<String> getMostFrequentIngredients(List<String> ingredients){
        Map<String, Long> occurrences =
                ingredients.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        List<String> result = new ArrayList<>();

        Long maxOccurence = Long.valueOf(0);

        for(String ingredient : occurrences.keySet()){
            if(occurrences.get(ingredient) > maxOccurence){
                result.clear();
                result.add(ingredient);
                maxOccurence = occurrences.get(ingredient);
            }
            else if(occurrences.get(ingredient) == maxOccurence){
                result.add(ingredient);
            }
        }
        return result;
    }
}

