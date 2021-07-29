package com.example.cs446_meal_planner.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs446_meal_planner.MainActivity;
import com.example.cs446_meal_planner.R;
import com.example.cs446_meal_planner.RecipeDBHelper;
import com.example.cs446_meal_planner.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecipeDBHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button picker1 = root.findViewById(R.id.button_pick_date_1);
        Button picker2 = root.findViewById(R.id.button_pick_date_2);
        picker1.setText(MainActivity.reportStartDate.getMonthOfYear() + "/" + MainActivity.reportStartDate.getDayOfMonth());
        picker2.setText(MainActivity.reportEndDate.getMonthOfYear() + "/" + MainActivity.reportEndDate.getDayOfMonth());


        return root;
    }
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}