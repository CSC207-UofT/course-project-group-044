package Controller;

import Entity.Employee;
import Service.EmployeeModifier;
import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("employee")
public class FireController {
    private static Employee employee = new Employee();

    @Autowired
    private EmployeeModifier employeeModifier;

    @PostMapping ("/fire")
    public String deleteEmployee(@ModelAttribute(value="employee") Employee employee, Model model){

        Employee user = employeeModifier.findEmployeeById(employee.getId());

        if (!Objects.equals(user, Optional.empty())){
            employeeModifier.fireEmployeebyID(user.getId());
            model.addAttribute("employee", employee);
            return "FirePage";
        }
        model.addAttribute("employee", user);
        model.addAttribute("message", "firing failed because you add nothing");

        return "FirePage";
    }
}
