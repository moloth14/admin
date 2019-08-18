package admin;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

/**
 * @SpringBootApplication makes all work on configuration and component scan. Don't forget that it
 * scans components only in its sub-packages - if it was moved, add in annotation another packages
 * and classes for scanning.
 */
@SpringBootApplication
public class UserApplication {

  public static void main(String[] args) {
    run(UserApplication.class, args);
  }
}
