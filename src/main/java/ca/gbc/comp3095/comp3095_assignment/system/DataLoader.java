package ca.gbc.comp3095.comp3095_assignment.system;

import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlan;
import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlanRepository;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.recipe.RecipeRepository;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataLoader implements ApplicationRunner {

    private final RecipeRepository recipes;
    private final MealPlanRepository mealPlans;
    private final StepRepository steps;

    @Autowired
    public DataLoader(RecipeRepository recipes, MealPlanRepository mealPlans, StepRepository steps) {
        this.recipes = recipes;
        this.mealPlans = mealPlans;
        this.steps = steps;
    }

    public void run(ApplicationArguments args) {
        // recipes
        Recipe recipe = new Recipe();
        recipe.setTitle("Title");
        recipe.setDescription("My First Recipe");
        recipes.save(recipe);
        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Title2");
        recipe1.setDescription("My Second Recipe");
        recipes.save(recipe1);

        // meal plans
        MealPlan mealPlan = new MealPlan();
        mealPlan.setDate(new Date());
        mealPlan.setRecipe(recipe);
        mealPlans.save(mealPlan);

        MealPlan mealPlan1 = new MealPlan();
        mealPlan1.setDate(new Date());
        mealPlan1.setRecipe(recipe);
        mealPlans.save(mealPlan1);

        // steps
        Step step = new Step();
        step.setStepDescription("New Step");
        step.setRecipe(recipe);
        step.setStepNumber(1);
        steps.save(step);
    }
}
