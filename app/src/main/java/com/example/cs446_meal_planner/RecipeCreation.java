package com.example.cs446_meal_planner;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cs446_meal_planner.model.Recipe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


public class RecipeCreation extends AppCompatActivity{
    LinearLayout add_ingredient_layoutlist;
    LinearLayout add_instruction_layoutlist;
    Button add_ingredient;
    Button add_instruction;
    Button parse_recipe_url;
    Button submit_recipe;
    String recipe_url;
    ArrayAdapter<String> adapter;
    // List of available units of ingredients
    String [] units = {"whole", "g", "teaspoon", "cup", "pound", "tablespoon"};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipe_creation_view);
        add_ingredient_layoutlist=findViewById(R.id.ingredient_list);
        add_instruction_layoutlist=findViewById(R.id.instruction_list);

        add_ingredient = findViewById(R.id.button_add_ingredient);
        add_instruction = findViewById(R.id.button_add_instruction);
        parse_recipe_url = findViewById(R.id.button_parse_recipe_url);
        submit_recipe = findViewById(R.id.button_submit_recipe);

        adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, units);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient,null,false);

                AutoCompleteTextView acTextView = (AutoCompleteTextView) ingredientView.findViewById(R.id.edit_ingredient_unit);
                acTextView.setThreshold(1);
                acTextView.setAdapter(adapter);

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
        parse_recipe_url.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText edit_recipe_url = findViewById(R.id.edit_recipe_url);
                recipe_url = edit_recipe_url.getText().toString();

                ParseURL parseURL = new ParseURL();
                parseURL.execute();

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
                    EditText curIngredientNumber = (EditText)curIngredientView.findViewById(R.id.edit_ingredient_number);
                    EditText curIngredientUnit = (EditText)curIngredientView.findViewById(R.id.edit_ingredient_unit);
                    EditText curIngredientText = (EditText)curIngredientView.findViewById(R.id.edit_ingredient_name);
                    //EditText curIngredientGram = (EditText) curIngredientView.findViewById(R.id.edit_ingredient_gram);
                    ingredients += curIngredientText.getText().toString()+"%"+curIngredientNumber.getText().toString()+"%"+curIngredientUnit.getText().toString()+"#";
                }
                // get recipe name
                EditText recipeNameText = (EditText)findViewById(R.id.edit_recipe_name);
                String recipeName = recipeNameText.getText().toString();

                // get cooking time
                EditText cookingTimeText = (EditText)findViewById(R.id.edit_cooking_time);
                Recipe r = Recipe.builder()
                        .name(recipeName)
                        .ingredients(ingredients)
                        .instruction(instructions)
                        .cookingTime(Double.parseDouble(cookingTimeText.getText().toString()))
                        .imageUrl("xxxx").build();
                DBHelper db = new DBHelper(RecipeCreation.this);
                db.insertRecipe(r);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }

    private class ParseURL extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            Looper.prepare();
            try {
                Document doc = Jsoup.connect(recipe_url).get();

                // Recipe title
                EditText edit_recipe_name = findViewById(R.id.edit_recipe_name);
                edit_recipe_name.setText(doc.title());

                // Recipe Ingredients
                Elements ingredientSections = doc.getElementsByAttributeValueContaining("class", "recipe-ingredients");
                if (ingredientSections.size() > 0) {
                    Element ingredientSection = ingredientSections.get(0);
                    Elements ingredients = ingredientSection.getElementsByTag("p");
                    for (Element ingredient: ingredients) {
                        String text = ingredient.text();
                        publishProgress("ingredient", text);
                    }
                }

                // Recipe Instructions
                Elements instructionSections = doc.getElementsByAttributeValueContaining("class", "instruction");
                if (instructionSections.size() == 0) {
                    instructionSections = doc.getElementsByAttributeValueContaining("class", "direction");
                }
                if (instructionSections.size() > 0) {
                    Element instructionSection = instructionSections.get(0);
                    Elements instructions = instructionSection.getElementsByTag("p");
                    for (Element instruction: instructions) {
                        String text = instruction.text();
                        publishProgress("instruction", text);
                    }
                }

                // Recipe Cook Time
                Elements timeSections = doc.select("div[class=recipe-copy-right]");
                Element timeSection = timeSections.get(1);
                Elements times = timeSection.getElementsByTag("span");
                Element rawTime = times.get(2);
                String time = rawTime.text().substring(0, rawTime.text().indexOf(" "));
                publishProgress("time", time);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onProgressUpdate(String... values) {
            if (values[0] == "instruction") {
                final View instructionView = getLayoutInflater().inflate(R.layout.row_add_instruction,null,false);

                EditText editText = (EditText)instructionView.findViewById(R.id.edit_instruction_name);
                editText.setText(values[1]);
                ImageView imageClose = (ImageView)instructionView.findViewById(R.id.image_remove);

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_instruction_layoutlist.removeView(instructionView);
                    }
                });

                add_instruction_layoutlist.addView(instructionView);
            } else if (values[0] == "ingredient") {
                // parse the content
                String raw_amount = values[1].substring(0, values[1].indexOf(' '));
                float amount = 0;
                for (int i=0; i < raw_amount.length(); ++i) {
                    char c = raw_amount.charAt(i);
                    switch(c) {
                        case '\u00bd':
                            amount += 0.5;
                            break;
                        case '\u2153':
                            amount += 0.33;
                            break;
                        case '\u2154':
                            amount += 0.67;
                            break;
                        case '\u00bc':
                            amount += 0.25;
                            break;
                        case '\u00be':
                            amount += 0.75;
                            break;
                        default:
                            amount += Character.getNumericValue(c);
                            break;
                    }
                }
                String temp = values[1].substring(values[1].indexOf(' ')+1);
                String raw_unit = temp.substring(0, temp.indexOf(' '));
                Optional<String> matching_unit = Arrays.stream(units).filter(raw_unit::contains).findAny();
                String raw_name = temp.substring(temp.indexOf(" ")+1);
                String name = raw_name.split("\\,")[0];

                final View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient,null,false);

                AutoCompleteTextView acTextView = (AutoCompleteTextView) ingredientView.findViewById(R.id.edit_ingredient_unit);
                acTextView.setThreshold(1);
                acTextView.setAdapter(adapter);

                EditText url_ingredient_name = ingredientView.findViewById(R.id.edit_ingredient_name);
                url_ingredient_name.setText(name);
                EditText url_ingredient_amount = ingredientView.findViewById(R.id.edit_ingredient_number);
                url_ingredient_amount.setText(String.format ("%.2f", amount));
                AutoCompleteTextView url_ingredient_unit = ingredientView.findViewById(R.id.edit_ingredient_unit);
                if (matching_unit.isPresent()) {
                    url_ingredient_unit.setText(matching_unit.get());
                }
                ImageView imageClose = (ImageView)ingredientView.findViewById(R.id.image_remove);

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_ingredient_layoutlist.removeView(ingredientView);
                    }
                });

                add_ingredient_layoutlist.addView(ingredientView);
            } else if (values[0] == "time") {
                EditText edit_cooking_time = findViewById(R.id.edit_cooking_time);
                edit_cooking_time.setText(values[1]);
            }
        }
    }

    public void submitRecipe(View v)
    {

    }
    public void viewRecipeCreation(View v)
    {
        startActivity(new Intent(getApplicationContext(), RecipeCreation.class));
    }
}