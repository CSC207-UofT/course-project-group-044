package com.hr.controller;

import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.service.impl.EmployeeModifierImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FireController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FireControllerTest {
    private static final String DUMMY_ID = "1";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeModifierImpl employeeModifier;


    @Before
    public void setUp() {
        Calendar calendar = new Calendar();
        Employee employee = new Employee("jack", 1, calendar,0, 8, 0, true);
        Mockito.when(employeeModifier.findEmployeeById(1))
                .thenReturn(employee);

    }

    @Test
    public void testFireEmployee() throws Exception {
        MvcResult response = mvc.perform(post("/employee/fire")
                        .param("id", DUMMY_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("employeemanager")).andReturn();

    }
}