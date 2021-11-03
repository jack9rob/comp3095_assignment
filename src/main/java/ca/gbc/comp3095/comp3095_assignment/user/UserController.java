package ca.gbc.comp3095.comp3095_assignment.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    //private final sdf users;

    public UserController(UserDetailsService users) {
        //this.users = users;
    }

    @GetMapping("/register")
    public String register() {
        return "user/userRegistration";
    }

    @PostMapping("/register")
    public String registerProcess(String username, String password) {
        // register user here
        return "redirect:/login";
    }
}
