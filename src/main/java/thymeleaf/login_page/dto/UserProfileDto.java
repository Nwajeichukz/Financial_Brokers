package thymeleaf.login_page.dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private String fullName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
    private String state;
    private String postalCode;



}
