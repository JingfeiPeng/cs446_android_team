package com.example.cs446_meal_planner.model;

import java.util.ArrayList;

public class WarningMessageGenerator {
    private String []SINGLE_INGREDIENTS={"peanut"};
    private String [][]MULTI_INGREDIENTS={{"crabs","vitamin c"}};
    private ArrayList<String> ingredients;

    public WarningMessageGenerator() {
        this.ingredients = new ArrayList();
    }
    public void add_ingredients(String ingredients)
    {
        this.ingredients.add(ingredients);
    }
    public void clear_ingredients()
    {
        this.ingredients.clear();
    }
    public String warning_msg()
    {
        String tmp = "";
        boolean hasWarning = false;
        for(String I : ingredients)
        {
            for(String J: SINGLE_INGREDIENTS)
            {
                if(J.toLowerCase().contains(I) || I.contains(J.toLowerCase()))
                {
                    hasWarning = true;
                    tmp+="This recipe contains "+J+". ";
                }
            }
        }
        for(int i =0 ;i < MULTI_INGREDIENTS.length;i++)
        {
            boolean []tmp_array = new boolean[MULTI_INGREDIENTS[i].length];
            for(String I: ingredients)
            {
                for(int j=0;j<MULTI_INGREDIENTS[i].length;j++)
                {
                    String cur_ingredients = MULTI_INGREDIENTS[i][j];
                    if(cur_ingredients.toLowerCase().contains(I) || I.contains(cur_ingredients.toLowerCase()))
                    {
                        tmp_array[j]=true;
                    }
                }
            }
            boolean if_multi_ingredient_match = true;
            for(int j=0;j<MULTI_INGREDIENTS[i].length;j++)
            {
                if_multi_ingredient_match &=tmp_array[j];
            }
            if(if_multi_ingredient_match)
            {
                hasWarning = true;
                tmp+="This recipe contains multiple ingredients that can cause potential harmful effects.";
            }
        }
        if(hasWarning)
        {
            return "WARNING: "+tmp;
        }
        return "";
    }
}
