package Controller;

import Entity.Employee;
import Entity.Employee;
import Service.EmployeeModifier;
import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("employee")
public class FireController {
    private static Employee employee = new Employee();

    @Autowired
    private EmployeeModifier employeeModifier;

    @PostMapping("/fire")
    public String deleteEmployee(@ModelAttribute(value="employee") Employee employee, Model model){

        Employee user = EmployeeModifier.findEmployeeBy(employee.getId());

        if (user != null){
            employeeModifier.fireEmployee(user);
            model.addAttribute("employee", employee);
            return "FirePage";
        }
        model.addAttribute("employee", user);
        model.addAttribute("message", "firing failed because you add nothing");

        return "FirePage";
    }
}
