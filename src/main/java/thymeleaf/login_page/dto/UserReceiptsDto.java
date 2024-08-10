package thymeleaf.login_page.dto;

import lombok.Data;

@Data
public class UserReceiptsDto {

    private String dateOfTransaction;

    private String timeOfTransaction;

    private double amount;

    private String transferType;
}