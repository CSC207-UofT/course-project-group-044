import java.util.List;

public class Employee {
    private final String name;
    private final int id;
    private List<Event> events;
    private int salary;
    private int maxHoursPerWeek;
    private boolean schedulable;

    public Employee(String name, int id, List<Event> events,
                    int salary, int maxHoursPerWeek, boolean schedulable) {

        this.name = name;
        this.id = id;
        this.events = events;
        this.salary = salary;
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.schedulable = schedulable;
    }

    // getters for every attributes above
    public String getName() { return this.name; }

    public int getId() { return this.id; }

    public List<Event> getEvents() { return this.events; }

    public int getSalary() { return this.salary; }

    public int getMaxHoursPerWeek() { return this.maxHoursPerWeek; }

    public boolean getSchedulable() { return this.schedulable; }

    // setters
    public void addEvent(Event event) { this.events.add(event); }

    public void setSalary(int new_sal) { this.salary = new_sal; }

    public void setMaxHoursPerWeek(int new_hour) { this.maxHoursPerWeek = new_hour; }

    public void setSchedulable(boolean new_bool) { this.schedulable = new_bool; }
}
