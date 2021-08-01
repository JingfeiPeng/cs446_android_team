package com.example.cs446_meal_planner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cs446_meal_planner.model.CalendarBooking;
import com.example.cs446_meal_planner.model.CalendarDate;
import com.example.cs446_meal_planner.model.Recipe;

import org.joda.time.DateTime;

public class CalendarDBHelper extends DBHelper {
    public static final String CALENDER_TABLE_NAME = "CalenderTable";
    private static CalendarDBHelper calendarDBHelper = null;
    private final String MEAL_DATE = "meal_date";
    private final String MEAL_TYPE = "meal_type";
    private final String RECIPE_ID = "recipe_id";

    private CalendarDBHelper(Context context) {
        super(context);
    }

    public static CalendarDBHelper getInstance(Context ctx) {
        if (calendarDBHelper == null) {
            calendarDBHelper = new CalendarDBHelper(ctx.getApplicationContext());
        }

        return calendarDBHelper;
    }
    // add booking of meal on calender
    public boolean insertCalenderBooking(CalendarBooking booking) {
        SQLiteDatabase db = this.getReadableDatabase();
        this.onCreate(db);
        ContentValues contentValues = new ContentValues();
        contentValues.put("meal_date", booking.getMealDate());
        contentValues.put("meal_type", booking.getMealType());
        contentValues.put("recipe_id", booking.getRecipeId());
        if (getMealBookingOnDate(new CalendarDate(booking.getMealDate()), booking.getMealType()) == null){
            db.insert("CalenderTable", null, contentValues);
        } else {
            db.update(CALENDER_TABLE_NAME, contentValues,
                    "meal_date = ? AND meal_type= ?",
                    new String[]{String.valueOf(booking.getMealDate()), booking.getMealType()});
        }
        return true;
    }

    public CalendarBooking getMealBookingOnDate(CalendarDate date, String mealType) {
        SQLiteDatabase db = this.getReadableDatabase();
        this.onCreate(db);
        Cursor res = db.rawQuery(FIND_BOOKING, new String[]{mealType, String.valueOf(date.getIntger())});
        res.moveToFirst();
        CalendarBooking retVal = null;
        if (res.isAfterLast() == false) {
            Integer attachedRecipeId = res.getInt(res.getColumnIndex(RECIPE_ID));
            Cursor recipeResult = db.rawQuery("select * from "+ RecipeDBHelper.RECIPE_TABLE_NAME +
                    " WHERE id=?", new String[]{ attachedRecipeId.toString() });
            recipeResult.moveToFirst();

            if(recipeResult.isAfterLast()){
                return null;
            }

            Recipe booked_recipe = Recipe.builder()
                    .id(attachedRecipeId)
                    .name(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_NAME)))
                    .cookingTime(Double.parseDouble(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_COOKING_TIME))))
                    .ingredients(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_INGREDIENTS)))
                    .instruction(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_INSTRUCTION)))
                    .imageUrl(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_IMAGE_URL)))
                    .calorie(recipeResult.getDouble(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_CALORIE)))
                    .build();

            retVal = CalendarBooking.builder()
                    .mealDate(res.getInt(res.getColumnIndex(MEAL_DATE)))
                    .mealType(res.getString(res.getColumnIndex(MEAL_TYPE)))
                    .recipeId(attachedRecipeId)
                    .bookedRecipe(booked_recipe)
                    .build();
        }
        return retVal;
    }

    public CalendarBooking getNextMealBooking() {
        SQLiteDatabase db = this.getReadableDatabase();
        this.onCreate(db);
        CalendarDate cur = new CalendarDate(DateTime.now());
        Cursor res = db.rawQuery(FIND_NEXT_BOOKING, new String[]{String.valueOf(cur.getIntger())});
        res.moveToFirst();
        CalendarBooking retVal = null;
        if (!res.isAfterLast()) {
            Integer attachedRecipeId = res.getInt(res.getColumnIndex(RECIPE_ID));
            Cursor recipeResult = db.rawQuery("select * from "+ RecipeDBHelper.RECIPE_TABLE_NAME +
                    " WHERE id=?", new String[]{ attachedRecipeId.toString() });
            recipeResult.moveToFirst();

            if(recipeResult.isAfterLast()){
                return null;
            }

            Recipe booked_recipe = Recipe.builder()
                    .id(attachedRecipeId)
                    .name(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_NAME)))
                    .cookingTime(Double.parseDouble(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_COOKING_TIME))))
                    .ingredients(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_INGREDIENTS)))
                    .instruction(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_INSTRUCTION)))
                    .imageUrl(recipeResult.getString(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_IMAGE_URL)))
                    .calorie(recipeResult.getDouble(recipeResult.getColumnIndex(RecipeDBHelper.RECIPE_CALORIE)))
                    .build();

            retVal = CalendarBooking.builder()
                    .mealDate(res.getInt(res.getColumnIndex(MEAL_DATE)))
                    .mealType(res.getString(res.getColumnIndex(MEAL_TYPE)))
                    .recipeId(attachedRecipeId)
                    .bookedRecipe(booked_recipe)
                    .build();
        }
        return retVal;
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
    private final String FIND_BOOKING = "SELECT * FROM CalenderTable" +
            " WHERE meal_type=? AND meal_date=?";
    private final String FIND_NEXT_BOOKING = "SELECT * FROM CalenderTable" +
            " WHERE meal_date >= ?" +
            " ORDER BY meal_date ASC," +
            " CASE WHEN meal_type = 'breakfast' THEN 0 WHEN meal_type = 'lunch' THEN 1 WHEN meal_type = 'dinner' THEN 2 END" +
            " LIMIT 1";

}
