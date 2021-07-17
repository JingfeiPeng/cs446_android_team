package com.example.cs446_meal_planner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.cs446_meal_planner.model.CalenderBooking;

public class CalenderDBHelper extends DBHelper{
    public static final String CALENDER_TABLE_NAME = "CalenderTable";
    private static CalenderDBHelper calenderDBHelper = null;

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
        contentValues.put("meal_date", booking.getMeal_date().toString());
        contentValues.put("meal_type", booking.getMeal_type());
        contentValues.put("recipe_id", booking.getRecipe_id());
        db.insert("CalenderTable", null, contentValues);
        return true;
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
