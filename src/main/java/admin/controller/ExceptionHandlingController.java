package admin.controller;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

import static org.apache.commons.logging.LogFactory.getLog;
import static org.springframework.http.ResponseEntity.badRequest;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private static final Log LOG = getLog(UserController.class);

    @ExceptionHandler({SQLException.class,
            DataAccessException.class})
    public ResponseEntity handle(Exception e) {
        LOG.error(e.getMessage(), e);
        return badRequest().build();
    }
}
