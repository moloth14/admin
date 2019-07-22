package admin.service;

import admin.UserApplication;
import admin.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static admin.utils.TestUtils.createUser;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.junit.Assert.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

/**
 * Tests for service logic
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserApplication.class)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * test case: if password is null, create and update methods set empty string instead
     */
    @Test
    public void nullPasswordTest() {
        User user = createUser(1);
        user.setPassword(null);
        userService.createUser(user);

        assertEquals(EMPTY, getPassword());

        User newUser = createUser(2);
        newUser.setPassword(null);
        userService.updateUser(1L, newUser);

        assertEquals(EMPTY, getPassword());
    }

    /**
     * test cases: if password is empty, create and update methods leave it empty
     */
    @Test
    public void emptyPasswordTest() {
        User user = createUser(1);
        user.setPassword(EMPTY);
        userService.createUser(user);

        assertEquals(EMPTY, getPassword());

        User newUser = createUser(2);
        newUser.setPassword(EMPTY);
        userService.updateUser(1L, newUser);

        assertEquals(EMPTY, getPassword());

        // if new password is empty, update method leaves old password
        newUser.setPassword("non-empty-password");
        userService.updateUser(1L, newUser);
        String newPassword = getPassword();
        newUser.setPassword(EMPTY);
        userService.updateUser(1L, newUser);

        assertEquals(newPassword, getPassword());
    }

    /**
     * testing that create and update methods encode password and could verify it
     */
    @Test
    public void matchesPasswordTest() {
        User user = createUser(1);
        String password = "password";
        user.setPassword(password);
        userService.createUser(user);

        String encodedPassword = getPassword();
        assertNotEquals(password, encodedPassword);
        assertTrue(passwordEncoder.matches(password, encodedPassword));

        User newUser = createUser(2);
        String newPassword = "newPassword";
        newUser.setPassword(newPassword);
        userService.updateUser(1L, newUser);

        String encodedNewPassword = getPassword();
        assertNotEquals(newPassword, encodedNewPassword);
        assertTrue(passwordEncoder.matches(newPassword, encodedNewPassword));
    }

    private String getPassword() {
        return userService.getUser(1L)
                .getPassword();
    }
}
