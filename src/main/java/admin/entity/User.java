package admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "User's table structure.")
public class User {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "Generated user ID", required = true)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @ApiModelProperty(notes = "User's name", required = true)
    private String name;

    @Column(name = "surname", nullable = false)
    @NotBlank
    @ApiModelProperty(notes = "User's surname", required = true)
    private String surname;

    @Column(name = "birth_date")
    @ApiModelProperty(notes = "User's date of birth", example = "[2000, 1, 1]")
    private LocalDate birthDate;

    @Column(name = "login", nullable = false)
    @NotBlank
    @ApiModelProperty(notes = "User's login", required = true)
    private String login;

    @Column(name = "password")
    @ApiModelProperty(notes = "User's password")
    private String password;

    @Column(name = "personal_info")
    @ApiModelProperty(notes = "User's personal info")
    private String personalInfo;

    @Column(name = "address")
    @ApiModelProperty(notes = "User's address")
    private String address;

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