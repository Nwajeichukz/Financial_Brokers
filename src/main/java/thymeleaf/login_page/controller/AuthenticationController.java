package thymeleaf.login_page.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thymeleaf.login_page.dto.AuthenticationDto;
import thymeleaf.login_page.dto.RegistrationDto;
import thymeleaf.login_page.dto.ResetPasswordDto;
import thymeleaf.login_page.service.user.AuthenticationService;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationServiceImpl;

    @GetMapping("/homePage")
    public String show(){
        return "authentication/home";
    }

    @GetMapping("/emailPage")
    public String emailPage(AuthenticationDto authenticationDto){ return "authentication/emailCheckPage";}

    @GetMapping("/signUp")
    public String registrationPage(RegistrationDto registrationDto){
        return "authentication/registrationPage";
    }

    @GetMapping("/signIn")
    public String loginPage(AuthenticationDto authenticationDto){
        return "authentication/loginPage";
    }

    @GetMapping("/changePassword")
    public String resetPassword(ResetPasswordDto resetPasswordDto){ return "authentication/resetPasswordPage";
    }

    @PostMapping("login")
    public String loginProcess(@ModelAttribute("authenticationDto") AuthenticationDto authenticationDto, Model model, HttpSession session){
        return authenticationServiceImpl.userLogin(authenticationDto, model, session);
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registrationDto") RegistrationDto registrationDto, Model model){
        return authenticationServiceImpl.userRegistration(registrationDto, model);

    }

    @PostMapping("/emailCheck")
    public String emailCheck(@ModelAttribute("authenticationDto") AuthenticationDto authenticationDto, Model model){
        return authenticationServiceImpl.emailCheck(authenticationDto.getEmail(), model);
    }

    @PostMapping("/resetPassword")
    public String passwordReset(@ModelAttribute("resetPasswordDto") ResetPasswordDto resetPasswordDto, Model model){
        return authenticationServiceImpl.passwordReset(resetPasswordDto, model);
    }
}
