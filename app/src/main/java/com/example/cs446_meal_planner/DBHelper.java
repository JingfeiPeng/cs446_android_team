package com.example.cs446_meal_planner;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

abstract class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "default.db";
    public Context mCxt;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.mCxt = context;
    }
}
