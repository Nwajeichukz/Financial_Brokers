package thymeleaf.login_page.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DB_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String fullName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String country;
    private String referralId;
    private String password;


    private LocalDateTime codeGeneratedTime;

    private String confirmationToken;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Role roles;


}
