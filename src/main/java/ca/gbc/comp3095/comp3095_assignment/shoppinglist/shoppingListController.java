package ca.gbc.comp3095.comp3095_assignment.shoppinglist;

import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.services.IngredientService;
import ca.gbc.comp3095.comp3095_assignment.services.ShoppingListService;
import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import ca.gbc.comp3095.comp3095_assignment.system.ShoppingListPDFExporter;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class shoppingListController {
    private final ShoppingListService shoppingLists;
    private final IngredientService ingredients;
    private final UserService users;

    public shoppingListController(ShoppingListService shoppingLists, IngredientService ingredients, UserService users) {
        this.shoppingLists = shoppingLists;
        this.ingredients = ingredients;
        this.users = users;
    }

    @GetMapping("/shoppinglist")
    public String viewShoppingList(Model model, Principal principal) {
        User user = users.findByUsername(principal.getName());
        if(shoppingLists.findByUser(principal.getName()) == null) {
                ShoppingList list = new ShoppingList();
                list.setUser(user);
                shoppingLists.save(list);
        }
        model.addAttribute("shoppingList", shoppingLists.findByUser(principal.getName()));
        return "shoppingList/shoppingListView";
    }

    @GetMapping("/shoppinglist/{recipeId}/{id}")
    public String processAddIngredient(@PathVariable("id") Long ingredientId, @PathVariable("recipeId") Long recipeId, Principal principal) {
        Ingredient ingredient = ingredients.findById(ingredientId);
        if(ingredient != null) {
            ShoppingList list = shoppingLists.findByUser(principal.getName());
            list.getIngredients().add(ingredient);
            shoppingLists.save(list);
        }


        return String.format("redirect:/recipes/%d", recipeId);
    }

    @GetMapping("/shoppinglist/delete/{id}")
    public String processDeleteIngredient(@PathVariable("id") Long ingredientId, Principal principal) {
        Ingredient ingredient = ingredients.findById(ingredientId);
        if(ingredient != null) {
            ShoppingList list = shoppingLists.findByUser(principal.getName());
            list.getIngredients().remove(ingredient);
            shoppingLists.save(list);
        }
        return String.format("redirect:/shoppinglist");
    }

    @GetMapping("/shoppinglist/export")
    public void exportToPDF(HttpServletResponse response, Principal principal) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        ShoppingList list = shoppingLists.findByUser(principal.getName());

        ShoppingListPDFExporter exporter = new ShoppingListPDFExporter(list);
        exporter.export(response);

    }
}
