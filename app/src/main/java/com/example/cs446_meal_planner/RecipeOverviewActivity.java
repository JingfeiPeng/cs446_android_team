package com.example.cs446_meal_planner;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cs446_meal_planner.model.Recipe;


public class RecipeOverviewActivity extends RecipeActivity {
    RecipeDBHelper db;
    Button modify_recipe;
    Button delete_recipe;
    @LayoutRes
    int layout_id = R.layout.recipe_view_layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(layout_id);
        super.onCreate(savedInstanceState);
        db = RecipeDBHelper.getInstance(RecipeOverviewActivity.this);
        modify_recipe = findViewById(R.id.button_modify_recipe);
        delete_recipe = findViewById(R.id.button_delete_recipe);

        String instructions = getIntent().getExtras().getString("instructions");
        String ingredients =getIntent().getExtras().getString("ingredients");
        String recipeName = getIntent().getExtras().getString("recipeName");
        Double calories = getIntent().getExtras().getDouble("calories");
        int recipeID = getIntent().getExtras().getInt("id");
        double cookingTime = getIntent().getExtras().getDouble("cookingTime");
        if(recipeName == null) {
            recipeName = "";
        }
        if (instructions == null) {
            instructions="";
        }
        if(ingredients==null) {
            ingredients="";
        }
        if (calories==null) {
            calories = 0.0;
        }

        recipeNameEdit.setText(recipeName);
        cookingTimeField.setText(Double.toString(cookingTime));
        edit_calories_total.setText(Double.toString(calories));

        if (!instructions.equals("")) {
            String[] instructionList = instructions.split("#");
            for (int i = 0; i < instructionList.length; i++) {
                addNewInstruction(instructionList[i]);
            }
        }

        if (!ingredients.equals("")) {
            String[] ingredientGramList = ingredients.split("#");
            for (int i = 0; i < ingredientGramList.length; i++) {
                // ingredient at index 0 and weight at index 1
                String[] ingredientGram = ingredientGramList[i].split("%");
                String ingredientName = ingredientGram[0];
                String ingredientNumber = ingredientGram[1];
                String ingredientUnit = ingredientGram[2];
                addNewIngredient(ingredientNumber, ingredientUnit, ingredientName);
            }
        }

        modify_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe r = processRecipeData();
                db.updateName(r.getName(),recipeID);
                db.updateCookingTime(r.getCookingTime(), recipeID);
                db.updateInstruction(r.getInstruction(),recipeID);
                db.updateIngredients(r.getIngredients(),recipeID);
                db.updateCalorie(r.getCalorie(), recipeID);
                db.notifyObservers();
                finish();
            }
        });

        delete_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRecipe(recipeID);
                db.notifyObservers();
                finish();
            }
        });
    }
}