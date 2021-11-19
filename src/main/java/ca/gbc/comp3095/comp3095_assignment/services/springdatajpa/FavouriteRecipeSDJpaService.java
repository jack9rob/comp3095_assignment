package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;

import ca.gbc.comp3095.comp3095_assignment.recipe.FavouriteRecipe;
import ca.gbc.comp3095.comp3095_assignment.services.FavouriteRecipeService;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.FavouriteRecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FavouriteRecipeSDJpaService implements FavouriteRecipeService {

    private final FavouriteRecipeRepository favouriteRecipeRepository;

    public FavouriteRecipeSDJpaService(FavouriteRecipeRepository favouriteRecipeRepository) {
        this.favouriteRecipeRepository = favouriteRecipeRepository;
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
