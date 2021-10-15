import java.util.HashMap;
import java.util.Collection;
import java.util.Map;

public class Organization {
    private Map<Integer, Employee> employees;

    public Organization() {
        this.employees = new HashMap<>();
    }

    public void addEmployee(Employee employee) {
        this.employees.put(employee.id, employee);
    }

    public Employee getEmployee(int id) {
        return this.employees.get(id);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee.id);
    }

    public Collection<Employee> getEmployees() {
        return this.employees.values();
    }
}
