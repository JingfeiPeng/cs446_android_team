package com.example.cs446_meal_planner.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs446_meal_planner.R;
import com.example.cs446_meal_planner.ViewRecipe;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;


public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.CalenderDateView> {
    private Context mcon;
    private ArrayList<CalenderDate> dateList = new ArrayList<>();

    public CalenderAdapter(Context con, ArrayList<CalenderDate> l) {
        dateList = l;
        mcon = con;
    }

    @NonNull
    @NotNull
    @Override
    public CalenderAdapter.CalenderDateView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_calender_date,parent,false);
        return new CalenderAdapter.CalenderDateView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CalenderAdapter.CalenderDateView holder, int position) {
        CalenderDate date = dateList.get(position);
        holder.calender_date.setText(date.getString());
        TextView[] meals = {holder.mealBreakfast, holder.mealLunch, holder.mealDinner};
        HashMap<String, TextView> mealTypes = new HashMap() {{
            put("breakfast", holder.mealBreakfast);
            put("lunch", holder.mealLunch);
            put("dinner", holder.mealDinner);
        }};
        for (String mealtype : mealTypes.keySet()) {
            mealTypes.get(mealtype).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Clicked", mealtype);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class CalenderDateView extends RecyclerView.ViewHolder {
        TextView calender_date;
        TextView mealBreakfast;
        TextView mealLunch;
        TextView mealDinner;

        public CalenderDateView(@NonNull @NotNull View itemView) {
            super(itemView);
            calender_date = itemView.findViewById(R.id.calender_date);
            mealBreakfast = itemView.findViewById(R.id.mealBreakfast);
            mealLunch = itemView.findViewById(R.id.mealLunch);
            mealDinner = itemView.findViewById(R.id.mealDinner);
        }
    }
}
