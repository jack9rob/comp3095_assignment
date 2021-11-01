package ca.gbc.comp3095.comp3095_assignment.recipe.ingredient;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface IngredientRepository extends Repository<Ingredient, Long> {

    void save(Ingredient ingredient);
}
