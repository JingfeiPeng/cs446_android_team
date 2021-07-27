package com.example.cs446_meal_planner.model;
import java.util.Dictionary;
import java.util.Hashtable;

public class IngredientCaloriesCalculator {
    private Hashtable<String, Ingredient> energy_table;
    private static IngredientCaloriesCalculator calculator = new IngredientCaloriesCalculator();

    private IngredientCaloriesCalculator() {
        energy_table = new Hashtable<String, Ingredient>();
        // construct some common ingredients
        Ingredient sugar = new Ingredient("sugar", 1.0, Unit.TABLESPOON, 19.0);
        Ingredient shrimp = new Ingredient("shrimp", 100.0, Unit.GRAM, 99.0);
        Ingredient buttermilk = new Ingredient("buttermilk",100.0, Unit.GRAM, 40.0);
        Ingredient hotsauce = new Ingredient("hotsauce", 100.0, Unit.GRAM, 11.0);
        Ingredient salt = new Ingredient("salt", 1.0, Unit.GRAM, 0.0);
        Ingredient flour = new Ingredient("flour", 100.0, Unit.GRAM, 364.0);
        Ingredient cornmeal = new Ingredient("cornmeal", 100.0, Unit.GRAM, 370.0 );
        Ingredient pepper = new Ingredient("pepper", 100.0, Unit.GRAM, 40.0 );
        Ingredient oregano = new Ingredient("oregano", 1.0, Unit.TEASPOON, 5.0);
        Ingredient thyme = new Ingredient("thyme", 1.0, Unit.TEASPOON, 4.0);
        Ingredient garlic_powder = new Ingredient("garlic_powder", 1.0, Unit.TEASPOON, 10.0);
        Ingredient black_pepper = new Ingredient("black_pepper", 1.0, Unit.TEASPOON, 6.0 );
        Ingredient oil = new Ingredient("oil", 100.0, Unit.GRAM, 884.0);
        Ingredient bread = new Ingredient("bread", 100.0, Unit.GRAM, 265.0);
        Ingredient tomato = new Ingredient("tomato", 100.0, Unit.GRAM, 18.0);
        Ingredient lettuce = new Ingredient("lettuce", 100.0, Unit.GRAM, 15.0);
        Ingredient mayonnaise = new Ingredient("mayonnaise", 100.0, Unit.GRAM, 680.0);
        Ingredient cucumber = new Ingredient("cucumber", 1.0, Unit.CUP, 16.0);
        energy_table.put("sugar", sugar);
        energy_table.put("shrimp", shrimp);
        energy_table.put("buttermilk", buttermilk);
        energy_table.put("hotsauce", hotsauce);
        energy_table.put("salt", salt);
        energy_table.put("flour", flour);
        energy_table.put("cornmeal", cornmeal);
        energy_table.put("pepper", pepper);
        energy_table.put("oregano", oregano);
        energy_table.put("thyme", thyme);
        energy_table.put("garlic powder", garlic_powder);
        energy_table.put("black pepper", black_pepper);
        energy_table.put("oil", oil);
        energy_table.put("bread", bread);
        energy_table.put("tomato", tomato);
        energy_table.put("lettuce", lettuce);
        energy_table.put("mayonnaise", mayonnaise);
        energy_table.put("cucumber", cucumber);
    }

    public static IngredientCaloriesCalculator getInstance() {
        return calculator;
    }

    public Double calculateCalories(String ingredientName, String amount, String unit) {
        Ingredient ingredient = energy_table.get(ingredientName);
        if (ingredient == null) {
            return 1.0; //Double.parseDouble(amount)/10;
        }
        // we have the ingredient, do logic of calculating ingredient calories value
        return 0.0;
    }
}
