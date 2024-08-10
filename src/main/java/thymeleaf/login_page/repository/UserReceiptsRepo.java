package thymeleaf.login_page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymeleaf.login_page.entity.UserReceipts;

@Repository
public interface UserReceiptsRepo extends JpaRepository<UserReceipts, Integer> {
    UserReceipts findByEmail(String email);

}
