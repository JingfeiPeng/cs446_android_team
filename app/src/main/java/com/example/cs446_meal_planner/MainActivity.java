package com.example.cs446_meal_planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cs446_meal_planner.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static RecipeDBHelper db;
    private static CalenderDBHelper calenderDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = RecipeDBHelper.getInstance(this);
        calenderDB = CalenderDBHelper.getInstance(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recipe, R.id.navigation_setting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void viewCalender(View view) {
        Intent moveToCalender = new Intent(getApplicationContext(), CalenderActivity.class);
        startActivity(moveToCalender);
    }
    public void viewRecipeCreation(View v)
    {
        startActivity(new Intent(getApplicationContext(), RecipeCreation.class));
    }

    public void viewRecipes(View view) {
        Intent intent = new Intent(getApplicationContext(),RecipeOverview.class);
        Bundle bundle = new Bundle();
        bundle.putString("cameFrom", "home");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}