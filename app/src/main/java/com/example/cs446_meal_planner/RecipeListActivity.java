package com.example.cs446_meal_planner;

import android.os.Bundle;
import android.util.Log;

import com.example.cs446_meal_planner.model.CalenderBookingData;
import com.example.cs446_meal_planner.model.CalenderDate;
import com.example.cs446_meal_planner.model.Recipe;
import com.example.cs446_meal_planner.model.RecipeAdapter;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecipeListActivity extends ObserverActivity {

    private AppBarConfiguration appBarConfiguration;

    RecyclerView recyclerRecipes;
    ArrayList<Recipe> recipes = new ArrayList<>();
    RecipeDBHelper db;
    private boolean cameFromCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_overview_layout);

        db = RecipeDBHelper.getInstance(this);
        db.attachActivity(this);

        // decide if came here from calender or recipe tab
        cameFromCalender = false;
        if (getIntent().getExtras() != null) {
            String cameFrom = getIntent().getExtras().getString("cameFrom");
            cameFromCalender = cameFrom.equals("calender");
        }

        recyclerRecipes = findViewById(R.id.recycler_recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerRecipes.setLayoutManager(layoutManager);
        update();
    }

    public void update() {
        recipes = db.getAllRecipes();
        if (cameFromCalender) {
            CalenderDate assignedDate = new CalenderDate(getIntent().getExtras().getInt("date"));
            String mealType = getIntent().getExtras().getString("mealType");
            CalenderBookingData bookingdata = new CalenderBookingData(assignedDate, mealType);
            recyclerRecipes.setAdapter(new RecipeAdapter(this,recipes, bookingdata));
        } else {
            recyclerRecipes.setAdapter(new RecipeAdapter(this,recipes, null));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public void onDestroy() {
        db.detchActivity(this);
        super.onDestroy();
    }
}