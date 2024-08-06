package thymeleaf.login_page.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DashboardController {
    @GetMapping("/dashboardPage")
    public String dashboardPage(){
        return "dashboard";
    }

}
