package ca.gbc.comp3095.comp3095_assignment.services.repositories;

import ca.gbc.comp3095.comp3095_assignment.recipe.FavouriteRecipe;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface FavouriteRecipeRepository extends CrudRepository<FavouriteRecipe, Long> {
}
