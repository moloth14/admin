package admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * data class for table "user": see README.md for its description
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(name = "uc_login", columnNames = "login")})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "surname", nullable = false)
    @NotBlank
    private String surname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "login", nullable = false)
    @NotBlank
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "personal_info")
    private String personalInfo;

    @Column(name = "address")
    private String address;

    public void setFields(String name, String surname, LocalDate birthDate, String login, String password,
                          String personalInfo, String address) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.login = login;
        this.password = password;
        this.personalInfo = personalInfo;
        this.address = address;
    }
}