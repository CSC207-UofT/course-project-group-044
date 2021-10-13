import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Meeting extends Event {
    private Employee holder;
    private List<Employee> participants;

	public Meeting(Instant start, Duration duration, String name, String location,
                   Employee holder, List<Employee> participants) {
        super(start, duration, name, location);
        this.holder = holder;
        this.participants = participants;
    }
}
