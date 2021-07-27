package com.example.cs446_meal_planner;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

abstract class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "default.db";
    public Context mCxt;
    public ArrayList<ObserverActivity> observers =  new ArrayList<>();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.mCxt = context;
    }

    public void attachActivity(ObserverActivity activity) {
        observers.add(activity);
    }

    public void detchActivity(ObserverActivity activity){
        observers.remove(activity);
    }

    public void notifyObservers() {
        for (ObserverActivity obs : observers) {
            obs.update();
        }
    }
}
