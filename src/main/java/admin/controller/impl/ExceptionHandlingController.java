package admin.controller.impl;

import admin.exception.UserAlreadyExistsException;
import admin.exception.UserNotFoundException;
import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import java.sql.SQLException;

import static org.apache.commons.logging.LogFactory.getLog;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

/**
 * Handler for intercepting tyical exceptions and returning human-understandable response
 */
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static final Log LOG = getLog(UserController.class);

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        LOG.error(e.getMessage(), e);
        return status(CONFLICT).body(e.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
        LOG.error(e.getMessage(), e);
        return status(NOT_FOUND).body(e.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({SQLException.class,
            DataAccessException.class,
            PersistenceException.class})
    public ResponseEntity handleSqlException(Exception e) {
        LOG.error(e.getMessage(), e);
        return badRequest().body(e.getMessage());
    }
}
