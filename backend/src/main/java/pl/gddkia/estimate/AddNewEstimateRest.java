package pl.gddkia.estimate;

public record AddNewEstimateRest(
        String companyName,
        String contractName,
        String dateFrom,
        String dateTo,
        String regionName,
        String branchName
) {
}
