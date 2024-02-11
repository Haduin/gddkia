package pl.gddkia.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ErrorDetails {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");

    private final String timestamp;
    private final String message;

    public ErrorDetails(String message) {
        this.timestamp = LocalDateTime.now().format(dateTimeFormatter);
        this.message = message;
    }
}
