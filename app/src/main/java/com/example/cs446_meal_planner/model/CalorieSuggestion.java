package com.example.cs446_meal_planner.model;

public class CalorieSuggestion {

    public Integer CalculateSuggestion(String gender, Integer age,String activity) {
        Integer result;
        if (activity.equals("sedentary")) {
            if (age < 4) {
                result = 1000;
            } else if (4 <= age && age < 9) {
                if (gender.equals("female")) {
                    result = 1200;
                } else {
                    result = 1400;
                }
            } else if (9 <= age && age < 14) {
                if (gender.equals("female")) {
                    result = 1600;
                } else {
                    result = 1800;
                }
            } else if (14 <= age && age < 19) {
                if (gender.equals("female")) {
                    result = 1800;
                } else {
                    result = 2200;
                }
            } else if (19 <= age && age < 31) {
                if (gender.equals("female")) {
                    result = 2000;
                } else {
                    result = 2400;
                }
            } else if (31 <= age && age < 51) {
                if (gender.equals("female")) {
                    result = 1800;
                } else {
                    result = 2200;
                }
            } else if (age >= 51) {
                if (gender.equals("female")) {
                    result = 1600;
                } else {
                    result = 2000;
                }
            } else {
                result = 2000;
            }
        } else if (activity.equals("moderately")) {
            if (age < 4) {
                result = 1250;
            } else if (4 <= age && age < 9) {
                if (gender.equals("female")) {
                    result = 1500;
                } else {
                    result = 1500;
                }
            } else if (9 <= age && age < 14) {
                if (gender.equals("female")) {
                    result = 1800;
                } else {
                    result = 2000;
                }
            } else if (14 <= age && age < 19) {
                if (gender.equals("female")) {
                    result = 2000;
                } else {
                    result = 2600;
                }
            } else if (19 <= age && age < 31) {
                if (gender.equals("female")) {
                    result = 2100;
                } else {
                    result = 2700;
                }
            } else if (31 <= age && age < 51) {
                if (gender.equals("female")) {
                    result = 2000;
                } else {
                    result = 2500;
                }
            } else if (age >= 51) {
                if (gender.equals("female")) {
                    result = 1800;
                } else {
                    result = 2300;
                }
            } else {
                result = 2200;
            }
        } else {
            if (age < 4) {
                result = 1250;
            } else if (4 <= age && age < 9) {
                if (gender.equals("female")) {
                    result = 1600;
                } else {
                    result = 1800;
                }
            } else if (9 <= age && age < 14) {
                if (gender.equals("female")) {
                    result = 2000;
                } else {
                    result = 2300;
                }
            } else if (14 <= age && age < 19) {
                if (gender.equals("female")) {
                    result = 2400;
                } else {
                    result = 3000;
                }
            } else if (19 <= age && age < 31) {
                if (gender.equals("female")) {
                    result = 2400;
                } else {
                    result = 3000;
                }
            } else if (31 <= age && age < 51) {
                if (gender.equals("female")) {
                    result = 2200;
                } else {
                    result = 2900;
                }
            } else if (age >= 51) {
                if (gender.equals("female")) {
                    result = 2100;
                } else {
                    result = 2600;
                }
            } else {
                result = 2400;
            }
        }
        return result;
    }
}
