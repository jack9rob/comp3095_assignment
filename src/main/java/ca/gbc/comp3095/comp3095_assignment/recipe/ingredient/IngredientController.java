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
import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class IngredientController {

    private final IngredientService ingredients;
    private final RecipeService recipes;
    private final UserService users;

    public IngredientController(IngredientService ingredients, RecipeService recipes, UserService users) {
        this.ingredients = ingredients;
        this.recipes = recipes;
        this.users = users;
    }

    @PostMapping("/ingredient/new")
    public String createIngredient(Long recipeId, Ingredient ingredient, BindingResult result) {
        if(!result.hasErrors()) {
            ingredient.setRecipe(this.recipes.findById(recipeId));
            this.ingredients.save(ingredient);
        }
        return String.format("redirect:/recipes/%d/edit", recipeId);
    }

    @GetMapping("ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable("id") Long ingredientId, Principal principal) {
        Ingredient ingredient = ingredients.findById(ingredientId);
        User user = users.findByUsername(principal.getName());
        if(ingredient != null && ingredient.getRecipe().getUser().getUsername().equals(user.getUsername())) {
            ingredients.delete(ingredient);
        }
        return String.format("redirect:/recipes/%d/edit", ingredient.getRecipe().getId());
    }
}
