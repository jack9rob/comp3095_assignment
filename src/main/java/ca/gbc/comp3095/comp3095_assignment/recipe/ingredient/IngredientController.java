/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: Used to create a new ingredient for a recipe
 */
package ca.gbc.comp3095.comp3095_assignment.recipe.ingredient;

import ca.gbc.comp3095.comp3095_assignment.services.IngredientService;
import ca.gbc.comp3095.comp3095_assignment.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private final IngredientService ingredients;

    private final RecipeService recipes;

    public IngredientController(IngredientService ingredients, RecipeService recipes) {
        this.ingredients = ingredients;
        this.recipes = recipes;
    }

    @PostMapping("/ingredient/new")
    public String createIngredient(Long recipeId, Ingredient ingredient, BindingResult result) {
        if(!result.hasErrors()) {
            ingredient.setRecipe(this.recipes.findById(recipeId));
            this.ingredients.save(ingredient);
        }
        return String.format("redirect:/recipes/%d", recipeId);
    }
}
