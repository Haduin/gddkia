package pl.gddkia.job;

public record FilteredJobsDetails(
        String startDate,
        String endDate,
        String selectedBranch,
        String[] selectedRegion,
        String[] selectedSection
) {
}
