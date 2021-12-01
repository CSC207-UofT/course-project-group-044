package controllerTest;

import com.hr.controller.FireController;
import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.service.impl.EmployeeModifierImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FireController.class)
public class HireControllerIntegrationTest {
    private static final int DUMMY_ID = 1;
    private static final String OLD_FIRST_NAME = "Jason";
    private static final String OLD_LAST_NAME = "Chen";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeModifierImpl employeeModifier;

    @Before
    public void setUp() {
        Calendar calendar = new Calendar();
        Employee employee = new Employee(OLD_FIRST_NAME, DUMMY_ID, calendar,0, 8, 0, true);
        Mockito.when(employeeModifier.findEmployeeById(DUMMY_ID))
                .thenReturn(employee);
    }

//    @Test
//    public void testHireEmployee() throws Exception {
//        MvcResult response = mvc.perform(post("/employee/hire")
//                .param("EmployeeID", DUMMY_ID)
//                .param("firstName", NEW_FIRST_NAME)
//                .param("lastName", NEW_LAST_NAME))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("text/html;charset=UTF-8"))
//                .andExpect(view().name("studentInfoPage")).andReturn();
//
//        assertTrue(response.getResponse().getContentAsString().contains(NEW_FIRST_NAME));
//        assertTrue(response.getResponse().getContentAsString().contains(NEW_LAST_NAME));
//    }
}