package thymeleaf.login_page.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import thymeleaf.login_page.dto.RegistrationDto;
import thymeleaf.login_page.repository.RoleRepository;
import thymeleaf.login_page.repository.UserRepository;
import thymeleaf.login_page.service.user.AuthenticationServiceImpl;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private MyUserDetailsService myUserDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserRegistration_UserExists() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("nwajeigoddowell@gmail.com");

        when(userRepository.existsByEmail(registrationDto.getEmail())).thenReturn(true);

        String result = authenticationServiceImpl.userRegistration(registrationDto, model);

        verify(model).addAttribute("errorMessage", "User already exists");
        assertEquals("registrationPage", result);
    }


    @Test
    public void testUserRegistration_PasswordMismatch() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setEmail("nwajeigoddowell@gmail.com");
        registrationDto.setPassword("password");
        registrationDto.setConfirmPassword("differentpassword");

        when(userRepository.existsByEmail(registrationDto.getEmail())).thenReturn(false);

        String result = authenticationServiceImpl.userRegistration(registrationDto, model);

        verify(model).addAttribute("error", "Passwords do not match");
        assertEquals("registrationPage", result);
    }

//    @Test
//    public void testUserRegistration_Success() {
//        RegistrationDto registrationDto = new RegistrationDto();
//        registrationDto.setEmail("nwajeigoddowell@gmail.com");
//        registrationDto.setPassword("password");
//        registrationDto.setConfirmPassword("password");
//        registrationDto.setUserName("firstUser");
//        registrationDto.setFullName("first user");
//        registrationDto.setCountry("country");
//        registrationDto.setPhoneNumber("1234567890");
//
//        User user = new User();
//        user.setPassword(passwordEncoder.encode("12345"));
//        user.setUserName("username");
//        user.setFullName("fullname");
//        user.setCountry("country");
//        user.setPhoneNumber("222222");
//
//
//        Role mockRole = new Role();
//        mockRole.setName("USER");
//
//        when(roleRepository.findByName("USER")).thenReturn(mockRole);
//
//        user.setRoles(mockRole);
//
//        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
//
//        String registeredUser = authenticationService.userRegisteration(registrationDto, model);
//
//        verify(userRepository, times(1)).save(any(User.class));
//        assertEquals("welcome", registeredUser);
//    }

//    @Test
//    public void testUserLogin_Success() {
//        AuthenticationDto authDto = new AuthenticationDto();
//        authDto.setEmail("nwajeigoddowell@gmail.com");
//        authDto.setPassword("password");
//
//        User mockUser = new User();
//        mockUser.setEmail(authDto.getEmail());
//        mockUser.setPassword(passwordEncoder.encode(authDto.getPassword()));
//
//        when(myUserDetailsService.loadUserByUsername(authDto.getEmail())).thenReturn((UserDetails) mockUser);
//        when(passwordEncoder.matches("password", mockUser.getPassword())).thenReturn(true);
//        when(jwtService.generateToken((UserDetails) mockUser)).thenReturn("mock-jwt-token");
//
//        String result = authenticationService.userLogin(authDto, model, session);
//
//        verify(session).setAttribute("jwtToken", "mock-jwt-token");
//        verify(session).setAttribute("user", mockUser);
//        assertEquals("redirect:/dashboardPage", result);
//    }
//
//    @Test
//    public void testUserLogin_InvalidPassword() {
//        AuthenticationDto authDto = new AuthenticationDto();
//        authDto.setEmail("nwajeigoddowell@gmail.com");
//        authDto.setPassword("wrongpassword");
//
//        User mockUser = new User();
//        mockUser.setEmail(authDto.getEmail());
//        mockUser.setPassword(passwordEncoder.encode(authDto.getPassword()));
//
//        when(passwordEncoder.matches(authDto.getPassword(), mockUser.getPassword())).thenReturn(false);
//
//        String result = authenticationService.userLogin(authDto, model, session);
//
//        verify(model).addAttribute("error", "Email or password is incorrect");
//        assertEquals("loginPage", result);
//    }




}
