package ca.gbc.comp3095.comp3095_assignment.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class RecipeController {


    private final RecipeRepository recipes;

    @Autowired
    public RecipeController(RecipeRepository recipes) {
        this.recipes = recipes;
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
}
