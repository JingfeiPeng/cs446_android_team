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

import com.example.cs446_meal_planner.R;
import com.example.cs446_meal_planner.RecipeOverview;
import com.example.cs446_meal_planner.ViewRecipe;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeView> {
    ArrayList<Recipe> recipeList = new ArrayList<>();
    private Context mcon;
    public RecipeAdapter(Context con, ArrayList<Recipe> l)
    {
        this.mcon = con;
        this.recipeList = l;
    }
    @NonNull
    @NotNull
    @Override
    public RecipeView onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe,parent,false);
        return new RecipeView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecipeView holder, int position) {
        Recipe r = recipeList.get(position);
        holder.recipe_name.setText(r.getName());
        holder.recipe_view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcon,ViewRecipe.class);
                Bundle bundle = new Bundle();
                bundle.putString("instruction",r.getInstruction());
                bundle.putString("ingredients",r.getIngredients());
                bundle.putString("recipeName",r.getName());
                bundle.putInt("id",r.getId());
                bundle.putDouble("cookingTime", r.getCookingTime());
                intent.putExtras(bundle);
                mcon.startActivity(intent);
            }
        });
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
