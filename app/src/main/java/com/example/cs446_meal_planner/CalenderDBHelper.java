package com.example.cs446_meal_planner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cs446_meal_planner.model.CalenderBooking;
import com.example.cs446_meal_planner.model.Recipe;

import java.util.ArrayList;

public class CalenderDBHelper extends DBHelper{
    public static final String CALENDER_TABLE_NAME = "CalenderTable";
    private static CalenderDBHelper calenderDBHelper = null;
    private final String MEAL_DATE = "meal_date";
    private final String MEAL_TYPE = "meal_type";
    private final String RECIPE_ID = "recipe_id";

    private CalenderDBHelper(Context context) {
        super(context);
    }

    public static CalenderDBHelper getInstance(Context ctx) {
        if (calenderDBHelper == null) {
            calenderDBHelper = new CalenderDBHelper(ctx.getApplicationContext());
        }

        return calenderDBHelper;
    }
    // add booking of meal on calender
    public boolean insertCalenderBooking(CalenderBooking booking) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("meal_date", booking.getMealDate());
        contentValues.put("meal_type", booking.getMealType());
        contentValues.put("recipe_id", booking.getRecipeId());
        db.insert("CalenderTable", null, contentValues);
        return true;
    }

    public ArrayList<CalenderBooking> getBookingInRange(Integer fromDate, Integer toDate){
        ArrayList<CalenderBooking> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CALENDER_TABLE_NAME +
                " WHERE "+MEAL_DATE + ">=? AND " + MEAL_DATE + " <=?", new String[]{fromDate.toString(), toDate.toString()});
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Integer attachedRecipeId = res.getInt(res.getColumnIndex(RECIPE_ID));
            Cursor recipeResult = db.rawQuery("select * from "+RecipeDBHelper.RECIPE_COOKING_TIME +
                    " WHERE id=?", new String[]{ attachedRecipeId.toString() });

            Recipe booked_recipe = Recipe.builder()
                    .id(attachedRecipeId)
                    .name(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_NAME)))
                    .cookingTime(Double.parseDouble(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_COOKING_TIME))))
                    .ingredients(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_INGREDIENTS)))
                    .instruction(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_INSTRUCTION)))
                    .imageUrl(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_IMAGE_URL)))
                    .build();

            CalenderBooking recipe = CalenderBooking.builder()
                    .meal_date(res.getInt(res.getColumnIndex(MEAL_DATE)))
                    .meal_type(res.getString(res.getColumnIndex(MEAL_TYPE)))
                    .recipe_id(attachedRecipeId)
                    .booked_recipe(booked_recipe)
                    .build();
            result.add(recipe);
            res.moveToNext();
        }
        return result;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_CALENDER_DB);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_UPGRADE_DB);
        onCreate(db);
    }

    private final String QUERY_CREATE_CALENDER_DB = "CREATE TABLE IF NOT EXISTS CalenderTable (" +
            "meal_date date," +
            "meal_type VARCHAR(10)," +
            "recipe_id integer," +
            "PRIMARY Key (meal_date, meal_type)," +
            "Foreign Key (recipe_id) references RecipeTable(id) ON DELETE CASCADE" +
            ")";
    private final String QUERY_UPGRADE_DB = "DROP TABLE IF EXISTS CalenderTable";

}
