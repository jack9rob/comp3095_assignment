package ca.gbc.comp3095.comp3095_assignment.services;

import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeService extends CrudService<Recipe, Long> {
    Recipe findByTitleIgnoreCase(String title);
    Set<Recipe> findAllByOrderByDateCreated();
    // maybe add one to where it uses LIKE SQL (like original in assignment 1)
}
