package pl.gddkia.exceptions;

public sealed interface MainError {
    record A(String wszstkoOk) implements MainError {}
    record B() implements MainError {}
    record C() implements MainError {}
}