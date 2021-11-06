package ca.gbc.comp3095.comp3095_assignment.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class UserController {

    private final UserRepository users;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository users, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.users = users;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
            return "forward:/login?logout";
        }

        return "user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "user/userRegistration";
    }

    @PostMapping("/register")
    public String registerProcess(String username, String password, String confirmPassword, Model model) {
        if(users.findByUsername(username) != null) {
            model.addAttribute("error", username + " is already taken");
            return "user/userRegistration";
        } else if(!password.equals(confirmPassword)) {
            model.addAttribute("passwords do not match");
            return "user/userRegistration";
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));

        users.save(newUser);
        return "redirect:/login";
    }

    @GetMapping("profile")
    public String profile(Model model, Principal principal) {
        User user = users.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/userProfile";
    }
}
