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

import com.example.cs446_meal_planner.databinding.ActivitySubmitRecipeBinding;

import java.util.ArrayList;

public class RecipeOverview extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySubmitRecipeBinding binding;

    RecyclerView recyclerRecipes;
    ArrayList<Recipe> recipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySubmitRecipeBinding.inflate(getLayoutInflater());
        setContentView(R.layout.recipe_overview_layout);

        recyclerRecipes = findViewById(R.id.recycler_recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerRecipes.setLayoutManager(layoutManager);
        DBHelper db = new DBHelper(this);
        recipes = db.getAllRecipes();
        recyclerRecipes.setAdapter(new RecipeAdapter(this,recipes));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_submit_recipe);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}