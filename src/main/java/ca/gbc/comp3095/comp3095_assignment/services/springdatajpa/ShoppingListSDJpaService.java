package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;

import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.services.IngredientService;
import ca.gbc.comp3095.comp3095_assignment.services.ShoppingListService;
import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import ca.gbc.comp3095.comp3095_assignment.shoppinglist.ShoppingList;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.ShoppingListRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingListSDJpaService implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserService userRepository;
    private final IngredientService ingredientRepository;

    public ShoppingListSDJpaService(ShoppingListRepository shoppingListRepository, UserService userRepository, IngredientService ingredientRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.userRepository = userRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Set<ShoppingList> findAll() {
        Set<ShoppingList> lists = new HashSet<>();
        shoppingListRepository.findAll().forEach(lists::add);
        return lists;
    }

    @Override
    public ShoppingList findById(Long id) {
        return shoppingListRepository.findById(id).orElse(null);
    }

    @Override
    public ShoppingList save(ShoppingList list) {
        if(list.getIngredients().size() > 0) {
            list.getIngredients().forEach(ingredient -> {
                if(ingredient.getId() == null) {
                    Ingredient savedIngredient = ingredientRepository.save(ingredient);
                    ingredient.setId(savedIngredient.getId());
                }
            });
        }
        return shoppingListRepository.save(list);
    }

    @Override
    public void delete(ShoppingList object) {
        shoppingListRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        shoppingListRepository.deleteById(id);
    }

    @Override
    public ShoppingList findByUser(String username) {
        return shoppingListRepository.findByUser(userRepository.findByUsername(username));
    }
}
