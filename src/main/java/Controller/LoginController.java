package Controller;


import Entity.User;
import Service.EmployeeModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("login")
public class LoginController {
    private static User DUMMY_FORM_PLACEHOLDER_User = new User();

    @Autowired
    private EmployeeModifier employeeModifier;

    @GetMapping()
    public String getLoginPage(Model model) {
        model.addAttribute("employee", DUMMY_FORM_PLACEHOLDER_User);

        return "login";
    }

    @PostMapping()
    public String login(@ModelAttribute(value="employee") User user, Model model) {

        User usermatch = EmployeeModifier.getUsernamebyID(user.getId());;
        //not satisfied the clean architecture
        if (user != null && user.getPassword().equals(usermatch.getPassword())) {
            model.addAttribute("user", user);
            model.addAttribute("message", "welcome");
            //int salary, int maxHoursPerWeek, int hoursPerShift
            return "HirePage";
        }

        model.addAttribute("user", DUMMY_FORM_PLACEHOLDER_User);
        model.addAttribute("message", "Username or password is wrong!");

        return "LoginPage";
    }
}