package com.example.cs446_meal_planner;

import android.app.Person;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cs446_meal_planner.model.CalendarBooking;
import com.example.cs446_meal_planner.model.PersonalInfo;
import com.example.cs446_meal_planner.model.Recipe;

import java.util.ArrayList;

public class SettingDBHelper extends DBHelper {
    public static final String SETTING_TABLE_NAME = "SettingTable";
    public static final String SETTING_ID = "id";
    public static final String SETTING_GENDER = "gender";
    public static final String SETTING_AGE = "age";
    public static final String SETTING_GOAL = "goal";
    public static final Integer default_id = 1;

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
        // insert default row if not present
        if (get_personal_info() == null) {
            db.execSQL("insert into SettingTable (id,gender,age,goal) values (1,'male',18,2200)");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_UPGRADE_DB);
        onCreate(db);
    }

    public PersonalInfo get_personal_info() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM SettingTable", null);
        res.moveToFirst();
        PersonalInfo info = null;

        // should have only 1 entry
        while (res.isAfterLast() == false) {
            info = PersonalInfo.builder()
                    .age(res.getInt(res.getColumnIndex(SETTING_AGE)))
                    .gender(res.getString(res.getColumnIndex(SETTING_GENDER)))
                    .goal(res.getInt(res.getColumnIndex(SETTING_GOAL)))
                    .build();
            res.moveToNext();
        }
        return info;
    }

    public boolean updateGender(String gender) {
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
        ContentValues cv = new ContentValues();
        cv.put(SETTING_GENDER, gender);
        db.update(SETTING_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(default_id)});
        return true;
    }

    public boolean updateAge(Integer age) {
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
        ContentValues cv = new ContentValues();
        cv.put(SETTING_AGE, age);
        db.update(SETTING_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(default_id)});
        return true;
    }


    public boolean updateGoal(Integer goal) {
        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
        ContentValues cv = new ContentValues();
        cv.put(SETTING_GOAL, goal);
        db.update(SETTING_TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(default_id)});
        return true;
    }

    private final String QUERY_CREATE_SETTING_DB = "CREATE TABLE IF NOT EXISTS SettingTable (" +
            "id INTEGER PRIMARY KEY, " +
            "gender TEXT, " +
            "age INTEGER, " +
            "goal INTEGER" +
            ")";

    private final String QUERY_UPGRADE_DB = "DROP TABLE IF EXISTS SettingTable";
    private final String GetAge = "SELECT * FROM CalenderTable WHERE id=?";

}
