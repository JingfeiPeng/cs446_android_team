package com.cs446.cs446_meal_planner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cs446.cs446_meal_planner.model.IngredientCaloriesCalculator;
import com.cs446.cs446_meal_planner.model.Recipe;
import com.cs446.cs446_meal_planner.model.WarningMessageGenerator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static android.view.View.GONE;

// The concrete component, interface component is not needed
public class RecipeActivity extends AppCompatActivity {
    protected EditText recipeNameEdit;
    protected EditText edit_calories_total;
    protected TextView warning_message;
    protected LinearLayout add_ingredient_layoutlist;
    protected LinearLayout add_instruction_layoutlist;
    protected Button add_ingredient;
    protected Button add_instruction;
    protected Button add_feedback;
    protected Button get_calorie_estimate;
    protected EditText cookingTimeField;
    protected ImageView imageView;

    protected ArrayAdapter<String> adapter;
    // List of available units of ingredients
    protected String [] units = {"whole", "gram", "teaspoon", "cup", "pound", "tablespoon"};
    protected String [] feedbacks = {"too sweet", "too salty", "too sour", "too spicy", "not sweet enough", "not salty enough", "not sour enough", "not spicy enough"};
    protected IngredientCaloriesCalculator calories_calculator = IngredientCaloriesCalculator.getInstance();
    protected WarningMessageGenerator warningMessageGenerator = new WarningMessageGenerator();

    protected String image_path;



