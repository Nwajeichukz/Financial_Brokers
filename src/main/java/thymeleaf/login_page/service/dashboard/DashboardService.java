package thymeleaf.login_page.service.dashboard;

import thymeleaf.login_page.dto.*;
import thymeleaf.login_page.entity.Account;

public interface DashboardService {
    String updatePassword(UpdatePassword updatePassword);

    AccountDto getAccountDetails();

    UserProfileDto getUSerProfile();

    UserReceiptsDto getUserReceipts();

    String deposit(DepositDto depositDto);
}
