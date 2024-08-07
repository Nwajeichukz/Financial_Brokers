package thymeleaf.login_page.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import thymeleaf.login_page.dto.AuthenticationDto;
import thymeleaf.login_page.dto.EmailDto;
import thymeleaf.login_page.dto.RegistrationDto;
import thymeleaf.login_page.dto.ResetPasswordDto;
import thymeleaf.login_page.entity.Role;
import thymeleaf.login_page.entity.User;
import thymeleaf.login_page.repository.RoleRepository;
import thymeleaf.login_page.repository.UserRepository;
import thymeleaf.login_page.service.JwtService;
import thymeleaf.login_page.service.MyUserDetailsService;
import thymeleaf.login_page.service.email.EmailServiceImpl;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtService jwtService;

    private final MyUserDetailsService myUserDetailsService;

    private final EmailServiceImpl emailServiceImpl;

    private final SpringTemplateEngine templateEngine;


    private final PasswordEncoder passwordEncoder;

    @Override
    public String userLogin(AuthenticationDto authenticationDto, Model model, HttpSession session) {

        try {
            boolean check = userRepository.existsByEmail(authenticationDto.getEmail().toLowerCase());

            var user = myUserDetailsService.loadUserByUsername(authenticationDto.getEmail());

            if (!passwordEncoder.matches(authenticationDto.getPassword(), user.getPassword())){
                model.addAttribute("error", "Email or password is incorrect");
                return "authentication/loginPage";
            }
            var jwtToken = jwtService.generateToken(user);

            // Store the JWT token in the session (or any preferred method)
            session.setAttribute("jwtToken", jwtToken);
            session.setAttribute("user", user);

            return "redirect:/dashboardPage";

        }catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Email or password is incorrect");
            return "authentication/loginPage";
        }

    }

    @Override
    public String userRegistration(RegistrationDto registrationDto, Model model) {
        boolean check = userRepository.existsByEmail(registrationDto.getEmail().toLowerCase());

        System.out.println();

        if(check){
            model.addAttribute("errorMessage", "User already exists");

            return "authentication/registrationPage";
        }

        User user = new User();
        user.setUserName(registrationDto.getUserName());
        user.setFullName(registrationDto.getFullName());
        user.setCountry(registrationDto.getCountry());
        user.setEmail(registrationDto.getEmail());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setReferralId(registrationDto.getReferralId());
        user.setAccountBalance(0.00);


        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");

            return "authentication/registrationPage";
        }

        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        Role roles = roleRepository.findByName("USER");

        if (roles == null) {
            Role newRole = new Role();
            newRole.setName("USER");

            user.setRoles(newRole);
        }else{
            user.setRoles(roles);
        }

        userRepository.save(user);

        model.addAttribute("authenticationDto", new AuthenticationDto());

        model.addAttribute("correct", "Account Successfully Created");

        return "authentication/loginPage";

    }

    @Override
    public String emailCheck(String email, Model model) {
        Optional<User> user = userRepository.findByEmail(email.toLowerCase());

        if(user.isEmpty()){
            model.addAttribute("error", "email not found");

            return "authentication/emailCheckPage";
        }

        int id = user.get().getUserId();

        User getUser = userRepository.findById(id).orElseThrow();

        String confirmationToken = UUID.randomUUID().toString();

        getUser.setCodeGeneratedTime(LocalDateTime.now());
        getUser.setConfirmationToken(confirmationToken);

        userRepository.save(getUser);

        // Create HTML content
        String confirmationLink = "http://localhost:8080/auth/changePassword";
        Context context = new Context();
        context.setVariable("confirmationLink", confirmationLink);
        String htmlContent = templateEngine.process("authentication/confirmationEmail", context);

        this.sendTransactionFile(htmlContent, email);

        model.addAttribute("correct", "email sent successfully");

        return "authentication/emailCheckPage";
    }


    @Override
    public String passwordReset(ResetPasswordDto resetPasswordDto, Model model) {

        Optional<User> user = userRepository.findByEmail(resetPasswordDto.getEmail().toLowerCase());

        if(user.isEmpty()){
            model.addAttribute("error", "not found / password reset token invalid");
            return "authentication/resetPasswordPage";

        }else if(!Objects.equals(user.get().getEmail(), resetPasswordDto.getEmail().toLowerCase())){
            model.addAttribute("error", "not found / password reset token invalid");
            return "authentication/resetPasswordPage";
        }


        if (user.get().getConfirmationToken() == null) {
            model.addAttribute("error", "not found / password reset token invalid");
            return "authentication/resetPasswordPage";
        }


        LocalDateTime codeGeneratedTime = user.get().getCodeGeneratedTime();
        if (Duration.between(codeGeneratedTime, LocalDateTime.now()).toMinutes() > 1) {
            model.addAttribute("error", "not found / password reset token invalid");
            return "authentication/resetPasswordPage";
        }

        user.get().setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));

        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getConfirmPassword())) {
            model.addAttribute("errorMessage", "Passwords do not match");

            return "authentication/resetPasswordPage";
        }


        user.get().setConfirmationToken(null);
        user.get().setCodeGeneratedTime(null);

        userRepository.save(user.get());

        model.addAttribute("authenticationDto", new AuthenticationDto());

        return "authentication/loginPage";

    }

    private void sendTransactionFile(String htmlContent, String email) {
        EmailDto emailDto = EmailDto.builder()
                .recipient(email)
                .subject("CONFIRMATION MAIL")
                .messageBody(htmlContent)
                .build();

        emailServiceImpl.sendEmailAlert(emailDto);
    }
}