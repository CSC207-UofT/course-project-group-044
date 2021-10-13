public class Employee {
    private String name;
    private int id;
    private String title;
    private Calendar calendar;
    private int salary;
    private int maxHoursPerWeek;

    public Employee(String name, int id, String title, Calendar calendar,
                    int salary, int maxHoursPerWeek) {

        this.name = name;
        this.id = id;
        this.title = title;
        this.calendar = calendar;
        this.salary = salary;
        this.maxHoursPerWeek = maxHoursPerWeek;
    }
}
