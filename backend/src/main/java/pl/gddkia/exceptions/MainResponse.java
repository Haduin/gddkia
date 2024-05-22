package pl.gddkia.exceptions;

public sealed interface MainResponse {
    record EstimateSuccessful(String message) implements MainResponse {
    }

    record UserLogged(String token) implements MainResponse {
    }
}