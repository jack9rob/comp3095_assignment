package ca.gbc.comp3095.comp3095_assignment.shoppinglist;

import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.IngredientRepository;
import ca.gbc.comp3095.comp3095_assignment.services.ShoppingListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class shoppingListController {
    private final ShoppingListService shoppingLists;
    private final IngredientRepository ingredients;

    public shoppingListController(ShoppingListService shoppingLists, IngredientRepository ingredients) {
        this.shoppingLists = shoppingLists;
        this.ingredients = ingredients;
    }

    @GetMapping("/shoppinglist/{recipeId}/{id}")
    public String processAddIngredient(@PathVariable("id") Long ingredientId, @PathVariable("recipeId") Long recipeId, Principal principal) {
        Ingredient ingredient = ingredients.findById(ingredientId).orElse(null);
        if(ingredient != null) {
            ShoppingList list = shoppingLists.findByUser(principal.getName());
            list.getIngredients().add(ingredient);
            shoppingLists.save(list);
        }


        return String.format("redirect:/recipes/%d", recipeId);
    }

    @PostMapping("/shoppinglist/delete")
    public String processDeleteIngredient(Long ingredientId, Principal principal) {
        Ingredient ingredient = ingredients.findById(ingredientId).orElse(null);
        if(ingredient != null) {
            ShoppingList list = shoppingLists.findByUser(principal.getName());
            list.getIngredients().remove(ingredient);
            shoppingLists.save(list);
        }
        return String.format("redirect:/profile");
    }
}
