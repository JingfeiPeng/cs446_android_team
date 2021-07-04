package com.example.cs446_meal_planner;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cs446_meal_planner.databinding.ActivityRecipeCreationBinding;

public class recipe_creation extends AppCompatActivity implements View.OnClickListener {
    LinearLayout add_ingredient_layoutlist;
    LinearLayout add_instruction_layoutlist;
    Button add_ingredient;
    Button add_instruction;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.recipe_creation_view);

        add_ingredient_layoutlist=findViewById(R.id.ingredient_list);
        add_instruction_layoutlist=findViewById(R.id.instruction_list);

        add_ingredient = findViewById(R.id.button_add_ingredient);
        add_instruction = findViewById(R.id.button_add_instruction);

        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient,null,false);

                EditText editText = (EditText)ingredientView.findViewById(R.id.edit_ingredient_name);
                ImageView imageClose = (ImageView)ingredientView.findViewById(R.id.image_remove);

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_ingredient_layoutlist.removeView(v);
                    }
                });

                add_ingredient_layoutlist.addView(ingredientView);
            }
        });
        add_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View instructionView = getLayoutInflater().inflate(R.layout.row_add_instruction,null,false);

                EditText editText = (EditText)instructionView.findViewById(R.id.edit_instruction_name);
                ImageView imageClose = (ImageView)instructionView.findViewById(R.id.image_remove);

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_instruction_layoutlist.removeView(v);
                    }
                });

                add_instruction_layoutlist.addView(instructionView);
            }
        });

    }

    @Override
    public void onClick(View v) {
        addView();
    }

    private void addView() {

    }
}