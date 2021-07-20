package com.example.cs446_meal_planner.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs446_meal_planner.CalenderActivity;
import com.example.cs446_meal_planner.CalenderDBHelper;
import com.example.cs446_meal_planner.R;
import com.example.cs446_meal_planner.RecipeOverviewActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeView> {
    ArrayList<Recipe> recipeList = new ArrayList<>();
    private Context mcon;
    private CalenderBookingData calenderBookingData;
    public RecipeAdapter(Context con, ArrayList<Recipe> l, CalenderBookingData b)
    {
        this.mcon = con;
        this.recipeList = l;
        this.calenderBookingData = b;
    }
    @NonNull
    @NotNull
    @Override
    public RecipeView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe,parent,false);
        if (this.calenderBookingData != null) {
            Button clickBut = view.findViewById(R.id.go_to_recipe);
            clickBut.setText("Book This Recipe");
        }
        return new RecipeView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecipeView holder, int position) {
        Recipe r = recipeList.get(position);
        holder.recipe_name.setText(r.getName());
        CalenderBookingData bookingData = this.calenderBookingData;
        if (bookingData == null) {
            holder.recipe_view_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcon, RecipeOverviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("instructions",r.getInstruction());
                    bundle.putString("ingredients",r.getIngredients());
                    bundle.putString("recipeName",r.getName());
                    bundle.putInt("id",r.getId());
                    bundle.putDouble("cookingTime", r.getCookingTime());
                    intent.putExtras(bundle);
                    mcon.startActivity(intent);
                }
            });
        } else {
            holder.recipe_view_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CalenderDBHelper db = CalenderDBHelper.getInstance(mcon);
                    CalenderBooking booking = CalenderBooking.builder()
                            .meal_type(bookingData.mealType)
                            .meal_date(bookingData.date.getIntger())
                            .recipe_id(r.getId())
                            .build();
                    db.insertCalenderBooking(booking);
                    Intent back = new Intent(mcon, CalenderActivity.class);
                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mcon.startActivity(back);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeView extends  RecyclerView.ViewHolder{
        TextView recipe_name;
        Button recipe_view_button;

        public RecipeView(@NonNull @NotNull View itemView) {
            super(itemView);
            recipe_name = (TextView)itemView.findViewById(R.id.text_recipe_name);
            recipe_view_button = itemView.findViewById(R.id.go_to_recipe);
        }
    }
}
