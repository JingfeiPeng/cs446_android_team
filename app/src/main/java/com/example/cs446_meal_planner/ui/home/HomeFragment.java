package com.example.cs446_meal_planner.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs446_meal_planner.CalendarDBHelper;
import com.example.cs446_meal_planner.MainActivity;
import com.example.cs446_meal_planner.R;
import com.example.cs446_meal_planner.RecipeDBHelper;
import com.example.cs446_meal_planner.RecipeOverviewActivity;
import com.example.cs446_meal_planner.SettingDBHelper;
import com.example.cs446_meal_planner.Utils;
import com.example.cs446_meal_planner.databinding.FragmentHomeBinding;
import com.example.cs446_meal_planner.model.CalendarBooking;
import com.example.cs446_meal_planner.model.CalendarDate;
import com.example.cs446_meal_planner.model.PersonalInfo;
import com.example.cs446_meal_planner.model.Recipe;

import org.joda.time.DateTime;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecipeDBHelper db;
    private CalendarDBHelper calendarDB;
    private SettingDBHelper settingDB;

    private Button picker1;
    private Button picker2;
    private ImageView imageViewNextRecipe;
    private TextView textViewNextCalorie;
    private TextView textViewNextCookingTime;
    private TextView textView_next_meal;
    private Button buttonNextRecipe;
    private ImageView imageViewBreakfast;
    private ImageView imageViewLunch;
    private ImageView imageViewDinner;
    private Recipe recipe;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        calendarDB = CalendarDBHelper.getInstance(root.getContext());
        settingDB = SettingDBHelper.getInstance(root.getContext());

        picker1 = root.findViewById(R.id.button_pick_date_1);
        picker2 = root.findViewById(R.id.button_pick_date_2);
        imageViewNextRecipe = root.findViewById(R.id.imageView_next_recipe);
        textViewNextCalorie = root.findViewById(R.id.textView_meal_calorie);
        textViewNextCookingTime = root.findViewById(R.id.textView_meal_prepTime);
        buttonNextRecipe = root.findViewById(R.id.button_view_recipe);
        imageViewBreakfast = root.findViewById(R.id.imageView_breakfast);
        imageViewLunch = root.findViewById(R.id.imageView_lunch);
        imageViewDinner = root.findViewById(R.id.imageView_dinner);
        textView_next_meal = root.findViewById(R.id.textView_next_meal);
        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        initializeHome();

        buttonNextRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), RecipeOverviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("instructions",recipe.getInstruction());
                bundle.putString("ingredients",recipe.getIngredients());
                bundle.putString("recipeName",recipe.getName());
                bundle.putInt("id",recipe.getId());
                bundle.putDouble("calories",recipe.getCalorie());
                bundle.putDouble("cookingTime", recipe.getCookingTime());
                bundle.putString("imageUrl", recipe.getImageUrl());
                intent.putExtras(bundle);
                root.getContext().startActivity(intent);
            }
        });
    }


    private void initializeHome() {
        //set dates
        picker1.setText(MainActivity.reportStartDate.getMonthOfYear() + "/" + MainActivity.reportStartDate.getDayOfMonth());
        picker2.setText(MainActivity.reportEndDate.getMonthOfYear() + "/" + MainActivity.reportEndDate.getDayOfMonth());

        //set next recipe
        CalendarBooking nextBooking = calendarDB.getNextMealBooking();


        if (nextBooking != null) {
            recipe = nextBooking.getBookedRecipe();
            if (recipe.getImageUrl() != null && !recipe.getImageUrl().equals("")) {
                imageViewNextRecipe.setImageURI(Uri.parse(recipe.getImageUrl()));
            }
            textViewNextCalorie.setText("Calorie: " + recipe.getCalorie() + "kCal");
            textViewNextCookingTime.setText("Cooking Time: " + (int) Math.round(recipe.getCookingTime()) + "min");
            textView_next_meal.setText("Next Meal: "+recipe.getName());

            if (nextBooking.getMealType().equals("breakfast")) {
                buttonNextRecipe.setText("VIEW BREAKFAST RECIPE");
            } else if (nextBooking.getMealType().equals("lunch")) {
                buttonNextRecipe.setText("VIEW LUNCH RECIPE");
            } else if (nextBooking.getMealType().equals("dinner")) {
                buttonNextRecipe.setText("VIEW DINNER RECIPE");
            }

        }


        TextView calorie_1 = root.findViewById(R.id.textView_calorie_1);
        TextView calorie_2 = root.findViewById(R.id.textView_calorie_2);
        ProgressBar progressBar = root.findViewById(R.id.progressBar);

        if(calorie_1 == null){
            return;
        }

        PersonalInfo info = settingDB.get_personal_info();

        double today = getDayCalorie(DateTime.now());

        calorie_1.setText("Today's Calorie: " + today + "kCal");
        int percent = 0;

        if(info.getGoal() != 0){
            percent =  (int)((today/info.getGoal().doubleValue()) * 100);
        }
        float floatPercent = (float) ((today/info.getGoal().doubleValue()) * 100);
        calorie_2.setText(percent + "% of your daily intake");
        progressBar.setMax(info.getGoal());
        progressBar.setProgress((int) today);
        int colour = R.color.red;
        if (floatPercent < 80) {
            colour = R.color.green;
        } else if (floatPercent <= 100) {
            colour = R.color.orange;
        }
        progressBar.getProgressDrawable().setColorFilter(
                ContextCompat.getColor(getContext(), colour),
                android.graphics.PorterDuff.Mode.SRC_IN
        );


        //set plan
        CalendarDate cur = new CalendarDate(DateTime.now());
        this.initializeSpecificMeal(imageViewBreakfast, cur, "breakfast");
        this.initializeSpecificMeal(imageViewLunch, cur, "lunch");
        this.initializeSpecificMeal(imageViewDinner, cur, "dinner");
    }

    private void initializeSpecificMeal(ImageView mealImage, CalendarDate today, String mealType) {
        CalendarBooking curBooking = calendarDB.getMealBookingOnDate(today, mealType);

        if(curBooking != null && curBooking.getBookedRecipe().getImageUrl() != null && !curBooking.getBookedRecipe().getImageUrl().equals("")){
            mealImage.setImageURI(Uri.parse(curBooking.getBookedRecipe().getImageUrl()));
        }
    }

    private double getDayCalorie(DateTime cur) {
        double totalCalorie = 0.0;

        CalendarBooking breakfast = calendarDB.getMealBookingOnDate(new CalendarDate(cur), "breakfast");
        CalendarBooking lunch = calendarDB.getMealBookingOnDate(new CalendarDate(cur), "lunch");
        CalendarBooking dinner = calendarDB.getMealBookingOnDate(new CalendarDate(cur), "dinner");

        if (breakfast != null) {
            Double calorie = breakfast.getBookedRecipe().getCalorie();

            if (calorie != null) {
                totalCalorie += calorie;
            }
        }

        if (lunch != null) {
            Double calorie = lunch.getBookedRecipe().getCalorie();

            if (calorie != null) {
                totalCalorie += calorie;
            }
        }

        if (dinner != null) {
            Double calorie = dinner.getBookedRecipe().getCalorie();

            if (calorie != null) {
                totalCalorie += calorie;
            }
        }

        return totalCalorie;
    }
}