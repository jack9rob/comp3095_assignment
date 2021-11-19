package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;

import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.services.IngredientService;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IngredientSDJpaService implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientSDJpaService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Set<Ingredient> findAll() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        return ingredients;
    }

    @Override
    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    @Override
    public Ingredient save(Ingredient object) {
        return ingredientRepository.save(object);
    }

    @Override
    public void delete(Ingredient object) {
        ingredientRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        ingredientRepository.deleteById(id);
    }
}
