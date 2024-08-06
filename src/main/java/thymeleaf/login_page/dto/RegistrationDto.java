package thymeleaf.login_page.dto;

import lombok.Data;


@Data
public class RegistrationDto {
    private String fullName;

    private String userName;
    private String email;

    private String phoneNumber;

    private String country;

    private String referralId;

    private String password;

    private String confirmPassword;
}
