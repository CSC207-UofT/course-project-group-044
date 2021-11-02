import Entity.Employee;
import Entity.Organization;
import Service.EmployeeModifier;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.HashMap;

public class EmployeeModifierTest {
    private Organization org;
    private EmployeeModifier mgr;
    private HashMap<Integer,Employee> employees;

    @Before
    public void setUp() {
        this.org = new Organization();
        this.mgr = new EmployeeModifier(this.org);
        this.employees = this.org.getEmployees();
    }

    @After
    public void tearDown() {
    }

    @Test(timeout = 50)
    public void testInit() {
        assertTrue(this.employees.isEmpty());
    }

    @Test(timeout = 50)
    public void testHireEmployee() {
        mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);

        assertTrue(this.employees.containsKey(1));
        assertEquals(this.employees.size(), 1);
    }

    @Test(timeout = 50)
    public void testFireEmployee() {
        Employee employee;
        employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);
        mgr.fireEmployee(employee);

        assertTrue(this.employees.isEmpty());
    }

}
