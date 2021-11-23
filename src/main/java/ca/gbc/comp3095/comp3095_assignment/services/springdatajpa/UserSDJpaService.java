package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;

import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.UserRepository;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserSDJpaService implements UserService {
    final private UserRepository userRepository;

    public UserSDJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
