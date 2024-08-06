package thymeleaf.login_page.service.user;

import org.springframework.ui.Model;
import thymeleaf.login_page.dto.AuthenticationDto;
import thymeleaf.login_page.dto.RegistrationDto;
import thymeleaf.login_page.dto.ResetPasswordDto;

import javax.servlet.http.HttpSession;

public interface AuthenticationService {
    public String userLogin(AuthenticationDto authenticationDto, Model model, HttpSession session);

    public String userRegistration(RegistrationDto registrationDto, Model model);

    public String emailCheck(String email, Model model);

    public String passwordReset(ResetPasswordDto resetPasswordDto, Model model);


    }
