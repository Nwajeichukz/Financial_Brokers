package thymeleaf.login_page.util;

import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class UserUtil {


    public static String createAccountNumber(){
        Random random = new Random();
        String sixDigitNumber;

        long randomNumber = Math.abs(random.nextLong());
        String randomString = Long.toString(randomNumber);
        sixDigitNumber = randomString.substring(0, 6);

        return sixDigitNumber;
    }

}
