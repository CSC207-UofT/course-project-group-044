import com.hr.entity.Employee;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.CalendarRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeModifierTest {

    @InjectMocks
    private EmployeeModifierImpl mgr;
    private HashMap<Integer,Employee> employees;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CalendarRepository calendarRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
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

    @Test
    public void testFireEmployee() {
        Employee employee;
        employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);
        mgr.fireEmployee(employee);

        verify(employeeRepository).save(employee);
        verify(employeeRepository).delete(employee);
    }

}