    ActivityResultLauncher<Intent> uploadImageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    uploadImage(data);
                }
            });


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cookingTimeField = findViewById(R.id.edit_cooking_time);
        recipeNameEdit = findViewById(R.id.edit_recipe_name);
        edit_calories_total = findViewById(R.id.edit_calories_total);
        warning_message = findViewById(R.id.warning_message);
        add_ingredient_layoutlist = findViewById(R.id.ingredient_list);
        add_instruction_layoutlist = findViewById(R.id.instruction_list);
        add_ingredient = findViewById(R.id.button_add_ingredient);
        add_instruction = findViewById(R.id.button_add_instruction);
        add_feedback = findViewById(R.id.button_add_feedback);
        get_calorie_estimate = findViewById(R.id.button_get_estimated_calories_total);
        imageView = (ImageView) findViewById(R.id.imageView_recipe_image);


        imageView.setVisibility(View.GONE);

        adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, units);

        add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewIngredient("", "", "");
            }
        });

        add_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewInstruction("");
            }
        });

        get_calorie_estimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_calorie = findViewById(R.id.edit_calories_total);
                Double total_calories = 0.0;
                String total_cal = "";
                for (int i = 0; i < add_ingredient_layoutlist.getChildCount(); i++) {
                    View curIngredientView = add_ingredient_layoutlist.getChildAt(i);
                    EditText curIngredientNumber = (EditText) curIngredientView.findViewById(R.id.edit_ingredient_number);
                    EditText curIngredientUnit = (EditText) curIngredientView.findViewById(R.id.edit_ingredient_unit);
                    EditText curIngredientText = (EditText) curIngredientView.findViewById(R.id.edit_ingredient_name);
                    Double curCal = calories_calculator.calculateCalories(
                            curIngredientText.getText().toString(),
                            curIngredientNumber.getText().toString(),
                            curIngredientUnit.getText().toString()
                    );
                    total_calories += curCal;
                    DecimalFormat df = new DecimalFormat("#.#");
                    df.setRoundingMode(RoundingMode.CEILING);
                    total_cal = df.format(total_calories);
                }
                edit_calorie.setText(total_cal);
            }
        });


        //upload recipe image
        Button buttonUploadImage = findViewById(R.id.button_upload_image);
        buttonUploadImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            uploadImageActivityResultLauncher.launch(
                    Intent.createChooser(intent, "Choose an Image")
            );
        });



    }

    protected Recipe processRecipeData() {
        String instructions = "";
        for(int i = 0; i < add_instruction_layoutlist.getChildCount(); i++)
        {
            View curInstructionView = add_instruction_layoutlist.getChildAt(i);
            EditText curInstructionText = (EditText)curInstructionView.findViewById(R.id.edit_instruction_name);
            instructions += curInstructionText.getText().toString()+"#";
        }
        String ingredients = "";
        Double total_calories = 0.0;
        Boolean is_calorie_edited = false;
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        String formated_calories = "";
        EditText edit_calorie = findViewById(R.id.edit_calories_total);
        if (!edit_calorie.getText().toString().equals("")) {
            total_calories = Double.parseDouble(String.valueOf(edit_calorie.getText()));
            is_calorie_edited = true;
        }

        for (int i = 0; i < add_ingredient_layoutlist.getChildCount(); i++)
        {
            View curIngredientView = add_ingredient_layoutlist.getChildAt(i);
            EditText curIngredientNumber = (EditText)curIngredientView.findViewById(R.id.edit_ingredient_number);
            EditText curIngredientUnit = (EditText)curIngredientView.findViewById(R.id.edit_ingredient_unit);
            EditText curIngredientText = (EditText)curIngredientView.findViewById(R.id.edit_ingredient_name);
            //EditText curIngredientGram = (EditText) curIngredientView.findViewById(R.id.edit_ingredient_gram);
            ingredients += curIngredientText.getText().toString()+"%"+curIngredientNumber.getText().toString()+"%"+curIngredientUnit.getText().toString()+"#";

            if (!is_calorie_edited && !curIngredientNumber.getText().toString().equals("")) {
                Double curCal = calories_calculator.calculateCalories(
                        curIngredientText.getText().toString(),
                        curIngredientNumber.getText().toString(),
                        curIngredientUnit.getText().toString()
                );
                total_calories += curCal;
            }
        }

        formated_calories = df.format(total_calories);
        total_calories = Double.parseDouble(formated_calories);
        // get recipe name
        EditText recipeNameText = (EditText)findViewById(R.id.edit_recipe_name);
        String recipeName = recipeNameText.getText().toString();

        // get cooking time
        String cookingTimeText = ((EditText)findViewById(R.id.edit_cooking_time)).getText().toString();
        Double cookingTime  = cookingTimeText.equals("")
                ? 0.0
                :Double.parseDouble(cookingTimeText);

        Recipe r = Recipe.builder()
                .name(recipeName)
                .ingredients(ingredients)
                .instruction(instructions)
                .cookingTime(cookingTime)
                .calorie(total_calories)
                .imageUrl(image_path)
                .feedbacks("")
                .build();
        return r;
    }
    public void collectIngredient()
    {
        warningMessageGenerator.clear_ingredients();
        for (int i = 0; i < add_ingredient_layoutlist.getChildCount(); i++)
        {
            View curIngredientView = add_ingredient_layoutlist.getChildAt(i);
            EditText curIngredientText = (EditText)curIngredientView.findViewById(R.id.edit_ingredient_name);
            warningMessageGenerator.add_ingredients(curIngredientText.getText().toString());
        }
    }
    public void setWarningMessage()
    {
        collectIngredient();
        String warning = warningMessageGenerator.warning_msg();
        if(!warning.equals(""))
        {
            warning_message.setText(warning);
            warning_message.setVisibility(View.VISIBLE);
        }
        else
        {
            warning_message.setText("");
            warning_message.setVisibility(GONE);
        }
    }
    public void addNewIngredient(String number, String unit, String name) {
        final View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient,null,false);

        // number
        EditText editIngredientNumber = ingredientView.findViewById(R.id.edit_ingredient_number);
        editIngredientNumber.setText(number);

        // unit
        AutoCompleteTextView editIngredientUnit = ingredientView.findViewById(R.id.edit_ingredient_unit);
        editIngredientUnit.setThreshold(1);
        editIngredientUnit.setAdapter(adapter);
        editIngredientUnit.setText(unit);  // checks to be done before calling this function

        // name
        EditText editIngredientName = ingredientView.findViewById(R.id.edit_ingredient_name);
        editIngredientName.setText(name);
        editIngredientName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                setWarningMessage();
            }
        });
        ImageView imageClose = ingredientView.findViewById(R.id.image_remove);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_ingredient_layoutlist.removeView(ingredientView);
                setWarningMessage();
            }
        });
        add_ingredient_layoutlist.addView(ingredientView);
        setWarningMessage();
    }

    public void addNewInstruction(String instruction) {
        final View instructionView = getLayoutInflater().inflate(R.layout.row_add_instruction,null,false);

        EditText editText = (EditText)instructionView.findViewById(R.id.edit_instruction_name);
        ImageView imageClose = (ImageView)instructionView.findViewById(R.id.image_remove);

        if (!instruction.equals("")) {
            editText.setText(instruction);
        }

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_instruction_layoutlist.removeView(instructionView);
            }
        });

        add_instruction_layoutlist.addView(instructionView);
    }
    public void uploadImage(Intent data){
        Uri selectedImage = data.getData();
        image_path = selectedImage.toString();
        Bitmap bitmap = Utils.getImage(this, image_path);
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
    }

}
