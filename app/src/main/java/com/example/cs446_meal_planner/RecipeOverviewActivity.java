package com.example.cs446_meal_planner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.example.cs446_meal_planner.DriveServiceHelper.getGoogleDriveService;


public class RecipeOverviewActivity extends AppCompatActivity {

    LinearLayout add_ingredient_layoutlist;
    LinearLayout add_instruction_layoutlist;
    Button add_ingredient;
    Button add_instruction;
    Button modify_recipe;
    Button delete_recipe;
    ImageView recipe_image_view;
    DriveServiceHelper driveServiceHelper;
    RecipeDBHelper db;
    private static ActivityResultLauncher<Intent> signInActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view_layout);

        db = RecipeDBHelper.getInstance(RecipeOverviewActivity.this);

        signInActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            handleSignInResult(data);
                        }
                    }
                });


        String instructions = getIntent().getExtras().getString("instructions");
        String ingredients =getIntent().getExtras().getString("ingredients");
        String recipeName = getIntent().getExtras().getString("recipeName");
        int recipeID = getIntent().getExtras().getInt("id");
        double cookingTime = getIntent().getExtras().getDouble("cookingTime");
        String image_id = getIntent().getExtras().getString("imageUrl");

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
        EditText curRecipeName = findViewById(R.id.edit_recipe_name_view);
        curRecipeName.setText(recipeName);
        EditText cookingTimeField = findViewById(R.id.edit_cooking_time);
        cookingTimeField.setText(Double.toString(cookingTime));

        if (!instructions.equals("")) {
            String[] instructionList = instructions.split("#");
            for (String s : instructionList) {
                final View instructionView = getLayoutInflater().inflate(R.layout.row_add_instruction, null, false);
                EditText instructionText = instructionView.findViewById(R.id.edit_instruction_name);
                instructionText.setText(s);
                add_instruction_layoutlist.addView(instructionView);

                // handle button close
                ImageView imageClose = instructionView.findViewById(R.id.image_remove);
                imageClose.setOnClickListener(v -> add_instruction_layoutlist.removeView(instructionView));
            }
        }

        if (!ingredients.equals("")) {
            String[] ingredientGramList = ingredients.split("#");
            for (String s : ingredientGramList) {
                // ingredient at index 0 and weight at index 1
                String[] ingredientGram = s.split("%");
                String ingredientName = ingredientGram[0];
                String ingredientNumber = ingredientGram[1];
                String ingredientUnit = ingredientGram[2];
                final View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient, null, false);
                EditText ingredientText = ingredientView.findViewById(R.id.edit_ingredient_name);
                ingredientText.setText(ingredientName);
                EditText ingredientUnitNumber = ingredientView.findViewById(R.id.edit_ingredient_number);
                ingredientUnitNumber.setText(ingredientNumber);
                AutoCompleteTextView unitEditText = ingredientView.findViewById(R.id.edit_ingredient_unit);
                unitEditText.setText(ingredientUnit);
                add_ingredient_layoutlist.addView(ingredientView);

                // handle close button
                ImageView imageClose = ingredientView.findViewById(R.id.image_remove);
                imageClose.setOnClickListener(v -> add_ingredient_layoutlist.removeView(ingredientView));
            }
        }

        add_ingredient = findViewById(R.id.button_add_ingredient_view);
        add_instruction = findViewById(R.id.button_add_instruction_view);
        modify_recipe = findViewById(R.id.button_modify_recipe);
        delete_recipe = findViewById(R.id.button_delete_recipe);
        recipe_image_view = findViewById(R.id.recipe_image_view);

        recipe_image_view.setImageBitmap(driveServiceHelper.readFile(image_id).getResult());

        add_ingredient.setOnClickListener(v -> {
            final View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient,null,false);

            EditText editText = ingredientView.findViewById(R.id.edit_ingredient_name);
            ImageView imageClose = ingredientView.findViewById(R.id.image_remove);

            imageClose.setOnClickListener(v12 -> add_ingredient_layoutlist.removeView(ingredientView));

            add_ingredient_layoutlist.addView(ingredientView);
        });

        add_instruction.setOnClickListener(v -> {
            final View instructionView = getLayoutInflater().inflate(R.layout.row_add_instruction,null,false);

            EditText editText = instructionView.findViewById(R.id.edit_instruction_name);
            ImageView imageClose = instructionView.findViewById(R.id.image_remove);

            imageClose.setOnClickListener(v1 -> add_instruction_layoutlist.removeView(instructionView));

            add_instruction_layoutlist.addView(instructionView);
        });

        modify_recipe.setOnClickListener(v -> {
            StringBuilder instructions1 = new StringBuilder();
            for(int i=0;i<add_instruction_layoutlist.getChildCount();i++)
            {
                View curInstructionView = add_instruction_layoutlist.getChildAt(i);
                EditText curInstructionText = curInstructionView.findViewById(R.id.edit_instruction_name);
                instructions1.append(curInstructionText.getText().toString()).append("#");
            }
            StringBuilder ingredients1 = new StringBuilder();
            for(int i=0;i<add_ingredient_layoutlist.getChildCount();i++)
            {
                View curIngredientView = add_ingredient_layoutlist.getChildAt(i);
                EditText curIngredientText = curIngredientView.findViewById(R.id.edit_ingredient_name);
                AutoCompleteTextView curIngredientUnit = curIngredientView.findViewById(R.id.edit_ingredient_unit);
                EditText curIngredientNumber = curIngredientView.findViewById(R.id.edit_ingredient_number);
                ingredients1.append(curIngredientText.getText().toString()).append("%").append(curIngredientNumber.getText().toString()).append("%").append(curIngredientUnit.getText().toString()).append("#");
            }

            db.updateName(curRecipeName.getText().toString(),recipeID);
            db.updateCookingTime(Double.parseDouble(cookingTimeField.getText().toString()), recipeID);
            db.updateInstruction(instructions1.toString(),recipeID);
            db.updateIngredients(ingredients1.toString(),recipeID);
            Intent back = new Intent(getApplicationContext(), RecipeListActivity.class);
            back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(back);
        });
        delete_recipe.setOnClickListener(v -> {
            db.deleteRecipe(recipeID);
            startActivity(new Intent(getApplicationContext(), RecipeListActivity.class));
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (account == null) {
            signIn();
        } else {
            driveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), account, "MealPlanner"));
        }
    }

    private void signIn() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(new Scope("https://www.googleapis.com/auth/drive"))
                        .requestEmail()
                        .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), signInOptions);
        signInActivityResultLauncher.launch(mGoogleSignInClient.getSignInIntent());
    }

    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        driveServiceHelper = new DriveServiceHelper(getGoogleDriveService(getApplicationContext(), googleSignInAccount, "MealPlanner"));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(null, "Unable to sign in.", e);
                    }
                });
    }



    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}