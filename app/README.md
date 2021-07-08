#Using DB
1. declare db using db = new DBHelper(getActivity())
2. insert: db.insertRecipe(Recipe recipe)
3. delete: db.deleteRecipe(Integer id)
4. retrieve: db.getAllRecipes() 
5. get total number of recipes: db.numberOfRows()
6. delete all recipes: db.deleteAllRecipes()
7. update name: db.updateName(String name, Integer id)
8. update image_url: db.updateImageUrl(String imageUrl, Integer id)
9. update instruction: db.updateInstruction(String instruction, Integer id)
10. update cooking time: db.updateCookingTime(Double cookingTime, Integer id)
11. update ingredients: db.updateIngredients(String ingredients, Integer id)

#sample code 
Recipe recipe = Recipe.builder()
                .name("name1")
                .ingredients("ingredients1")
                .instruction("instruction1")
                .cookingTime(55)
                .imageUrl("xxxx").build();
        
db = new DBHelper(getActivity());
db.insertRecipe(recipe);
ArrayList<Recipe> recipes = db.getAllRecipes();

final TextView textView = binding.textViewPlan;
homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {

@Override
public void onChanged(@Nullable String s) {
    textView.setText(recipes.get(0).toString());
    }
