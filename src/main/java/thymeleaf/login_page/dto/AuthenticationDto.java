package thymeleaf.login_page.dto;

import lombok.Data;

@Data
public class AuthenticationDto {
    private String email;
    private String password;
}
