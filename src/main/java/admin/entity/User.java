package admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(name = "uc_login", columnNames = "login")})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "personal_info")
    private String personalInfo;

    @Column(name = "address")
    private String address;
}