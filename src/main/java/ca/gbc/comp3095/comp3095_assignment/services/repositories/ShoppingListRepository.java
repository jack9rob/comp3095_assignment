package ca.gbc.comp3095.comp3095_assignment.services.repositories;

import ca.gbc.comp3095.comp3095_assignment.shoppinglist.ShoppingList;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {
    ShoppingList findByUser(User user);
}
