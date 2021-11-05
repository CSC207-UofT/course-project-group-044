package Repository;

import Entity.Event;
import org.springframework.data.repository.CrudRepository;
import java.time.*;

public interface EventRepository extends CrudRepository<Event, Instant> {
}