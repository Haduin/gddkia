package pl.gddkia.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegionNotFoundException.class)
    public ResponseEntity<ErrorDetails> handle(RegionNotFoundException e) {
        return new ResponseEntity<>(new ErrorDetails(e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidCredentials.class)
    public ResponseEntity<ErrorDetails> handle(InvalidCredentials e) {
        return new ResponseEntity<>(new ErrorDetails(e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorDetails> handle(InternalAuthenticationServiceException e) {
        return new ResponseEntity<>(new ErrorDetails(e.getMessage()), HttpStatus.OK);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDetails> handle(ExpiredJwtException e) {
        return new ResponseEntity<>(new ErrorDetails(e.getMessage()), HttpStatus.FORBIDDEN);
    }
}