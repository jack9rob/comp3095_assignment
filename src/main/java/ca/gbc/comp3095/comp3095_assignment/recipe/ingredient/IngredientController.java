/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: Used to create a new ingredient for a recipe
 */
package ca.gbc.comp3095.comp3095_assignment.recipe.ingredient;

import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.recipe.RecipeRepository;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    private final IngredientRepository ingredients;

    private final RecipeRepository recipes;

    public IngredientController(IngredientRepository ingredients, RecipeRepository recipes) {
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
