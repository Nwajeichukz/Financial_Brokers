package thymeleaf.login_page.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "RECEIPTS")
public class UserReceipts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private LocalDate dateOfTransaction;

    private LocalTime timeOfTransaction;

    private double amount;

    private String transferType;
}
