package pl.gddkia.branch;

public record BranchRest(
        Long id,
        String branch,
        String region,
        String section,
        String roadNumber,
        String city,
        String addressZipCity,
        String addressStreet,
        String phone,
        double managedNetworkLength,
        String roadSectionAtDistrictHeadquarters,
        double geoCoordinateX,
        double geoCoordinateY,
        String notes
) {
}
