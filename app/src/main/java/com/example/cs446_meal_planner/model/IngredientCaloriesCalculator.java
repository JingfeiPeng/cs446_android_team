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
        Ingredient beef = new Ingredient("beef", 100.0, Unit.GRAM, 250.5);
        Ingredient potato = new Ingredient("potato", 100.0, Unit.GRAM, 76.7);
        Ingredient pork = new Ingredient("pork", 100.0, Unit.GRAM, 242.1);
        Ingredient duck = new Ingredient("duck", 100.0, Unit.GRAM, 337.0);
        Ingredient chicken = new Ingredient("chicken", 100.0, Unit.GRAM, 239.0);
        Ingredient egg = new Ingredient("egg", 100.0, Unit.GRAM, 155.1);
        energy_table.put("egg", egg);
        energy_table.put("chicken", chicken);
        energy_table.put("duck", duck);
        energy_table.put("pork", pork);
        energy_table.put("potato", potato);
        energy_table.put("beef", beef);
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

    public Double unit_conversion(Unit from, Unit to, Double curr_amount) {
        if (from == to) {
            return curr_amount;
        } else {
            if (from == Unit.CUP) {
                if (to == Unit.TEASPOON) {
                    return curr_amount * 48.0;
                } else if (to == Unit.TABLESPOON) {
                    return curr_amount * 16.0;
                } else if (to == Unit.WHOLE) {
                    return curr_amount/2.0;
                } else if (to == Unit.GRAM) {
                    return curr_amount * 236.6;
                } else if (to == Unit.POUND) {
                    return curr_amount * 0.52;
                }
            } else if (from == Unit.TABLESPOON) {
                if (to == Unit.TEASPOON) {
                    return curr_amount * 3.0;
                } else if (to == Unit.CUP) {
                    return curr_amount / 16.0;
                } else if (to == Unit.WHOLE) {
                    return curr_amount/96.0;
                } else if (to == Unit.GRAM) {
                    return curr_amount * 14.8;
                } else if (to == Unit.POUND) {
                    return curr_amount * 0.033;
                }
            } else if (from == Unit.TEASPOON) {
                if (to == Unit.CUP) {
                    return curr_amount / 48.0;
                } else if (to == Unit.TABLESPOON) {
                    return curr_amount / 3.0;
                } else if (to == Unit.WHOLE) {
                    return curr_amount/32.0;
                } else if (to == Unit.GRAM) {
                    return curr_amount * 4.9;
                } else if (to == Unit.POUND) {
                    return curr_amount * 0.011;
                }
            } else if (from == Unit.WHOLE) {
                if (to == Unit.TEASPOON) {
                    return curr_amount * 96.0;
                } else if (to == Unit.TABLESPOON) {
                    return curr_amount * 32.0;
                } else if (to == Unit.CUP) {
                    return curr_amount * 2.0;
                } else if (to == Unit.GRAM) {
                    return curr_amount * 236.6 * 2;
                } else if (to == Unit.POUND) {
                    return curr_amount * 0.52 * 2;
                }
            } else if (from == Unit.GRAM) {
                if (to == Unit.TEASPOON) {
                    return curr_amount * 0.2029;
                } else if (to == Unit.TABLESPOON) {
                    return curr_amount * 0.6763;
                } else if (to == Unit.WHOLE) {
                    return curr_amount/(236.6 * 2);
                } else if (to == Unit.CUP) {
                    return curr_amount / 236.6;
                } else if (to == Unit.POUND) {
                    return curr_amount / 0.52;
                }
            } else if (from == Unit.POUND) {
                if (to == Unit.TEASPOON) {
                    return curr_amount * 92.03;
                } else if (to == Unit.TABLESPOON) {
                    return curr_amount * 30.68;
                } else if (to == Unit.WHOLE) {
                    return curr_amount * (1.92/2.0);
                } else if (to == Unit.GRAM) {
                    return curr_amount * 453.59;
                } else if (to == Unit.CUP) {
                    return curr_amount * 1.92;
                }
            }
        }
        return curr_amount;
    }

    public Double calculateCalories(String ingredientName, String amount, String unit) {
        Ingredient ingredient = null;
        String[] possible_ingredients = ingredientName.split(" ");
        for (int i = possible_ingredients.length - 1; i >= 0; i--){
            ingredient = energy_table.get(possible_ingredients[i]);
            if (ingredient != null) {
                break;
            }
        }

        Unit u;
        if (unit.toLowerCase().equals("cup")) {
            u = Unit.CUP;
        } else if (unit.toLowerCase().equals("teaspoon")) {
            u = Unit.TEASPOON;
        } else if (unit.toLowerCase().equals("tablespoon")) {
            u = Unit.TABLESPOON;
        } else if (unit.toLowerCase().equals("whole")) {
            u = Unit.WHOLE;
        } else if (unit.toLowerCase().equals("pound")) {
            u = Unit.POUND;
        } else {
            u = Unit.GRAM; // default
        }

        if (ingredient == null) {
            Double new_amount = unit_conversion(u, Unit.GRAM, Double.parseDouble(amount));
            return new_amount/10; //Double.parseDouble(amount)/10;
        }

        Unit ingredient_default = ingredient.getUnit();
        Double new_amount = unit_conversion(u, ingredient_default, Double.parseDouble(amount));
        // we have the ingredient, do logic of calculating ingredient calories value
        return (new_amount/ingredient.getAmount())*ingredient.getCalorie();
    }
}
