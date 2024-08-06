package thymeleaf.login_page.service.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import thymeleaf.login_page.Exception.ApiException;
import thymeleaf.login_page.dto.EmailDto;

import javax.mail.internet.MimeMessage;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendEmailAlert(EmailDto emailDto) {
        if (javaMailSender == null) {
            throw new ApiException("JAVA SENDER NOT AVAILABLE");
        }


        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("nwajeigoddowell@gmail.com");
            helper.setTo(emailDto.getRecipient());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getMessageBody(), true); // Set to true for HTML content

            javaMailSender.send(mimeMessage);

            System.out.println("Mail sent Successfully");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }




}