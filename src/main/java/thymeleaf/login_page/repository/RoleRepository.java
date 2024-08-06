package thymeleaf.login_page.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thymeleaf.login_page.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

}
