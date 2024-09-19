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
        Double managedNetworkLength,
        String roadSectionAtDistrictHeadquarters,
        Double geoCoordinateX,
        Double geoCoordinateY,
        String notes
) {
}
