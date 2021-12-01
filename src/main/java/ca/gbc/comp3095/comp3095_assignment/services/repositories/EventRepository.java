package ca.gbc.comp3095.comp3095_assignment.services.repositories;

import ca.gbc.comp3095.comp3095_assignment.event.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Set;

public interface EventRepository extends CrudRepository<Event, Long> {
    Set<Event> findEventByDateAfterOrderByDate(Date date);

    Set<Event> findEventByDateBeforeOrderByDate(Date date);
}
