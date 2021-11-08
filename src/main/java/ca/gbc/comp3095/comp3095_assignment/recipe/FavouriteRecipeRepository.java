/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: Repo for favourite recipes, only use is to save a favourite recipe to the database
 */
package ca.gbc.comp3095.comp3095_assignment.recipe;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface FavouriteRecipeRepository extends Repository<FavouriteRecipe, Long> {

    void save(FavouriteRecipe recipe);
}
