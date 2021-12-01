/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: Loads dummy data to the database on startup. Data is cleared each time the app is closed
 */
package ca.gbc.comp3095.comp3095_assignment.system;

import ca.gbc.comp3095.comp3095_assignment.event.Event;
import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlan;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.services.*;
import ca.gbc.comp3095.comp3095_assignment.shoppinglist.ShoppingList;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataLoader implements ApplicationRunner {

    private final RecipeService recipes;
    private final MealPlanService mealPlans;
    private final StepService steps;
    private final UserService userRepository;
    private final ShoppingListService shoppingRepository;
    private final IngredientService ingredientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EventService events;

    @Autowired
    public DataLoader(RecipeService recipes, MealPlanService mealPlans, StepService steps, UserService userRepository, ShoppingListService shoppingRepository, IngredientService ingredientRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EventService events) {
        this.recipes = recipes;
        this.mealPlans = mealPlans;
        this.steps = steps;
        this.userRepository = userRepository;
        this.shoppingRepository = shoppingRepository;
        this.ingredientRepository = ingredientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.events = events;
    }

    public void run(ApplicationArguments args) {
        // users
        User user = new User();
        user.setUsername("user");
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        userRepository.save(user);

        User user1 = new User();
        user1.setUsername("jack");
        user1.setPassword(bCryptPasswordEncoder.encode("password"));
        userRepository.save(user1);

        // recipes
        Recipe recipe = new Recipe();
        recipe.setTitle("Title");
        recipe.setDescription("My First Recipe");
        recipe.setUser(user);
        recipes.save(recipe);

        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Title2");
        recipe1.setDescription("My Second Recipe");
        recipe1.setUser(user1);
        recipes.save(recipe1);

        // meal plans
        MealPlan mealPlan = new MealPlan();
        mealPlan.setDate(new Date());
        mealPlan.setRecipe(recipe);
        mealPlan.setUser(user);
        mealPlans.save(mealPlan);

        MealPlan mealPlan1 = new MealPlan();
        mealPlan1.setDate(new Date());
        mealPlan1.setRecipe(recipe);
        mealPlan1.setUser(user);
        mealPlans.save(mealPlan1);

        // steps
        Step step = new Step();
        step.setStepDescription("New Step");
        step.setRecipe(recipe);
        step.setStepNumber(1);
        steps.save(step);

        // shopping list
        Ingredient ing1 = new Ingredient();
        ing1.setAmount("Test");
        ing1.setName("TEst");
        ShoppingList list = new ShoppingList();
        list.setUser(user);
        list.getIngredients().add(ing1);
        shoppingRepository.save(list);

        // event
        Event event = new Event();
        event.setTitle("First Event");
        event.setDescription("Description of event");
        event.setDate(new Date());
        event.setUser(user);
        events.save(event);
    }
}
