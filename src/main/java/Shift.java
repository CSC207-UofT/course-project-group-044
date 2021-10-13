import java.time.Duration;
import java.time.Instant;

public class Shift extends Event {
	public Shift(Instant start, Duration duration, String location) {
        super(start, duration, "Shift", location);
	}
}
