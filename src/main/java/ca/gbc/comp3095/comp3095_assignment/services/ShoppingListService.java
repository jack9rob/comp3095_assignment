package ca.gbc.comp3095.comp3095_assignment.services;

import ca.gbc.comp3095.comp3095_assignment.shoppinglist.ShoppingList;

public interface ShoppingListService extends CrudService<ShoppingList, Long> {
    ShoppingList findByUser(String username);
}
