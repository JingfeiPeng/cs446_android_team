package com.example.cs446_meal_planner.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs446_meal_planner.DBHelper;
import com.example.cs446_meal_planner.databinding.FragmentHomeBinding;
import com.example.cs446_meal_planner.model.Recipe;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private DBHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}