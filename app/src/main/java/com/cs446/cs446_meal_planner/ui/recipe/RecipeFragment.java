package com.cs446.cs446_meal_planner.ui.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cs446.cs446_meal_planner.R;
import com.cs446.cs446_meal_planner.databinding.FragmentRecipeBinding;

public class RecipeFragment extends Fragment {

    private RecipeViewModel recipeViewModel;
    private FragmentRecipeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recipeViewModel =
                new ViewModelProvider(this).get(RecipeViewModel.class);

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button view_recipe_create = (Button)root.findViewById(R.id.create_recipe);
        Log.d("whattt", "hiiiiii");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}