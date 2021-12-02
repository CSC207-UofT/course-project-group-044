package controllertestPackage;

import com.hr.Application;
import com.hr.controller.FireController;
import com.hr.controller.LoginController;
import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.service.impl.EmployeeModifierImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = {LoginController.class})
public class LoginControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void testgetLoginPage() throws Exception {
        MvcResult response = mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("login")).andReturn();

    }
}

