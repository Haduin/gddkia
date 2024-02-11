package pl.gddkia.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegionNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRegionNotFoundException(RegionNotFoundException e) {
        return new ResponseEntity<>(new ErrorDetails(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}