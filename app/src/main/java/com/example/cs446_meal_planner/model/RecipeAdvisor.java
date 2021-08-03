package com.example.cs446_meal_planner.model;

import java.util.ArrayList;

public class RecipeAdvisor {
    private String []ingredients;
    private String feedbacks[];
    private final int FEEDBACK_TYPE_COUNT = 4;
    private final String [] FEEDBACK = {"too sweet", "too salty", "too sour", "too spicy", "not sweet enough", "not salty enough", "not sour enough", "not spicy enough"};
    private final String [] SWEET_INGREDIETNS={"cacao","fruit","sugar","cocoa","vanilla","cardamom","star anise","cloves","cinnamon","nutmeg","saffron","syrup","honey","jam"};
    private final String [] SOUR_INGREDIETNS={"buttermilk","sour cream","lemon","lime","vinegar"};
    private final String [] SALTY_INGREDIETNS={"salt","soy sauce","fish sauce"};
    private final String [] SPICY_INGREDIETNS={"pepper","harissa","red chili paste","sriracha","tabasco","wasabi","horseradish","mustard","ginger"};
    private final String[][] INGREDIETNS_LIST = new String[][]{SWEET_INGREDIETNS, SALTY_INGREDIETNS, SOUR_INGREDIETNS,SPICY_INGREDIETNS};
    public RecipeAdvisor(String[] ingredients, String[] feedbacks) {
        this.ingredients = ingredients;
        this.feedbacks = feedbacks;
    }
    private String form_advice(String ingredients)
    {
        if(ingredients.length() == 0)
        {
            return "";
        }
        String advice="";
        String []tmp = ingredients.split("#");
        if(tmp.length==1)
        {
            return tmp[0];
        }
        for(int i=0;i<tmp.length-2;i++)
        {
            advice+=tmp[i]+", ";
        }
        advice+=tmp[tmp.length-2]+" and "+tmp[tmp.length-1];
        return advice;
    }
    public String recommend()
    {
        if (feedbacks.length == 0 || feedbacks[0]=="")
        {
            return "We noticed that you did not enter any feedback. If you are not satisfied with the recipe, feel free to add any feedback in the Feedback section.";
        }
        String [] ingredient_in_advice = new String[]{"",""};
        boolean []feedback_type_check= new boolean[FEEDBACK_TYPE_COUNT*2];
        for(String S: feedbacks)
        {
            int cur_feedback = Integer.parseInt(S);
            if (feedback_type_check[cur_feedback])
            {
                continue;
            }
            feedback_type_check[cur_feedback] = true;
            for(String T: ingredients)
            {
                String[] ingredientGram = T.split("%");
                String ingredientName = ingredientGram[0];
                for(String I: INGREDIETNS_LIST[cur_feedback%FEEDBACK_TYPE_COUNT])
                {
                    if(ingredientName.toLowerCase().contains(I) || I.contains(ingredientName.toLowerCase()))
                    {
                        ingredient_in_advice[cur_feedback/FEEDBACK_TYPE_COUNT]+=ingredientName+"#";
                    }
                }
            }
        }
        if(ingredient_in_advice[0].length() == 0 && ingredient_in_advice[1].length() == 0)
        {
            return "We are not sure on how to make the recipe better.";
        }
        String advice="Based on the feedback, we recommend";
        if(ingredient_in_advice[0].length() !=0)
        {
            advice+=" decreasing the amount of "+form_advice(ingredient_in_advice[0]);
        }
        advice+=" in the recipe.";
        if(ingredient_in_advice[1].length()!=0)
        {
            advice+=" Also, we recommend increasing the amount of "+form_advice(ingredient_in_advice[1]);
        }
        advice += "\n Please note that our suggestions are merely suggestions and might not always be correct. Professional advice takes precedence over our suggestions";
        return advice;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(String[] feedbacks) {
        this.feedbacks = feedbacks;
    }
}
