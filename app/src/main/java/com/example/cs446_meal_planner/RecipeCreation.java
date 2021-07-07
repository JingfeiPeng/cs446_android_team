package com.example.cs446_meal_planner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cs446_meal_planner.model.Recipe;


public class RecipeCreation extends AppCompatActivity{
    LinearLayout add_ingredient_layoutlist;
    LinearLayout add_instruction_layoutlist;
    Button add_ingredient;
    Button add_instruction;
    Button submit_recipe;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipe_creation_view);
        add_ingredient_layoutlist=findViewById(R.id.ingredient_list);
        add_instruction_layoutlist=findViewById(R.id.instruction_list);

        add_ingredient = findViewById(R.id.button_add_ingredient);
        add_instruction = findViewById(R.id.button_add_instruction);
        submit_recipe = findViewById(R.id.button_submit_recipe);

        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient,null,false);

                EditText editText = (EditText)ingredientView.findViewById(R.id.edit_ingredient_name);
                ImageView imageClose = (ImageView)ingredientView.findViewById(R.id.image_remove);

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_ingredient_layoutlist.removeView(ingredientView);
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
                        add_instruction_layoutlist.removeView(instructionView);
                    }
                });

                add_instruction_layoutlist.addView(instructionView);
            }
        });
        submit_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String instructions = "";
                for(int i=0;i<add_instruction_layoutlist.getChildCount();i++)
                {
                    View curInstructionView = add_instruction_layoutlist.getChildAt(i);
                    EditText curInstructionText = (EditText)curInstructionView.findViewById(R.id.edit_instruction_name);
                    instructions += curInstructionText.getText().toString()+"#";
                }
                String ingredients = "";
                for(int i=0;i<add_ingredient_layoutlist.getChildCount();i++)
                {
                    View curIngredientView = add_ingredient_layoutlist.getChildAt(i);
                    EditText curIngredientText = (EditText)curIngredientView.findViewById(R.id.edit_ingredient_name);
                    ingredients += curIngredientText.getText().toString()+"#";
                }
                Recipe r = Recipe.builder()
                        .name("placeholder")
                        .ingredients(ingredients)
                        .instruction(instructions)
                        .cookingTime(55)
                        .imageUrl("xxxx").build();
                DBHelper db = new DBHelper(RecipeCreation.this);
                db.insertRecipe(r);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }


        });

    }
    public void submitRecipe(View v)
    {

    }
    public void viewRecipeCreation(View v)
    {
        startActivity(new Intent(getApplicationContext(), RecipeCreation.class));
    }
}