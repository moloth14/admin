package admin.exception;

import static java.lang.String.format;

public class UserAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "User with id %d already exists";

    private Long userId;

    public UserAlreadyExistsException(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getMessage() {
        return format(MESSAGE, userId);
    }
}
