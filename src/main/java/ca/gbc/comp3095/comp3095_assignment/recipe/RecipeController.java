/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: main controller, used to create recipes, ingredients, steps, meal plans
 */
package ca.gbc.comp3095.comp3095_assignment.recipe;

import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlan;
import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlanRepository;
import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.IngredientRepository;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.StepRepository;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import ca.gbc.comp3095.comp3095_assignment.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class RecipeController {


    private final RecipeRepository recipes;
    private final StepRepository steps;
    private final IngredientRepository ingredients;
    private final UserRepository users;
    private final FavouriteRecipeRepository favourites;
    private final MealPlanRepository mealPlans;

    @Autowired
    public RecipeController(RecipeRepository recipes, StepRepository steps, IngredientRepository ingredients, UserRepository users, FavouriteRecipeRepository favourites, MealPlanRepository mealPlans) {
        this.recipes = recipes;
        this.steps = steps;
        this.ingredients = ingredients;
        this.users = users;
        this.favourites = favourites;
        this.mealPlans = mealPlans;
    }

    @RequestMapping("/recipes")
    public String findAll(Model model, String search) {
        if(search != null) {
            model.addAttribute("recipes", this.recipes.searchByTitle(search));
        } else {
            model.addAttribute("recipes", this.recipes.getAll());
        }
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
        Boolean isOwner = false;
        if(principal.getName().equals(recipe.getUser().getUsername())) {
            isOwner = true;
        }
        model.addAttribute("recipe", recipe);
        model.addAttribute("step", new Step());
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("steps", this.steps.getStepOrderStepNumber(recipeId));
        model.addAttribute("isOwner", isOwner);
        return "recipe/recipeView";
    }

    // move to other controller maybe
    @PostMapping("/recipes/new/step")
    public String processAddStep(Long recipeId, Step step, BindingResult result) {
        Recipe recipe = this.recipes.findById(recipeId);
        List<Step> steps = this.steps.getStepOrderStepNumber(recipeId);
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
        return String.format("redirect:/recipes/%d", recipeId);
    }

    @RequestMapping("favourite/{recipeId}")
    public String addFavourite(@PathVariable("recipeId") Long recipeId, Principal principal) {
        Recipe recipe = this.recipes.findById(recipeId);
        User user = this.users.findByUsername(principal.getName());
        if(!user.getFavourites().contains(recipe) && recipe.getUser().getId() != user.getId()) {
            FavouriteRecipe favouriteRecipe = new FavouriteRecipe();
            favouriteRecipe.setRecipe(recipe);
            favouriteRecipe.setUser(user);
            this.favourites.save(favouriteRecipe);
        }
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
}
