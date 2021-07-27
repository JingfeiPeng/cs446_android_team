package com.example.cs446_meal_planner.model;
import java.util.Dictionary;
import java.util.Hashtable;

public class IngredientCaloriesCalculator {
    private Hashtable<String, Ingredient> energy_table;

    public IngredientCaloriesCalculator() {
        energy_table = new Hashtable<String, Ingredient>();
        // construct some common ingredients
        Ingredient sugar = new Ingredient("sugar", 1, Unit.TABLESPOON, 19);
        Ingredient shrimp = new Ingredient("shrimp", 100, Unit.GRAM, 99);
        Ingredient buttermilk = new Ingredient("buttermilk",100, Unit.GRAM, 40);
        Ingredient hotsauce = new Ingredient("hotsauce", 100, Unit.GRAM, 11);
        Ingredient salt = new Ingredient("salt", 1, Unit.GRAM, 0);
        Ingredient flour = new Ingredient("flour", 100, Unit.GRAM, 364);
        Ingredient cornmeal = new Ingredient("cornmeal", 100, Unit.GRAM, 370 );
        Ingredient pepper = new Ingredient("pepper", 100, Unit.GRAM, 40 );
        Ingredient oregano = new Ingredient("oregano", 1, Unit.TEASPOON, 5);
        Ingredient thyme = new Ingredient("thyme", 1, Unit.TEASPOON, 4);
        Ingredient garlic_powder = new Ingredient("garlic_powder", 1, Unit.TEASPOON, 10);
        Ingredient black_pepper = new Ingredient("black_pepper", 1, Unit.TEASPOON, 6 );
        Ingredient oil = new Ingredient("oil", 100, Unit.GRAM, 884);
        Ingredient bread = new Ingredient("bread", 100, Unit.GRAM, 265);
        Ingredient tomato = new Ingredient("tomato", 100, Unit.GRAM, 18 );
        Ingredient lettuce = new Ingredient("lettuce", 100, Unit.GRAM, 15 );
        Ingredient mayonnaise = new Ingredient("mayonnaise", 100, Unit.GRAM, 680);
        Ingredient cucumber = new Ingredient("cucumber", 1, Unit.CUP, 16);
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

    public Integer calculateCalories(String ingredientName, String amount, String unit) {
        Ingredient ingredient = energy_table.get(ingredientName);
        if (ingredient == null) {
            return 1; //Double.parseDouble(amount)/10;
        }
        // we have the ingredient, do logic of calculating ingredient calories value
        return 0;
    }
}
