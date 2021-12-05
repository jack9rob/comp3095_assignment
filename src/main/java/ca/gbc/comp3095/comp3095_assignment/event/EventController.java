package ca.gbc.comp3095.comp3095_assignment.event;

import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.services.EventService;
import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import ca.gbc.comp3095.comp3095_assignment.user.User;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Set;

@Controller
public class EventController {

    private final EventService events;
    private final UserService users;

    public EventController(EventService events, UserService users) {
        this.events = events;
        this.users = users;
    }

    @RequestMapping("/events/new")
    public String initCreateEvent(Model model){
        Event event = new Event();

        model.addAttribute("event", event);
        return "event/eventCreate";
    }

    @PostMapping("/events/new")
    public String processCreateEvent(Event event, BindingResult result, Principal principal, Model model){
        if(result.hasErrors()) {
            return "event/eventCreate";
        }else{
            if(event.getTitle().isEmpty() || event.getDescription().isEmpty()) {
                model.addAttribute("event", event);
                return "event/eventCreate";
            }
                User user = users.findByUsername(principal.getName());
            event.setUser(user);
            this.events.save(event);
            return "redirect:/events";
        }
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

    @PostMapping("/events/{eventId}/edit")
    public String processUpdateEvent(@PathVariable("eventId") Long eventId, String title, String description, Model model){
        Event event = this.events.findById(eventId);
        if(title.isEmpty() || description.isEmpty()) {
            model.addAttribute("event", event);
            return "event/eventEdit";
        }
        event.setTitle(title);
        event.setDescription(description);
        events.save(event);
        return "redirect:/events";
    }

    @PostMapping("/events/delete")
    public String deleteEvent(Long eventId, Principal principal) {
        Event event = events.findById(eventId);
        if(event.getUser().getUsername().equals(principal.getName())) {
            events.deleteById(eventId);
        }
        return "redirect:/events";
    }
}
