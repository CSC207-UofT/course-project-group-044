import com.hr.entity.Employee;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.CalendarRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

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

    @Mock
    private CalendarRepository calendarRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test(timeout = 50)
    public void testHireEmployee() {
        Employee returned = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);

        verify(employeeRepository).save(returned);
        verify(calendarRepository).save(returned.getCalendar());
    }

    @Test
    public void testSalaryEvaluation(){
        Employee returned = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);

        assertEquals(2800.0, mgr.evaluateSalary(returned));
    }

    @Test(timeout = 50)
    public void testFireEmployee() {
        Employee employee;
        employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);
        mgr.fireEmployee(employee);

        verify(employeeRepository).save(employee);
        verify(employeeRepository).delete(employee);
    }

}
