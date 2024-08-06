package thymeleaf.login_page.service.email;

import thymeleaf.login_page.dto.EmailDto;

public interface EmailService {
    void sendEmailAlert(EmailDto emailDto);

}
