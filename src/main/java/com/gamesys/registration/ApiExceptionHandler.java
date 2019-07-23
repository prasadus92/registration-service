package com.gamesys.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.gamesys.registration.exception.GenericException;
import com.gamesys.registration.exception.IssuerIdNumberBlockedException;
import com.gamesys.registration.exception.MinimumAgeCriteriaFailedException;
import com.gamesys.registration.exception.UserAlreadyExistsException;
import com.gamesys.registration.model.dto.ErrorDto;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.error("Request isn't valid", ex);
        return new ResponseEntity(ErrorDto.builder().message(ex.getMessage()).build(), headers, status);
    }

    @ExceptionHandler (UserAlreadyExistsException.class)
    public final ResponseEntity<Object> handleUserAlreadyExistsError(UserAlreadyExistsException ex, WebRequest request) {
        log.warn("Handling UserAlreadyExistsException: request - {}, error - {}", request.getDescription(false), ex.getMessage());
        return new ResponseEntity<>(ErrorDto.builder().message(ex.getMessage()).build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler (MinimumAgeCriteriaFailedException.class)
    public final ResponseEntity<Object> handleMinimumAgreCriteriaError(MinimumAgeCriteriaFailedException ex, WebRequest request) {
        log.warn("Handling MinimumAgeCriteriaFailedException: request - {}, error - {}", request.getDescription(false), ex.getMessage());
        return new ResponseEntity<>(ErrorDto.builder().message(ex.getMessage()).build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler (IssuerIdNumberBlockedException.class)
    public final ResponseEntity<Object> handleIssuerIdNumberBlockedError(IssuerIdNumberBlockedException ex, WebRequest request) {
        log.warn("Handling IssuerIdNumberBlockedException: request - {}, error - {}", request.getDescription(false), ex.getMessage());
        return new ResponseEntity<>(ErrorDto.builder().message("Error occurred during registration").build(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler (GenericException.class)
    public final ResponseEntity<Object> handleGenericError(GenericException ex, WebRequest request) {
        log.warn("Handling GenericException: request - {}, error - {}", request.getDescription(false), ex.getMessage());
        return new ResponseEntity<>(ErrorDto.builder().message(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
