import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Calendar {
    private HashMap<Instant, List<Event>> events;

    public Calendar() {
        this.events = new HashMap<Instant, List<Event>>();
    }

    public void addEvent(Instant startTime, Event event) {
        if (!this.events.containsKey(startTime)) {
            this.events.put(startTime, new ArrayList<Event>());
        }
        this.events.get(startTime).add(event);
    }
}
