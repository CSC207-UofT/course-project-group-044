import org.junit.*;

import static org.junit.Assert.*;

import java.util.Collection;

public class EmployeeManagerTest {
    private Organization org;
    private EmployeeManager mgr;
    private Collection<Employee> employees;

    @Before
    public void setUp() {
        this.org = new Organization();
        this.mgr = new EmployeeManager(this.org);
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
        mgr.hireEmployee("Sunset Shimmer", 1, 20, 20);

        Employee arr[] = this.employees.toArray(new Employee[1]);
        assertEquals(arr[0].getName(), "Sunset Shimmer");
        assertEquals(arr.length, 1);
    }

    @Test(timeout = 50)
    public void testFireEmployee() {
        Employee employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20);
        mgr.fireEmployee(employee);

        assertTrue(this.employees.isEmpty());
    }

}
