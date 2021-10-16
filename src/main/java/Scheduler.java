import java.time.Instant;

public class Scheduler {
    public Calendar calendar;

    public Scheduler(Calendar calendar) {
        this.calendar = calendar;
    }

    public void createEvent(Instant start, Event event){
        this.calendar.addEvent(start, event);
    }

}
