package Repository;

import Entity.Event;
import Entity.Shift;
import org.springframework.data.repository.CrudRepository;
import java.time.*;

public interface EventRepository extends CrudRepository<Event, Instant> {
    Shift findEventBy(Instant start);
}