package com.example.cs446_meal_planner;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs446_meal_planner.model.IngredientCaloriesCalculator;
import com.example.cs446_meal_planner.model.Recipe;
import com.example.cs446_meal_planner.model.CalorieSuggestion;


import java.math.RoundingMode;
import java.text.DecimalFormat;

public class SettingActivity extends AppCompatActivity {


    private static SettingDBHelper db;

    protected RadioGroup gender_result;
    protected RadioButton gender_male;
    protected RadioButton gender_female;
    protected Button personalinfo_save;
    protected Button goal_save;
    protected EditText ageEdit;
    protected EditText goalEdit;

    private TextView textview_Suggestion;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);


        gender_result = (RadioGroup) findViewById(R.id.radiogroup);
        gender_male = (RadioButton) findViewById(R.id.radiobutton1);
        gender_female = (RadioButton) findViewById(R.id.radiobutton2);

        personalinfo_save = findViewById(R.id.button_submit_PersonalInfo);
        goal_save = findViewById(R.id.button_submit_Goal);
        ageEdit = findViewById(R.id.edit_age);
        goalEdit = findViewById(R.id.edit_goal);

        textview_Suggestion =  findViewById(R.id.textView_suggestion);

        db = SettingDBHelper.getInstance(this);

        gender_result.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == gender_male.getId()) {
                    db.updateGender("male");
                } else {
                    db.updateGender("female");
                }
            }
        });

        personalinfo_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recalculate suggestion&update suggestion
            }
        });

        goal_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.updateGoal(200); //update with the number enter
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTextView() {
        setTextViewSuggestion(2000); // do calorise suggestion
    }

    private void setTextViewSuggestion(int num){
        textview_Suggestion.setText("According to your gender and age, the recommended daily calorie intake is" + num + "calories");
    }
}
