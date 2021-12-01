package ca.gbc.comp3095.comp3095_assignment.services.repositories;

import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findByTitleIgnoreCase(String title);
    List<Recipe> findAllByOrderByDateCreatedDesc();
}
