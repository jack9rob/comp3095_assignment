package ca.gbc.comp3095.comp3095_assignment.event;

import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.services.EventService;
import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/events/{eventId}")
    public String viewEventDetails(@PathVariable("eventId") Long eventId, Model model,
                            Principal principal){
        Event event = this.events.findById(eventId);
        User user = this.users.findByUsername(principal.getName());

        Boolean isOwner = false;
        if(principal.getName().equals(event.getUser().getUsername())) {
            isOwner = true;
        }
        model.addAttribute("user", user);
        model.addAttribute("event", event);
        model.addAttribute("isOwner", isOwner);
        return "event/eventDetails";
    }

    @RequestMapping("/events/{eventId}/edit")
    public String initEditEvent(@PathVariable("eventId") Long eventId, Principal principal,
                                Model model){
        Event event = this.events.findById(eventId);
        if(principal.getName().equals(event.getUser().getUsername())) {
            model.addAttribute("event", event);
            return "event/eventEdit";
        }
        return String.format("redirect:/events/%d", eventId);
    }
}
