/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: contains the routes for user profiles, login, register. used bCryptPasswordEncoder to encrypt all passwords
 */
package ca.gbc.comp3095.comp3095_assignment.user;

import ca.gbc.comp3095.comp3095_assignment.services.ShoppingListService;
import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import ca.gbc.comp3095.comp3095_assignment.shoppinglist.ShoppingList;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

@Controller
public class UserController {

    private final UserService users;
    private final ShoppingListService shoppingListService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService users, ShoppingListService shoppingListService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.users = users;
        this.shoppingListService = shoppingListService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
            return "user/login";
        }

        return "user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "user/userRegistration";
    }

    @PostMapping("/register")
    public String registerProcess(String username, String password, String confirmPassword, String email,
                                  String firstName, String lastName, MultipartFile image, Model model) {
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
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setImage(StringUtils.cleanPath(image.getOriginalFilename()));

        users.save(newUser);
        return "redirect:/login";
    }

    @GetMapping("profile")
    public String profile(Model model, Principal principal) {
        User user = users.findByUsername(principal.getName());
        ShoppingList list = shoppingListService.findByUser(principal.getName());
        if(list == null) {
            ShoppingList newList = new ShoppingList();
            newList.setUser(user);
            shoppingListService.save(newList);
            list = shoppingListService.findByUser(principal.getName());
        }
        model.addAttribute("user", user);
        model.addAttribute("list", list);
        return "user/userProfile";
    }

    @GetMapping("profile/update")
    public String initUpdateProfile(Model model, Principal principal) {
        User user = users.findByUsername(principal.getName());
        if(user != null) {
            model.addAttribute("user", user);
            return "user/userProfileEdit";
        }
        return "redirect:/login";
    }

    @PostMapping("profile/update")
    public String processUpdateProfile(Model model, String email,
                                       String firstName, String lastName, String bio, MultipartFile image, Principal principal) {


        User user = users.findByUsername(principal.getName());
        if(email.isEmpty()|| firstName.isEmpty() || lastName.isEmpty())  {
            model.addAttribute("error", "please fill out information");
            model.addAttribute("user", user);
            return "user/userProfileEdit";
        }

        String filename = StringUtils.cleanPath(image.getOriginalFilename());
        if(filename.contains("..")) {
            model.addAttribute("error", "not a valid file");
            model.addAttribute("user", user);
            return "user/userProfileEdit";
        }
        try {
            user.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch(IOException e) {
            model.addAttribute("error", "not a valid file");
            model.addAttribute("user", user);
            return "user/userProfileEdit";
        }
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBio(bio);
        users.save(user);

        return "redirect:/profile";
    }

    @GetMapping("/user/changepassword")
    public String initChangePassword(Model model, Principal principal) {
        User user = users.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user/userChangePassword";

    }

    @PostMapping("/user/changepassword")
    public String processChangePassword(Model model, Principal principal, String oldPassword, String newPassword,
                                        String confirmNewPassword) {

        User user = users.findByUsername(principal.getName());
        if(!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("user", user);
            model.addAttribute("error", "invalid password");
            return "user/userChangePassword";
        } else if(!newPassword.equals(confirmNewPassword) || newPassword.isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("error", "passwords do not match");
            return "user/userChangePassword";
        }

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        users.save(user);
        return "redirect:/logout";

    }
}
