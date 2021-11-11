package Controller;

import Entity.Employee;
import Service.EmployeeModifier;
import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("employee")
public class HireController {
    private static Employee employee = new Employee();

    @Autowired
    private EmployeeModifier employeeModifier;

    @PostMapping("/hire")
    public String addEmployee(@ModelAttribute(value="employee") Employee employee, Model model){

        Employee user = EmployeeModifier.findEmployeeBy(employee.getId());

        if (user != null){
            employeeModifier.hireEmployee(user.getName(), user.getId(),
                    user.getMaxHoursPerWeek(), user.getHoursPerShift());
            model.addAttribute("employee", employee);
            return "HirePage";
        }

        model.addAttribute("employee", user);
        model.addAttribute("message", "hiring failed because you add nothing");

        return "HirePage";
    }
}
