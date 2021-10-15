import java.util.List;

public class Employee {
    public final String name;
    public final int id;
    public List<Event> events;
    public int salary;
    public int maxHoursPerWeek;
    public boolean schedulable;

    public Employee(String name, int id, List<Event> events,
                    int salary, int maxHoursPerWeek, boolean schedulable) {

        this.name = name;
        this.id = id;
        this.events = events;
        this.salary = salary;
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.schedulable = schedulable;
    }
}
