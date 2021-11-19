package ca.gbc.comp3095.comp3095_assignment.services;

import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;

public interface RecipeService extends CrudService<Recipe, Long> {
    Recipe findByTitleIgnoreCase(String title);

    // maybe add one to where it uses LIKE SQL (like original in assignment 1)
}
