package com.example.cs446_meal_planner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cs446_meal_planner.model.CalendarBooking;
import com.example.cs446_meal_planner.model.Recipe;

import java.util.ArrayList;

public class RecipeDBHelper extends DBHelper {
    public static final String RECIPE_TABLE_NAME = "RecipeTable";
    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_IMAGE_URL = "image_url";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String RECIPE_INSTRUCTION = "instruction";
    public static final String RECIPE_COOKING_TIME = "cooking_time";
    public static final String RECIPE_CALORIE = "calorie";

    private static RecipeDBHelper recipeDBHelper = null;

    private RecipeDBHelper(Context context) {
        super(context);
        onCreate(this.getReadableDatabase());
    }

    public static RecipeDBHelper getInstance(Context ctx) {
        if (recipeDBHelper == null) {
            recipeDBHelper = new RecipeDBHelper(ctx.getApplicationContext());
        }

        return recipeDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_CREATE_RECIPE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_UPGRADE_DB);
        onCreate(db);
    }

    // number of recipes
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, RECIPE_TABLE_NAME);
        return numRows;
    }

    // add recipe
    public boolean insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        int n = numberOfRows();
        if (n == 0) {
            contentValues.put(RECIPE_ID, 1);
        } else {
            Cursor res = db.rawQuery(QUERY_LAST_ROW, null);
            res.moveToFirst();
            contentValues.put(RECIPE_ID, Integer.parseInt(res.getString(res.getColumnIndex(RECIPE_ID))) + 1);
        }
        contentValues.put(RECIPE_NAME, recipe.getName());
        contentValues.put(RECIPE_IMAGE_URL, recipe.getImageUrl());
        contentValues.put(RECIPE_INGREDIENTS, recipe.getIngredients());
        contentValues.put(RECIPE_INSTRUCTION, recipe.getInstruction());
        contentValues.put(RECIPE_COOKING_TIME, recipe.getCookingTime());
        contentValues.put(RECIPE_CALORIE, recipe.getCalorie());
        db.insert(RECIPE_TABLE_NAME, null, contentValues);
        return true;
    }

    // remove recipe
    public Integer deleteRecipe(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Integer deletedId = db.delete(RECIPE_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
        return deletedId;
    }

    // get recipe
    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(QUERY_ALL_RECIPE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Recipe recipe = Recipe.builder()
                    .id(res.getInt(res.getColumnIndex(RECIPE_ID)))
                    .name(res.getString(res.getColumnIndex(RECIPE_NAME)))
                    .imageUrl(res.getString(res.getColumnIndex(RECIPE_IMAGE_URL)))
                    .ingredients(res.getString(res.getColumnIndex(RECIPE_INGREDIENTS)))
                    .instruction(res.getString(res.getColumnIndex(RECIPE_INSTRUCTION)))
                    .cookingTime(Double.parseDouble(res.getString(res.getColumnIndex(RECIPE_COOKING_TIME))))
                    .calorie(Double.parseDouble(res.getString(res.getColumnIndex(RECIPE_CALORIE))))
                    .build();
            result.add(recipe);
            res.moveToNext();
        }

        return result;
    }

    public boolean updateName(String name, Integer id) {
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_NAME, name);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(RECIPE_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean updateImageUrl(String imageUrl, Integer id) {
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_IMAGE_URL, imageUrl);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(RECIPE_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean updateInstruction(String instruction, Integer id) {
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_INSTRUCTION, instruction);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(RECIPE_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean updateCookingTime(Double cookingTime, Integer id) {
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_COOKING_TIME, cookingTime);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(RECIPE_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
        return true;
    }


    public boolean updateIngredients(String ingredients, Integer id) {
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_INGREDIENTS, ingredients);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(RECIPE_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean updateCalorie(Double newCal, Integer id) {
        ContentValues cv = new ContentValues();
        cv.put(RECIPE_CALORIE, newCal);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(RECIPE_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
        return true;
    }

    //delete all recipe
    public boolean deleteAllRecipes() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + RECIPE_TABLE_NAME);
        return true;
    }


    private final String QUERY_CREATE_RECIPE_DB = "CREATE TABLE IF NOT EXISTS RecipeTable (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "image_url TEXT, " +
            "ingredients TEXT, " +
            "instruction TEXT, " +
            "cooking_time REAL," +
            "calorie REAL" +
            ")";

    private final String QUERY_UPGRADE_DB = "DROP TABLE IF EXISTS RecipeTable";
    private final String QUERY_ALL_RECIPE = "SELECT * FROM RecipeTable ORDER BY " + RECIPE_ID + " DESC";
    private final String QUERY_LAST_ROW = "SELECT * FROM " + RECIPE_TABLE_NAME + " ORDER BY " + RECIPE_ID + " DESC LIMIT 1";
}