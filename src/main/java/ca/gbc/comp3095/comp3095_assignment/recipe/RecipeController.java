/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: main controller, used to create recipes, ingredients, steps, meal plans
 */
package ca.gbc.comp3095.comp3095_assignment.recipe;

import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlan;
import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.services.*;
import ca.gbc.comp3095.comp3095_assignment.shoppinglist.ShoppingList;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class RecipeController {


    private final RecipeService recipes;
    private final StepService steps;
    private final IngredientService ingredients;
    private final UserService users;
    private final FavouriteRecipeService favourites;
    private final MealPlanService mealPlans;
    private final ShoppingListService shoppingLists;

    @Autowired
    public RecipeController(RecipeService recipes, StepService steps, IngredientService ingredients, UserService users, FavouriteRecipeService favourites, MealPlanService mealPlans, ShoppingListService shoppingLists) {
        this.recipes = recipes;
        this.steps = steps;
        this.ingredients = ingredients;
        this.users = users;
        this.favourites = favourites;
        this.mealPlans = mealPlans;
        this.shoppingLists = shoppingLists;
    }

    @RequestMapping({"/recipes", "/"})
    public String findAll(Model model, String search, Principal principal) {
        if(search != null) {
            model.addAttribute("recipes", this.recipes.findByTitleIgnoreCase(search));
        } else {
            model.addAttribute("recipes", this.recipes.findAllByOrderByDateCreated());
        }
        model.addAttribute("username", principal.getName());
        return "recipe/recipeList";
    }

    @GetMapping("/recipes/new")
    public String initCreateRecipe(Model model) {
        Recipe recipe = new Recipe();

        model.addAttribute("recipe", recipe);
        return "recipe/recipeCreate";
    }

    @PostMapping("/recipes/new")
    public String processCreateRecipe(Recipe recipe, BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            return "recipe/recipeCreate";
        } else {
            User user = users.findByUsername(principal.getName());
            recipe.setUser(user);
            this.recipes.save(recipe);
            return "redirect:/recipes";
        }
    }

    @RequestMapping("/recipes/{recipeId}")
    public String viewRecipe(@PathVariable("recipeId") Long recipeId, Model model, Principal principal) {
        Recipe recipe = this.recipes.findById(recipeId);
        ShoppingList list = this.shoppingLists.findByUser(principal.getName());
        User user = this.users.findByUsername(principal.getName());
        Boolean isOwner = false;
        Boolean isFavourite = false;
        if(principal.getName().equals(recipe.getUser().getUsername())) {
            isOwner = true;
        }

        // make sure the user hasn't favourited the recipe already
        for (FavouriteRecipe fav : user.getFavourites()) {
            if(fav.getRecipe().equals(recipe)) {
                isFavourite = true;
                break;
            }
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("step", new Step());
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("steps", this.steps.findByRecipeIdOrderByStepNumber(recipeId));
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isFavourite", isFavourite);
        model.addAttribute("list", list);
        return "recipe/recipeView";
    }

    @RequestMapping("/recipes/{recipeId}/edit")
    public String initEditRecipe(@PathVariable("recipeId") Long recipeId, Model model, Principal principal) {
        Recipe recipe = this.recipes.findById(recipeId);
        if(principal.getName().equals(recipe.getUser().getUsername())) {
            model.addAttribute("recipe", recipe);
            model.addAttribute("step", new Step());
            model.addAttribute("ingredient", new Ingredient());
            model.addAttribute("steps", this.steps.findByRecipeIdOrderByStepNumber(recipeId));
            return "recipe/recipeEdit";
        }
        return String.format("redirect:/recipes/%d", recipeId);
    }

    // move to other controller maybe
    @PostMapping("/recipes/new/step")
    public String processAddStep(Long recipeId, Step step, BindingResult result) {
        Recipe recipe = this.recipes.findById(recipeId);
        List<Step> steps = this.steps.findByRecipeIdOrderByStepNumber(recipeId);
        if(steps.size() != 0) {
            int stepNumber = steps.get(steps.size() - 1).getStepNumber();
            step.setStepNumber(stepNumber + 1);
        }
        else {
            step.setStepNumber(1);
        }
        if(!result.hasErrors() && recipe != null) {
            step.setRecipe(this.recipes.findById(recipeId));
            this.steps.save(step);
        }
        return String.format("redirect:/recipes/%d/edit", recipeId);
    }

    @RequestMapping("favourite/{recipeId}")
    public String addFavourite(@PathVariable("recipeId") Long recipeId, Principal principal) {
        Recipe recipe = this.recipes.findById(recipeId);
        User user = this.users.findByUsername(principal.getName());

        // owners aren't allow to favourite their own recipes
        if(recipe.getUser().getUsername().equals(user.getUsername())) {
            return String.format("redirect:/recipes/%d", recipeId);
        }

        // make sure the user hasn't favourited the recipe already
        for (FavouriteRecipe fav : user.getFavourites()) {
            if(fav.getRecipe().equals(recipe)) {
                return String.format("redirect:/recipes/%d", recipeId);
            }
        }
        FavouriteRecipe favouriteRecipe = new FavouriteRecipe();
        favouriteRecipe.setRecipe(recipe);
        favouriteRecipe.setUser(user);
        favourites.save(favouriteRecipe);
        return String.format("redirect:/recipes/%d", recipeId);
    }

    @GetMapping("plan/{recipeId}")
    public String initCreateMealPlan(@PathVariable("recipeId") Long recipeId, Model model, Principal principal) {
        User user = this.users.findByUsername(principal.getName());
        MealPlan mealPlan = new MealPlan();
        model.addAttribute("mealPlan", mealPlan);
        model.addAttribute("recipeId", recipeId.longValue());
        model.addAttribute("userId", user.getId());
        return "mealPlan/mealPlanCreate";
    }

    @PostMapping("recipes/plan")
    public String processCreateMealPlan(Long recipeId, MealPlan mealPlan, Principal principal) {
        Recipe recipe = this.recipes.findById(recipeId);
        User user = this.users.findByUsername(principal.getName());
        mealPlan.setRecipe(recipe);
        mealPlan.setUser(user);
        this.mealPlans.save(mealPlan);

        return "redirect:/profile";
    }

    @GetMapping("/myrecipes")
    public String viewUserRecipes(Principal principal, Model model) {
        User user = users.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "recipe/recipeUser";
    }
}
