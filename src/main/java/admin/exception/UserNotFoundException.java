package admin.exception;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User with id %d not exists";

    private Long userId;

    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getMessage() {
        return format(MESSAGE, userId);
    }
}
