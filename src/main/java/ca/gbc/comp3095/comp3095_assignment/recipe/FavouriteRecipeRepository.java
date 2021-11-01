package ca.gbc.comp3095.comp3095_assignment.recipe;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface FavouriteRecipeRepository extends Repository<FavouriteRecipe, Long> {

    void save(FavouriteRecipe recipe);
}
