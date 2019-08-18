package admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * data class for table "user": see README.md for its description
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(name = "uc_login", columnNames = "login")})
@ApiModel(description = "User's table structure.")
public class User {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "User's ID", example = "1")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @ApiModelProperty(notes = "User's name", required = true, example = "John")
    private String name;

    @Column(name = "surname", nullable = false)
    @NotBlank
    @ApiModelProperty(notes = "User's surname", required = true, example = "Snow")
    private String surname;

    @Column(name = "birth_date")
    @ApiModelProperty(notes = "User's date of birth", example = "[2000, 1, 1]")
    private LocalDate birthDate;

    @Column(name = "login", nullable = false)
    @NotBlank
    @ApiModelProperty(notes = "User's login", required = true, example = "king")
    private String login;

    @Column(name = "password")
    @ApiModelProperty(notes = "User's password", example = "of_N0rth")
    private String password;

    @Column(name = "personal_info")
    @ApiModelProperty(notes = "User's personal info", example = "winter is coming")
    private String personalInfo;

    @Column(name = "address")
    @ApiModelProperty(notes = "User's address", example = "Wall")
    private String address;

    public User(String name, String surname, LocalDate birthDate, String login, String password, String personalInfo,
                String address) {
        setFields(name, surname, birthDate, login, personalInfo, address);
        this.password = password;
    }

    public void setFields(String name, String surname, LocalDate birthDate, String login, String personalInfo,
                          String address) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.login = login;
        this.personalInfo = personalInfo;
        this.address = address;
    }
}