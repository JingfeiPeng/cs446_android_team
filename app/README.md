#Using DB
1. declare db using db = new DBHelper(getActivity())
2. insert: db.insertRecipe(Recipe recipe)
3. delete: db.deleteRecipe(Integer id)
4. retrieve: db.getAllRecipes() 
5. get total number of recipes: db.numberOfRows()

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
