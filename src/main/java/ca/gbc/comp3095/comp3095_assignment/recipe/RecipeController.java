package ca.gbc.comp3095.comp3095_assignment.recipe;

import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.IngredientRepository;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
public class RecipeController {


    private final RecipeRepository recipes;
    private final StepRepository steps;
    private final IngredientRepository ingredients;

    @Autowired
    public RecipeController(RecipeRepository recipes, StepRepository steps, IngredientRepository ingredients) {
        this.recipes = recipes;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    @RequestMapping("/recipes")
    public String findAll(Model model) {
        Collection<Recipe> results = this.recipes.getAll();
        model.addAttribute("recipes", results);
        return "recipe/recipeList";
    }

    @GetMapping("/recipes/new")
    public String initCreateRecipe(Model model) {
        Recipe recipe = new Recipe();
        model.addAttribute("recipe", recipe);
        return "recipe/recipeCreate";
    }

    @PostMapping("/recipes/new")
    public String processCreateRecipe(Recipe recipe, BindingResult result) {
        if(result.hasErrors()) {
            return "recipe/recipeCreate";
        } else {
            this.recipes.save(recipe);
            return "redirect:/recipes";
        }
    }

    @RequestMapping("/recipes/{recipeId}")
    public String viewRecipe(@PathVariable("recipeId") Long recipeId, Model model) {
        Recipe recipe = this.recipes.findById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("step", new Step());
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("steps", this.steps.getStepOrderStepNumber(recipeId));
        return "recipe/recipeView";
    }
    // move to step controller probably
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
}
