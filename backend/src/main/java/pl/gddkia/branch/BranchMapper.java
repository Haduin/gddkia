package pl.gddkia.branch;

public class BranchMapper {
    public static Branch mapToEntity(RoadData roadData) {
        if (roadData != null) {
            return Branch.builder()
                    .branch(roadData.branch())
                    .region(roadData.region())
                    .section(roadData.section())
                    .roadNumber(roadData.roadNumber())
                    .city(roadData.city())
                    .addressZipCity(roadData.addressZipCity())
                    .addressStreet(roadData.addressStreet())
                    .phone(roadData.phone())
                    .managedNetworkLength(roadData.managedNetworkLength())
                    .roadSectionAtDistrictHeadquarters(roadData.roadSectionAtDistrictHeadquarters())
                    .geoCoordinateX(roadData.geoCoordinateX())
                    .geoCoordinateY(roadData.geoCoordinateY())
                    .notes(roadData.notes())
                    .build();

        } else return null;
    }
    public static Branch mapToEntity(RoadData roadData,Long branchId) {
        if (roadData != null) {
            return Branch.builder()
                    .id(branchId)
                    .branch(roadData.branch())
                    .region(roadData.region())
                    .section(roadData.section())
                    .roadNumber(roadData.roadNumber())
                    .city(roadData.city())
                    .addressZipCity(roadData.addressZipCity())
                    .addressStreet(roadData.addressStreet())
                    .phone(roadData.phone())
                    .managedNetworkLength(roadData.managedNetworkLength())
                    .roadSectionAtDistrictHeadquarters(roadData.roadSectionAtDistrictHeadquarters())
                    .geoCoordinateX(roadData.geoCoordinateX())
                    .geoCoordinateY(roadData.geoCoordinateY())
                    .notes(roadData.notes())
                    .build();

        } else return null;
    }

    public static BranchRest mapToRest(Branch branch) {
        if (branch != null) {
            return new BranchRest(
                    branch.getId(),
                    branch.getBranch(),
                    branch.getRegion(),
                    branch.getSection(),
                    branch.getRoadNumber(),
                    branch.getCity(),
                    branch.getAddressZipCity(),
                    branch.getAddressStreet(),
                    branch.getPhone(),
                    branch.getManagedNetworkLength(),
                    branch.getRoadSectionAtDistrictHeadquarters(),
                    branch.getGeoCoordinateX(),
                    branch.getGeoCoordinateY(),
                    branch.getNotes()
            );
        } else return null;
    }
    public static Branch mapToEntity(Branch oldBranch, UpdateBranchDto branchToUpdate) {
        if (branchToUpdate != null) {
            return Branch.builder()
                    .id(oldBranch.getId())
                    .branch(branchToUpdate.branch())
                    .region(branchToUpdate.region())
                    .section(branchToUpdate.section())
                    .roadNumber(branchToUpdate.roadNumber())
                    .city(branchToUpdate.city())
                    .addressZipCity(branchToUpdate.addressZipCity())
                    .addressStreet(branchToUpdate.addressStreet())
                    .phone(branchToUpdate.phone())
                    .managedNetworkLength(branchToUpdate.managedNetworkLength())
                    .roadSectionAtDistrictHeadquarters(branchToUpdate.roadSectionAtDistrictHeadquarters())
                    .geoCoordinateX(branchToUpdate.geoCoordinateX())
                    .geoCoordinateY(branchToUpdate.geoCoordinateY())
                    .notes(branchToUpdate.notes())
                    .build();

        } else return null;
    }
}
