package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;

import ca.gbc.comp3095.comp3095_assignment.recipe.FavouriteRecipe;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.services.FavouriteRecipeService;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.FavouriteRecipeRepository;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.RecipeRepository;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.UserRepository;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FavouriteRecipeSDJpaService implements FavouriteRecipeService {

    private final FavouriteRecipeRepository favouriteRecipeRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public FavouriteRecipeSDJpaService(FavouriteRecipeRepository favouriteRecipeRepository, UserRepository userRepository, RecipeRepository recipeRepository) {
        this.favouriteRecipeRepository = favouriteRecipeRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<FavouriteRecipe> findAll() {
        Set<FavouriteRecipe> favourites = new HashSet<>();
        favouriteRecipeRepository.findAll().forEach(favourites::add);
        return favourites;
    }

    @Override
    public FavouriteRecipe findById(Long id) {
        return favouriteRecipeRepository.findById(id).orElse(null);
    }

    @Override
    public FavouriteRecipe save(FavouriteRecipe object) {
        return favouriteRecipeRepository.save(object);
    }

    @Override
    public void delete(FavouriteRecipe object) {
        favouriteRecipeRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        favouriteRecipeRepository.deleteById(id);
    }
}
