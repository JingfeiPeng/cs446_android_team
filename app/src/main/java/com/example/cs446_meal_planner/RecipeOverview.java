package com.example.cs446_meal_planner;

import android.os.Bundle;

import com.example.cs446_meal_planner.model.Recipe;
import com.example.cs446_meal_planner.model.RecipeAdapter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecipeOverview extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    RecyclerView recyclerRecipes;
    ArrayList<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipe_overview_layout);

        recyclerRecipes = findViewById(R.id.recycler_recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerRecipes.setLayoutManager(layoutManager);
        DBHelper db = DBHelper.getInstance(this);
        recipes = db.getAllRecipes();
        recyclerRecipes.setAdapter(new RecipeAdapter(this,recipes));
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}