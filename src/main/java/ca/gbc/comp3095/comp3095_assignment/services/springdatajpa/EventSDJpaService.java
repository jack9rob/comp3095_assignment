package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;

import ca.gbc.comp3095.comp3095_assignment.event.Event;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.services.EventService;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class EventSDJpaService implements EventService {

    private final EventRepository eventRepository;

    public EventSDJpaService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Set<Event> findAll() {
        Set<Event> events = new HashSet<>();
        eventRepository.findAll().forEach(events::add);
        return events;
    }

    @Override
    public Event findById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    @Override
    public Event save(Event object) {
        return eventRepository.save(object);
    }

    @Override
    public void delete(Event object) {
        eventRepository.delete(object);
    }

    @Override
    public void deleteById(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public Set<Event> findEventByDateAfterOrderByDate(Date date) {
        Set<Event> events = new HashSet<>();
        eventRepository.findEventByDateAfterOrderByDate(date).forEach(events::add);
        return events;
    }

    @Override
    public Set<Event> findEventByDateBeforeOrderByDate(Date date) {
        Set<Event> events = new HashSet<>();
        eventRepository.findEventByDateBeforeOrderByDate(date).forEach(events::add);
        return events;
    }
}
