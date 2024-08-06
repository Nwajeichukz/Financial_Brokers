package thymeleaf.login_page;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass=true)
public class LoginPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginPageApplication.class, args);
	}

}
