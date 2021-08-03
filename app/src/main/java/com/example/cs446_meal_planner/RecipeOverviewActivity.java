package com.example.cs446_meal_planner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.cs446_meal_planner.model.Recipe;
import com.example.cs446_meal_planner.model.RecipeAdvisor;


public class RecipeOverviewActivity extends RecipeActivity {
    RecipeDBHelper db;
    RecipeAdvisor advisor;
    protected LinearLayout add_feedback_layoutlist;
    protected TextView feedback_view;
    Button modify_recipe;
    Button delete_recipe;
    Button add_feedback;
    @LayoutRes
    int layout_id = R.layout.recipe_view_layout;
    String[] instructionList = new String[0];
    String[] ingredientGramList = new String[0];
    String[] feedbackList = new String[0];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(layout_id);
        super.onCreate(savedInstanceState);
        db = RecipeDBHelper.getInstance(RecipeOverviewActivity.this);
        modify_recipe = findViewById(R.id.button_modify_recipe);
        delete_recipe = findViewById(R.id.button_delete_recipe);
        add_feedback = findViewById(R.id.button_add_feedback);
        add_feedback_layoutlist = findViewById(R.id.feedback_list);
        feedback_view = findViewById(R.id.feedback);



        String instructions = getIntent().getExtras().getString("instructions");
        String ingredients =getIntent().getExtras().getString("ingredients");
        String recipeName = getIntent().getExtras().getString("recipeName");
        String feedbacks = getIntent().getExtras().getString("feedbacks");
        Double calories = getIntent().getExtras().getDouble("calories");
        int recipeID = getIntent().getExtras().getInt("id");
        double cookingTime = getIntent().getExtras().getDouble("cookingTime");
        String imageUrl = getIntent().getExtras().getString("imageUrl");
        if(recipeName == null) {
            recipeName = "";
        }
        if (instructions == null) {
            instructions="";
        }
        if(ingredients==null) {
            ingredients="";
        }
        if(feedbacks == null)
        {
            feedbacks = "";
        }
        if (calories==null) {
            calories = 0.0;
        }
        if(imageUrl == null){
            imageUrl = "";
        }

        recipeNameEdit.setText(recipeName);
        cookingTimeField.setText(Double.toString(cookingTime));
        edit_calories_total.setText(Double.toString(calories));
        image_path = imageUrl;

        if (!instructions.equals("")) {
            instructionList = instructions.split("#");
            for (String s : instructionList) {
                addNewInstruction(s);
            }
        }

        if (!ingredients.equals("")) {
            ingredientGramList = ingredients.split("#");
            for (String s : ingredientGramList) {
                if (s.equals("%%")) {
                    continue;
                }
                // ingredient at index 0 and weight at index 1
                String[] ingredientGram = s.split("%");
                String ingredientName = ingredientGram[0];
                String ingredientNumber = ingredientGram[1];
                String ingredientUnit = ingredientGram[2];
                addNewIngredient(ingredientNumber, ingredientUnit, ingredientName);
            }
        }

        if (!feedbacks.equals("")) {
            feedbackList =feedbacks.split("!");
            for (String s : feedbackList) {
                addNewFeedback(Integer.parseInt(s));
            }
        }
        advisor = new RecipeAdvisor(ingredientGramList,feedbackList);
        updateRecommendation();
        //display image
        if(!imageUrl.equals("")){
            ImageView recipeImage = findViewById(R.id.imageView_recipe_image);
            recipeImage.setImageBitmap(Utils.getImage(this.getApplicationContext(), imageUrl));
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            ImageView recipeImage = findViewById(R.id.imageView_recipe_image);
            recipeImage.setVisibility(View.GONE);
        }

        modify_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe r = processRecipeData();
                String feedbacks=collectFeedback();
                db.updateName(r.getName(),recipeID);
                db.updateCookingTime(r.getCookingTime(), recipeID);
                db.updateInstruction(r.getInstruction(),recipeID);
                db.updateIngredients(r.getIngredients(),recipeID);
                db.updateCalorie(r.getCalorie(), recipeID);
                db.updateImageUrl(r.getImageUrl(), recipeID);
                db.updateFeedbacks(feedbacks,recipeID);
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

        add_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewFeedback();
            }
        });
    }

    public String collectFeedback()
    {
        String feedbacks = "";
        for(int i=0;i< add_feedback_layoutlist.getChildCount();i++)
        {
            final View feedbackView = add_feedback_layoutlist.getChildAt(i);
            AppCompatSpinner feedback_item = feedbackView.findViewById(R.id.feedback_item);
            feedbacks+=String.valueOf(feedback_item.getSelectedItemPosition())+"!";
        }
        return feedbacks;
    }
    public void updateRecommendation()
    {
        advisor.setIngredients(ingredientGramList);
        advisor.setFeedbacks(feedbackList);
        feedback_view.setText(advisor.recommend());
    }
    public void addNewFeedback()
    {
        final View feedback_view = getLayoutInflater().inflate(R.layout.feedback_add,null,false);
        ArrayAdapter feedback_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,feedbacks);
        AppCompatSpinner feedback_select = feedback_view.findViewById(R.id.feedback_item);
        ImageView imageClose = feedback_view.findViewById(R.id.image_remove);
        feedback_select.setAdapter(feedback_adapter);
        feedback_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                feedbackList = collectFeedback().split("!");
                updateRecommendation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_feedback_layoutlist.removeView(feedback_view);
                feedbackList = collectFeedback().split("!");
                updateRecommendation();
            }
        });
        add_feedback_layoutlist.addView(feedback_view);
        feedbackList = collectFeedback().split("!");
        updateRecommendation();

    }
    public void addNewFeedback(int position)
    {
        final View feedback_view = getLayoutInflater().inflate(R.layout.feedback_add,null,false);
        ArrayAdapter feedback_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,feedbacks);
        AppCompatSpinner feedback_select = feedback_view.findViewById(R.id.feedback_item);
        ImageView imageClose = feedback_view.findViewById(R.id.image_remove);
        feedback_select.setAdapter(feedback_adapter);
        feedback_select.setSelection(position);
        feedback_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                feedbackList = collectFeedback().split("!");
                updateRecommendation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_feedback_layoutlist.removeView(feedback_view);
            }
        });
        add_feedback_layoutlist.addView(feedback_view);
    }
}