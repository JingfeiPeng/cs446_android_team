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

public class SettingDBHelper extends DBHelper {
    public static final String SETTING_TABLE_NAME = "SettingTable";
    public static final String SETTING_ID = "id";
    public static final String SETTING_GENDER = "gender";
    public static final String SETTING_AGE = "age";
    public static final String SETTING_SUGGESTION = "suggestion";
    public static final String SETTING_GOAL = "goal";

    private static SettingDBHelper settingDBHelper = null;

    private SettingDBHelper(Context context) {
        super(context);
    }

    public static SettingDBHelper getInstance(Context ctx) {
        if (settingDBHelper == null) {
            settingDBHelper = new SettingDBHelper(ctx.getApplicationContext());
        }

        return settingDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_SETTING_DB);
        db.execSQL("insert into SettingTable (id,gender,age,suggestion,goal) values (1,'male',4,1400,1400)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_UPGRADE_DB);
        onCreate(db);
    }



    public boolean updateGender(String gender) {
        ContentValues cv = new ContentValues();
        cv.put(SETTING_GENDER, gender);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(SETTING_TABLE_NAME, cv, null,null);
        return true;
    }

    public boolean updateAge(Integer age) {
        ContentValues cv = new ContentValues();
        cv.put(SETTING_AGE, age);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(SETTING_TABLE_NAME, cv, null,null);
        return true;
    }

    public boolean updateSuggestion(Integer suggestion) {
        ContentValues cv = new ContentValues();
        cv.put(SETTING_SUGGESTION, suggestion);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(SETTING_TABLE_NAME, cv, null,null);
        return true;
    }

    public boolean updateGoal(Integer goal) {
        ContentValues cv = new ContentValues();
        cv.put(SETTING_GOAL, goal);
        SQLiteDatabase db = this.getReadableDatabase();
        db.update(SETTING_TABLE_NAME, cv, null,null);
        return true;
    }



    private final String QUERY_CREATE_SETTING_DB = "CREATE TABLE IF NOT EXISTS SettingTable (" +
            "id INTEGER PRIMARY KEY, " +
            "gender TEXT, " +
            "age INTEGER, " +
            "suggestion INTEGER, " +
            "goal INTEGER" +
            ")";

    private final String QUERY_UPGRADE_DB = "DROP TABLE IF EXISTS SettingTable";
    private final String GetAge = "SELECT * FROM CalenderTable WHERE id=?";

}
