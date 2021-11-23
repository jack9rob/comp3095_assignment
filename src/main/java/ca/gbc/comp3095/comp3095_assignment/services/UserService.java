package ca.gbc.comp3095.comp3095_assignment.services;

import ca.gbc.comp3095.comp3095_assignment.user.User;

public interface UserService extends CrudService<User, Long>{
    User findByUsername(String username);
}
