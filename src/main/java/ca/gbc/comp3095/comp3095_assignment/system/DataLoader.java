package ca.gbc.comp3095.comp3095_assignment.system;

import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.recipe.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private RecipeRepository recipes;

    @Autowired
    public DataLoader(RecipeRepository recipes) {
        this.recipes = recipes;
    }

    public void run(ApplicationArguments args) {
        Recipe recipe = new Recipe();
        recipe.setTitle("Title");
        recipe.setIngredients("Ingredients");
        recipe.setInstructions("instructions");
        recipes.save(recipe);
        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Title2");
        recipe1.setIngredients("Ingredients");
        recipe1.setInstructions("instructions");
        recipes.save(recipe1);
    }
}
