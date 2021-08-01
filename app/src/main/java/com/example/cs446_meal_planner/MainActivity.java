package com.example.cs446_meal_planner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cs446_meal_planner.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.joda.time.DateTime;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static RecipeDBHelper db;
    private static CalendarDBHelper calenderDB;

    public static DateTime reportEndDate = new DateTime(DateTime.now().getYear(),
            DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth(),
            0,0,0);
    public static DateTime reportStartDate = reportEndDate.minusDays(7);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_PERMISSION_STORAGE = 100;
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_PERMISSION_STORAGE);
                    return;
                }
            }
        }

        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = RecipeDBHelper.getInstance(this);
        calenderDB = CalendarDBHelper.getInstance(this);

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

    @Override
    protected void onResume() {
        super.onResume();
        Button picker1 = findViewById(R.id.button_pick_date_1);
        Button picker2 = findViewById(R.id.button_pick_date_2);
        if(picker1 != null){
            picker1.setText(MainActivity.reportStartDate.getMonthOfYear() + "/" + MainActivity.reportStartDate.getDayOfMonth());
        }

        if(picker2 != null){
            picker2.setText(MainActivity.reportEndDate.getMonthOfYear() + "/" + MainActivity.reportEndDate.getDayOfMonth());
        }

    }

    public void viewCalender(View view) {
        Intent moveToCalender = new Intent(getApplicationContext(), CalendarActivity.class);
        startActivity(moveToCalender);
    }

    public void viewRecipeCreation(View v)
    {
        startActivity(new Intent(getApplicationContext(), RecipeCreationActivity.class));
    }

    public void viewReport(View v)
    {
        Intent switchActivityIntent = new Intent(this, ReportActivity.class);
        startActivity(switchActivityIntent);
    }

    public void viewRecipes(View view) {
        Intent intent = new Intent(getApplicationContext(), RecipeListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cameFrom", "home");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getDate1(View view) {
        Intent intent = new Intent(getApplicationContext(), ReportDateActivity.class);
        startActivity(intent);
    }

    public void getDate2(View view) {
        Intent intent = new Intent(getApplicationContext(), ReportDate2Activity.class);
        startActivity(intent);
    }

}