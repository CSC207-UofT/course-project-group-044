import java.util.List;
import java.util.ArrayList;

public class Calendar {
    private List<Event> events;

    public Calendar() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
}
