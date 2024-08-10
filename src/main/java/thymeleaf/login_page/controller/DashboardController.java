package thymeleaf.login_page.controller;


import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thymeleaf.login_page.dto.*;
import thymeleaf.login_page.entity.Account;
import thymeleaf.login_page.service.dashboard.DashboardService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dash")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/welcomePage")
    public String dash(){
        return "dashboard/welcome";
    }

    @GetMapping("/profilePage")
    public String resetPassword(){ return "dashboard/profile";
    }

    @GetMapping("/receiptPage")
    public String getReceipt(){
        return "dashboard/receipts";
    }

    @GetMapping("/depositPage")
    public String deposit(){return "dashboard/deposit";}

    @PostMapping("/updatePassword")
    public String updatePassword (UpdatePassword updatePassword){
        dashboardService.updatePassword(updatePassword);
        return "";
    }

    @GetMapping("accountDetails")
    public AccountDto accountDetails(){
        return dashboardService.getAccountDetails();
    }
    @GetMapping("userProfile")
    public UserProfileDto getUserProfile(){
        return dashboardService.getUSerProfile();
    }

    @GetMapping("userReceipts")
    public UserReceiptsDto getUserReceipts(){return dashboardService.getUserReceipts();}

    @PostMapping("/deposit")
    public String depositIntoAccount(DepositDto depositDto) {return dashboardService.deposit(depositDto);}
}
