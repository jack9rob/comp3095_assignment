package ca.gbc.comp3095.comp3095_assignment.services.repositories;

import ca.gbc.comp3095.comp3095_assignment.recipe.FavouriteRecipe;
import org.springframework.data.repository.CrudRepository;

public interface FavouriteRecipeRepository extends CrudRepository<FavouriteRecipe, Long> {
}
