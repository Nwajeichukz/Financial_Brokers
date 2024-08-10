package thymeleaf.login_page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymeleaf.login_page.entity.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    Account findByEmail(String email);
}
