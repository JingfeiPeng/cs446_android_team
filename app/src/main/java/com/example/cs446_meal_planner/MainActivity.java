package com.example.cs446_meal_planner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cs446_meal_planner.databinding.ActivityMainBinding;

import com.example.cs446_meal_planner.model.PersonalInfo;

import com.example.cs446_meal_planner.model.CalendarBooking;
import com.example.cs446_meal_planner.model.CalendarDate;
import com.example.cs446_meal_planner.model.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.joda.time.DateTime;

public class MainActivity extends ObserverActivity {

    private ActivityMainBinding binding;
    private static RecipeDBHelper db;

    private static CalendarDBHelper calenderDB;
    private static SettingDBHelper settingDB;
    private static CalendarDBHelper calendarDBHelper;


    public static DateTime reportEndDate = new DateTime(DateTime.now().getYear(),
            DateTime.now().getMonthOfYear(), DateTime.now().getDayOfMonth(),
            0,0,0);
    public static DateTime reportStartDate = reportEndDate.minusDays(7);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPermission();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = RecipeDBHelper.getInstance(this);
        calenderDB = CalendarDBHelper.getInstance(this);
        settingDB = SettingDBHelper.getInstance(this);
        // observer
        settingDB.attachActivity(this);
        calendarDBHelper = CalendarDBHelper.getInstance(this);


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

    private void getPermission(){
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
    }
    @Override
    protected void onResume(){
        super.onResume();
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
        Bundle bundle = new Bundle();
        bundle.putString("startDate", "StartDate");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getDate2(View view) {
        Intent intent = new Intent(getApplicationContext(), ReportDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("endDate", "endDate");
        intent.putExtras(bundle);
        startActivity(intent);
    }


}