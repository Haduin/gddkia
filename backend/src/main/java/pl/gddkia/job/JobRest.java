package pl.gddkia.job;

public record JobRest(
        String SST,
        String description,
        String unit,
        Double costEstimate,
        Double quantity,
        String subType
) {
}
