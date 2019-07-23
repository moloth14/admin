package admin.exception;

import static java.lang.String.format;

public class UserAlreadyExistsException extends RuntimeException {

    private String message;

    public UserAlreadyExistsException(Long userId) {
        message = format("User with id %d already exists", userId);
    }

    public UserAlreadyExistsException(String login) {
        message = format("User with login %s already exists", login);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
