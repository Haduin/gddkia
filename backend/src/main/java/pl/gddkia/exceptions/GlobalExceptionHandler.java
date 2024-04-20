package pl.gddkia.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegionNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRegionNotFoundException(RegionNotFoundException e) {
        return new ResponseEntity<>(new ErrorDetails(e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<ErrorDetails> handleInvalidCredencialsException(InvalidCredentials e) {
        return new ResponseEntity<>(new ErrorDetails(e.getMessage()), HttpStatus.OK);
    }
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorDetails> handleInternalAuthenticationServiceExceptionsException(InternalAuthenticationServiceException e) {
        return new ResponseEntity<>(new ErrorDetails(e.getMessage()), HttpStatus.OK);
    }
}