package com.example.cs446_meal_planner.ui.setting;

import android.app.Person;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs446_meal_planner.MainActivity;
import com.example.cs446_meal_planner.R;
import com.example.cs446_meal_planner.SettingDBHelper;
import com.example.cs446_meal_planner.databinding.FragmentHomeBinding;

import com.example.cs446_meal_planner.databinding.FragmentSettingBinding;
import com.example.cs446_meal_planner.model.CalorieSuggestion;
import com.example.cs446_meal_planner.model.PersonalInfo;

public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;
    private FragmentSettingBinding binding;

    private Integer age;
    private String gender;
    private Integer dailyCaloriesGoal;

    protected RadioGroup gender_result;
    protected RadioButton gender_male;
    protected RadioButton gender_female;
    private SettingDBHelper settingDB;
    protected EditText ageEdit;
    protected EditText goalEdit;

    private CalorieSuggestion calorieCal = new CalorieSuggestion();

    private TextView textview_Suggestion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                new ViewModelProvider(this).get(SettingViewModel.class);

        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        settingDB = SettingDBHelper.getInstance(getContext());

        gender_result = getView().findViewById(R.id.radiogroup);
        gender_male = getView().findViewById(R.id.radiobutton1);
        gender_female = getView().findViewById(R.id.radiobutton2);

        ageEdit = getView().findViewById(R.id.edit_age);
        ageEdit.addTextChangedListener(watchAgeChange);
        goalEdit = getView().findViewById(R.id.edit_goal);
        goalEdit.addTextChangedListener(watchGoalChange);

        textview_Suggestion =  getView().findViewById(R.id.textView_suggestion);

        // initialize
        PersonalInfo personalInfo = settingDB.get_personal_info();
        age = personalInfo.getAge();
        gender = personalInfo.getGender();
        dailyCaloriesGoal = personalInfo.getGoal();
        if (gender.equals("male")) {
            gender_male.setChecked(true);
        } else {
            gender_female.setChecked(true);
        }
        ageEdit.setText(age.toString());
        Log.d("here", dailyCaloriesGoal.toString());
        goalEdit.setText(dailyCaloriesGoal.toString());
        updateUI();

        gender_result.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == gender_male.getId()) {
                    gender = "male";
                } else {
                    gender = "female";
                }
                settingDB.updateGender(gender);
                updateUI();
            }
        });
    }

    private void updateUI(){
        Integer suggestion = calorieCal.CalculateSuggestion(gender, age);
        textview_Suggestion.setText("According to your gender of " + gender + " and age:" + age +","
                + " the recommended daily calorie intake is " + suggestion + " calories");
    }

    private TextWatcher watchAgeChange = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                age = Integer.valueOf(s.toString());
                settingDB.updateAge(age);
                updateUI();
            }
        }
    };

    private TextWatcher watchGoalChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                dailyCaloriesGoal = Integer.valueOf(s.toString());
                settingDB.updateGoal(dailyCaloriesGoal);
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}