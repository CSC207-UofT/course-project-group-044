import java.time.Duration;
import java.time.Instant;

public abstract class Event {
	private Instant start;
	private Duration duration;
	private String name;
	private String location;

	public Event(Instant start, Duration duration, String name, String location) {
        this.start = start;
        this.duration = duration;
        this.name = name;
        this.location = location;
	}
}
