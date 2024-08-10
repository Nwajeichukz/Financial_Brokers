package thymeleaf.login_page.service.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import thymeleaf.login_page.dto.*;
import thymeleaf.login_page.entity.Account;
import thymeleaf.login_page.entity.User;
import thymeleaf.login_page.entity.UserReceipts;
import thymeleaf.login_page.repository.AccountRepo;
import thymeleaf.login_page.repository.UserReceiptsRepo;
import thymeleaf.login_page.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
    private final UserRepository userRepository;

    private final AccountRepo accountRepo;

    private final UserReceiptsRepo userReceiptsRepo;
    @Override
    public String updatePassword(UpdatePassword updatePassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String userEmail = userDetails.getUsername();
            String userDetailsPassword = userDetails.getPassword();

            Optional<User> userFromDb = userRepository.findByEmail(userEmail);

            if(Objects.equals(updatePassword.getOldPassword(), userDetailsPassword)){
                if(Objects.equals(updatePassword.getNewPassword(), updatePassword.getConfirmPassword())){
                    userFromDb.get().setPassword(updatePassword.getNewPassword());

                    userRepository.save(userFromDb.get());

                    return "successful";
                }else {
                    return "new and confirm password do not match";
                }
            }

            return "wrong password";

        }

        return "user not authenticated";
        }

    @Override
    public AccountDto getAccountDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String userEmail = userDetails.getUsername();
            String userDetailsPassword = userDetails.getPassword();

            Account accountFromDb = accountRepo.findByEmail(userEmail);

            AccountDto accountDto = new AccountDto();
            accountDto.setAccountBalance(accountFromDb.getAccountBalance());
            accountDto.setTodayTrade(accountFromDb.getTodayTrade());
            accountDto.setRevenue(accountFromDb.getRevenue());

            return accountDto;
        }
        return new AccountDto();
    }

    @Override
    public UserProfileDto getUSerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String userEmail = userDetails.getUsername();
            String userDetailsPassword = userDetails.getPassword();

            Optional<User> userFromDb = userRepository.findByEmail(userEmail);

            UserProfileDto userProfileDto = new UserProfileDto();
            userProfileDto.setEmail(userFromDb.get().getEmail());
            userProfileDto.setUserName(userFromDb.get().getUserName());
            userProfileDto.setFullName(userFromDb.get().getFullName());
            userProfileDto.setPhoneNumber(userFromDb.get().getPhoneNumber());
            userProfileDto.setCountry(userFromDb.get().getCountry());

            return userProfileDto;
        }
            return new UserProfileDto();
    }

    @Override
    public UserReceiptsDto getUserReceipts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String userEmail = userDetails.getUsername();

            UserReceipts userReceiptsFromDb = userReceiptsRepo.findByEmail(userEmail);

            UserReceiptsDto userReceiptsDto = new UserReceiptsDto();
            userReceiptsDto.setAmount(userReceiptsFromDb.getAmount());
            userReceiptsDto.setDateOfTransaction(userReceiptsFromDb.getDateOfTransaction().toString());
            userReceiptsDto.setTimeOfTransaction(userReceiptsFromDb.getTimeOfTransaction().toString());
            userReceiptsDto.setTransferType(userReceiptsFromDb.getTransferType());

            return userReceiptsDto;
        }


        return new UserReceiptsDto();
    }

    @Override
    public String deposit(DepositDto depositDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String userEmail = userDetails.getUsername();

            Account userAccount = accountRepo.findByEmail(userEmail);

            if (userAccount != null){
                userAccount.setAccountBalance(depositDto.getAmount() + userAccount.getAccountBalance());
                userAccount.setRevenue(depositDto.getAmount() + userAccount.getRevenue());
                userAccount.setTodayTrade(userAccount.getTodayTrade() + 1);

                accountRepo.save(userAccount);

                UserReceipts userReceipts = new UserReceipts();
                userReceipts.setAmount(depositDto.getAmount());
                userReceipts.setDateOfTransaction(LocalDate.now());
                userReceipts.setTimeOfTransaction(LocalTime.now());
                userReceipts.setTransferType("DEBIT");

                userReceiptsRepo.save(userReceipts);


                return "successful";
            }else{

                userAccount.setAccountBalance(depositDto.getAmount());
                userAccount.setRevenue(depositDto.getAmount());
                userAccount.setEmail(userEmail);
                userAccount.setTodayTrade(1);

                accountRepo.save(userAccount);

                UserReceipts userReceipts = new UserReceipts();
                userReceipts.setEmail(userEmail);
                userReceipts.setAmount(depositDto.getAmount());
                userReceipts.setDateOfTransaction(LocalDate.now());
                userReceipts.setTimeOfTransaction(LocalTime.now());
                userReceipts.setTransferType("DEBIT");


                userReceiptsRepo.save(userReceipts);

                return "success";
            }



        }

        return "does not correspond";
    }
}

