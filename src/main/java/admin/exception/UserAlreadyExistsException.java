package admin.exception;

import static java.lang.String.format;

public class UserAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "User with id %d or login %s already exists";

    private Long userId;
    private String login;

    public UserAlreadyExistsException(Long userId, String login) {
        this.userId = userId;
        this.login = login;
    }

    @Override
    public String getMessage() {
        return format(MESSAGE, userId, login);
    }
}
