import com.hr.entity.Employee;
import com.hr.repository.EmployeeRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeModifierTest {

    @InjectMocks
    private EmployeeModifierImpl mgr;
    private HashMap<Integer,Employee> employees;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        ArrayList<Employee> employees = (ArrayList<Employee>) employeeRepository.findAll();
        for (Employee employee: employees){
            this.employees.put(employee.getId(), employee);
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testInit() {
        assertTrue(this.employees.isEmpty());
    }

    @Test
    public void testHireEmployee() {
        mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);

        assertTrue(this.employees.containsKey(1));
        assertEquals(this.employees.size(), 1);
    }

    @Test
    public void testSalaryEvaluation(){
        assertEquals(2800.0, mgr.evaluateSalary(1));
    }

    @Test
    public void testFireEmployee() {
        Employee employee;
        employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);
        mgr.fireEmployee(employee);

        assertTrue(this.employees.isEmpty());
    }

}
