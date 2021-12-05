package controllerTest;

import com.hr.controller.HireController;
import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HireControllerUnitTest {
    private static final int DUMMY_ID = 1;
    private static final String DUMMY_NAME = "Jason";

    @InjectMocks
    private HireController hireController;

    @Mock
    private EmployeeModifierImpl employeeModifier;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CalendarRepository calendarRepository;

    @Before
    public void setUp() {
        Calendar calendar = new Calendar();
        Employee employee = new Employee(DUMMY_NAME, DUMMY_ID, calendar,0, 8, 0, true);
        hireController = new HireController();

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Mockito.when(calendarRepository.save(calendar)).thenReturn(calendar);
        Mockito.spy(employeeModifier.hireEmployee(DUMMY_NAME, DUMMY_ID,0, 8, 0));
    }

    @Test
    public void testAddEmployee() {
        Calendar calendar = new Calendar();
        Employee employee = new Employee(DUMMY_NAME, DUMMY_ID, calendar,0, 8, 0, true);
        Model model = new BindingAwareModelMap();


        hireController.addEmployee(employee, model);

        Assertions.assertEquals(1, ((Employee)model.getAttribute("employee")).getId());
        Assertions.assertEquals("Jason", ((Employee)model.getAttribute("employee")).getName());
    }
}