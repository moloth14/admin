package admin.utils;

import admin.entity.User;

import static java.time.LocalDate.of;

public class TestUtils {

    public static User createUser(int i) {
        return new User("name" + i, "surname" + i, of(2000, 1, i), "login" + i, "password" + i, "personalInfo" + i,
                        "address" + i);
    }
}
