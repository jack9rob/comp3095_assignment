package ca.gbc.comp3095.comp3095_assignment.services;

import ca.gbc.comp3095.comp3095_assignment.event.Event;

import java.util.Date;
import java.util.Set;

public interface EventService extends CrudService<Event, Long> {
    Set<Event> findEventByDateAfterOrderByDate(Date date);
    Set<Event> findEventByDateBeforeOrderByDate(Date date);
}
