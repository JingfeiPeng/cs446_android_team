package com.example.cs446_meal_planner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.navigation.ui.AppBarConfiguration;


public class ViewRecipe extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    LinearLayout add_ingredient_layoutlist;
    LinearLayout add_instruction_layoutlist;
    Button add_ingredient;
    Button add_instruction;
    Button modify_recipe;
    Button delete_recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view_layout);
        String instructions = getIntent().getExtras().getString("instructions");
        String ingredients =getIntent().getExtras().getString("ingredients");
        String recipeName = getIntent().getExtras().getString("recipeName");
        int recipeID = getIntent().getExtras().getInt("id");
        double cookingTime = getIntent().getExtras().getDouble("cookingTime");
        if(recipeName == null)
        {
            recipeName = "";
        }
        if (instructions == null)
        {
            instructions="";
        }
        if(ingredients==null)
        {
            ingredients="";
        }
        add_ingredient_layoutlist=findViewById(R.id.ingredient_list_view);
        add_instruction_layoutlist=findViewById(R.id.instruction_list_view);
        EditText curRecipeName = (EditText) findViewById(R.id.edit_recipe_name_view);
        curRecipeName.setText(recipeName);
        EditText cookingTimeField = (EditText) findViewById(R.id.edit_cooking_time);
        cookingTimeField.setText(Double.toString(cookingTime));

        if (!instructions.equals("")) {
            String[] instructionList = instructions.split("#");
            for (int i = 0; i < instructionList.length; i++) {
                final View instructionView = getLayoutInflater().inflate(R.layout.row_add_instruction,null,false);
                EditText instructionText = instructionView.findViewById(R.id.edit_instruction_name);
                instructionText.setText(instructionList[i]);
                add_instruction_layoutlist.addView(instructionView);

                // handle button close
                ImageView imageClose = instructionView.findViewById(R.id.image_remove);
                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_instruction_layoutlist.removeView(instructionView);
                    }
                });
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
                final View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient,null,false);
                EditText ingredientText = ingredientView.findViewById(R.id.edit_ingredient_name);
                ingredientText.setText(ingredientName);
                EditText ingredientUnitNumber = ingredientView.findViewById(R.id.edit_ingredient_number);
                ingredientUnitNumber.setText(ingredientNumber);
                AutoCompleteTextView unitEditText = ingredientView.findViewById(R.id.edit_ingredient_unit);
                unitEditText.setText(ingredientUnit);
                add_ingredient_layoutlist.addView(ingredientView);

                // handle close button
                ImageView imageClose = ingredientView.findViewById(R.id.image_remove);
                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_ingredient_layoutlist.removeView(ingredientView);
                    }
                });
            }
        }

        add_ingredient = findViewById(R.id.button_add_ingredient_view);
        add_instruction = findViewById(R.id.button_add_instruction_view);
        modify_recipe = findViewById(R.id.button_modify_recipe);
        delete_recipe = findViewById(R.id.button_delete_recipe);

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

        modify_recipe.setOnClickListener(new View.OnClickListener() {
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
                    AutoCompleteTextView curIngredientUnit = (AutoCompleteTextView) curIngredientView.findViewById(R.id.edit_ingredient_unit);
                    EditText curIngredientNumber = (EditText) curIngredientView.findViewById(R.id.edit_ingredient_number);
                    ingredients += curIngredientText.getText().toString()+"%"+curIngredientNumber.getText().toString()+"%"+curIngredientUnit.getText().toString()+"#";
                }
                Log.d("hereherehere", String.valueOf(recipeID));
                RecipeDBHelper db = RecipeDBHelper.getInstance(ViewRecipe.this);
                db.updateName(curRecipeName.getText().toString(),recipeID);
                db.updateCookingTime(Double.parseDouble(cookingTimeField.getText().toString()), recipeID);
                db.updateInstruction(instructions,recipeID);
                db.updateIngredients(ingredients,recipeID);
                Intent back = new Intent(getApplicationContext(), RecipeOverview.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(back);
            }


        });
        delete_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeDBHelper db = RecipeDBHelper.getInstance(ViewRecipe.this);
                db.deleteRecipe(recipeID);
                startActivity(new Intent(getApplicationContext(),RecipeOverview.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}