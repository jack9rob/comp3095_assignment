/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: Repo for ingredients, only used for saving ingredients
 */
package ca.gbc.comp3095.comp3095_assignment.recipe.ingredient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    Ingredient save(Ingredient ingredient);
}
