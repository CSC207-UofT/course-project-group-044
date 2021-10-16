import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Organization {
    private Map<Integer,Employee> employees;

    public Organization(){
        this.employees = Collections.emptyMap();
    }

    public Organization(List<Employee> employees) {
        for (Employee e: employees){
            this.employees.put(e.getId(), e);
        }
    }

    public Map<Integer, Employee> getEmployees() {
        return this.employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.put(employee.getId(), employee);
    }


    public void removeEmployee(Employee employee) {
        this.employees.remove(employee.getId());
    }
}
