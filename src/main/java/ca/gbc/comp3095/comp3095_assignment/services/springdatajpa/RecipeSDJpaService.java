package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;


import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.services.RecipeService;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeSDJpaService implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeSDJpaService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> findAll() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public Recipe save(Recipe object) {
        object.setDateCreated(new Date());
        return recipeRepository.save(object);
    }

    @Override
    public void delete(Recipe object) {
        recipeRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe findByTitleIgnoreCase(String title) {
        return recipeRepository.findByTitleIgnoreCase(title);
    }

    @Override
    public Set<Recipe> findAllByOrderByDateCreated() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAllByOrderByDateCreatedDesc().forEach(recipes::add);
        return recipes;
    }
}
