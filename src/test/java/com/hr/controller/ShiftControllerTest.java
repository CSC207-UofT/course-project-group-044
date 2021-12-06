package com.hr.controller;

import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.entity.Shift;
import com.hr.service.impl.EmployeeModifierImpl;
import com.hr.service.impl.EventServiceImpl;
import com.hr.service.impl.SchedulerImpl;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ShiftController.class)
@AutoConfigureMockMvc(addFilters = false)
class ShiftControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeModifierImpl employeeModifier;
    Employee employee;

    @MockBean
    private EventServiceImpl eventService;

    @MockBean
    private SchedulerImpl scheduler;

    @Before
    public void setUp() {
        Calendar calendar = new Calendar();
        employee = new Employee("jimmy", 100, calendar, 20,
                20, 0, true);
        Mockito.when(employeeModifier.findEmployeeById(Mockito.anyInt())).thenReturn(new Employee());
        Mockito.when(scheduler.scheduleShift(employee, Mockito.any(ZonedDateTime.class),
                Mockito.anyString(), Mockito.anyInt())).thenReturn(new Shift());
    }

    @Test
    void addShift() throws Exception {
        mvc.perform(post("/event/addshift")
                        .param("date", "2022-01-06T00:00")
                        .param("loccation", "207 Room")
                        .param("employee", "jimmy"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("eventmanager"));
    }

    @Test
    void addMeeting() throws Exception {
        mvc.perform(post("/event/addmeeting")
                        .param("employee", "1")
                        .param("participants", "2")
                        .param("location", "207 Room")
                        .param("hours", "5")
                        .param("name", "ABC Meeting")
                        .param("date", "2021-12-15T02:07"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("eventmanager"));
    }
}