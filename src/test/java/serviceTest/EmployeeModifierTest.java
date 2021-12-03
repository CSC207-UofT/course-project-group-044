package serviceTest;

import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeModifierTest {

    @InjectMocks
    private EmployeeModifierImpl mgr;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CalendarRepository calendarRepository;

    public EmployeeModifierTest() {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        }

    @After
    public void tearDown() {
    }

    @Test
    public void testHireEmployee() {
        when(calendarRepository.save(any(Calendar.class))).thenReturn(new Calendar());
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());
        Employee result = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);

        verify(calendarRepository, times(1)).save(any(Calendar.class));
        verify(employeeRepository, times(1)).save(any(Employee.class));
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("Sunset Shimmer", result.getName());
        Assertions.assertEquals(20, result.getSalary());
        Assertions.assertEquals(20, result.getMaxHoursPerWeek());
        Assertions.assertEquals(4, result.getHoursPerShift());
    }

    @Test
    public void testFireEmployee() {
        when(calendarRepository.save(any(Calendar.class))).thenReturn(new Calendar());
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());
        Employee employee = mgr.hireEmployee("Sunset Shimmer", 1, 20,
                20, 4);

        doNothing().when(calendarRepository).delete(any(Calendar.class));
        doNothing().when(employeeRepository).delete(employee);
        mgr.fireEmployee(employee);

        verify(employeeRepository).delete(employee);
//        verify(calendarRepository).delete(employee.getCalendar());
    }

    @Test
    public void testFindEmployeeById(){
        when(calendarRepository.save(any(Calendar.class))).thenReturn(new Calendar());
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());
        Employee expectEmployee = mgr.hireEmployee("Sunset Shimmer", 1, 20,
                20, 4);

        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(expectEmployee));
        Employee actualEmployee = mgr.findEmployeeById(1);

        Assertions.assertEquals(expectEmployee, actualEmployee);
    }

    @Test
    public void testFindEmployeeByIdNullEmployee(){
        Employee actualEmployee = mgr.findEmployeeById(1);

        Assertions.assertNull(actualEmployee);
    }

    @Test
    public void testSalaryEvaluation(){
        when(calendarRepository.save(any(Calendar.class))).thenReturn(new Calendar());
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());
        Employee employee = mgr.hireEmployee("Sunset Shimmer", 1, 20,
                20, 4);

        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(employee));
        Assertions.assertEquals(2800.0, mgr.evaluateSalary(1));
    }
}
