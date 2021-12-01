package ca.gbc.comp3095.comp3095_assignment.event;

import ca.gbc.comp3095.comp3095_assignment.services.EventService;
import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class EventController {

    private final EventService events;
    private final UserService users;

    public EventController(EventService events, UserService users) {
        this.events = events;
        this.users = users;
    }

    @GetMapping("/events")
    public String viewEvents(Principal principal, Model model) {
        User user = users.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "event/eventView";
    }
}
