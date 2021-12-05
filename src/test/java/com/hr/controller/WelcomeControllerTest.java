package com.hr.controller;

import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.service.EmployeeModifier;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WelcomeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WelcomeControllerTest {
    private static final String DUMMY_ID = "1";

    @Autowired
    MockMvc mvc;

    @MockBean
    EmployeeModifier employeeModifier;



    @Before
    public void setUp() {
        Calendar calendar = new Calendar();
        Employee employee = new Employee("jack", 1, calendar,0, 8, 0, true);
        Mockito.when(employeeModifier.findEmployeeById(1))
                .thenReturn(employee);

    }
    @Test
    public void testgetWelcomePage() throws Exception {
        MvcResult response = mvc.perform(get("/welcome")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("welcome")).andReturn();

    }


}