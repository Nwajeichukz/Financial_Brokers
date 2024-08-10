package thymeleaf.login_page.dto;

import lombok.Data;

@Data
public class DepositDto {
    private final double  amount;
    private final String paymentMethod;

}
