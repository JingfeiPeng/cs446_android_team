package com.example.cs446_meal_planner.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs446_meal_planner.CalendarDBHelper;
import com.example.cs446_meal_planner.MainActivity;
import com.example.cs446_meal_planner.R;
import com.example.cs446_meal_planner.RecipeDBHelper;
import com.example.cs446_meal_planner.RecipeOverviewActivity;
import com.example.cs446_meal_planner.Utils;
import com.example.cs446_meal_planner.databinding.FragmentHomeBinding;
import com.example.cs446_meal_planner.model.CalendarBooking;
import com.example.cs446_meal_planner.model.CalendarDate;
import com.example.cs446_meal_planner.model.Recipe;

import org.joda.time.DateTime;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecipeDBHelper db;
    private CalendarDBHelper calendarDBHelper;

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

        calendarDBHelper = CalendarDBHelper.getInstance(root.getContext());

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
        CalendarBooking nextBooking = calendarDBHelper.getNextMealBooking();


        if (nextBooking != null) {
            recipe = nextBooking.getBookedRecipe();
            if (recipe.getImageUrl() != null) {
                imageViewNextRecipe.setImageBitmap(Utils.getImage(root.getContext(),
                        recipe.getImageUrl()));
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


        //set plan
        DateTime cur = DateTime.now();
        CalendarBooking curBooking = calendarDBHelper.getMealBookingOnDate(new CalendarDate(cur),
                "breakfast");

        if(curBooking != null){
            imageViewBreakfast.setImageBitmap(Utils.getImage(root.getContext(), curBooking.getBookedRecipe().getImageUrl()));
        }

        curBooking = calendarDBHelper.getMealBookingOnDate(new CalendarDate(cur),
                "lunch");

        if(curBooking != null && curBooking.getBookedRecipe().getImageUrl() != null){
            imageViewLunch.setImageBitmap(Utils.getImage(this.root.getContext(), curBooking.getBookedRecipe().getImageUrl()));
        }


        curBooking = calendarDBHelper.getMealBookingOnDate(new CalendarDate(cur),
                "dinner");

        if(curBooking != null){
            imageViewDinner.setImageBitmap(Utils.getImage(this.root.getContext(), curBooking.getBookedRecipe().getImageUrl()));
        }
    }
}