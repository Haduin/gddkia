package pl.gddkia.exceptions;

public class RegionNotFoundException extends RuntimeException {
    public RegionNotFoundException(String region) {
        super(String.format("Region [%s] was not found", region));
    }
}
