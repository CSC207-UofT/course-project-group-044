public class Employee {
    public final String name;
    public final int id;
    public final String title;
    public final Calendar calendar;
    public final int salary;
    public final int maxHoursPerWeek;
    public boolean schedulable;

    public Employee(String name, int id, String title, Calendar calendar,
                    int salary, int maxHoursPerWeek, boolean schedulable) {

        this.name = name;
        this.id = id;
        this.title = title;
        this.calendar = calendar;
        this.salary = salary;
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.schedulable = schedulable;
    }
}
