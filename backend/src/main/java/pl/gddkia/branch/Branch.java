package pl.gddkia.branch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.gddkia.estimate.Estimate;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Branch {
    @Id
    @Column(name = "branch_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String branch;
    private String region;
    private String section;
    private String roadNumber;
    private String city;
    private String addressZipCity;
    private String addressStreet;
    private String phone;
    private Double managedNetworkLength;
    private String roadSectionAtDistrictHeadquarters;
    private Double geoCoordinateX;
    private Double geoCoordinateY;
    private String notes;


    @ManyToMany(mappedBy = "branches")
    private Set<Estimate> estimates;

    public void addEstimate(Estimate estimate) {
        this.estimates.add(estimate);
        estimate.getBranches().forEach(e -> e.estimates.add(estimate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Branch branch1 = (Branch) o;

        return new EqualsBuilder().append(managedNetworkLength, branch1.managedNetworkLength).append(geoCoordinateX, branch1.geoCoordinateX).append(geoCoordinateY, branch1.geoCoordinateY).append(id, branch1.id).append(branch, branch1.branch).append(region, branch1.region).append(section, branch1.section).append(roadNumber, branch1.roadNumber).append(city, branch1.city).append(addressZipCity, branch1.addressZipCity).append(addressStreet, branch1.addressStreet).append(phone, branch1.phone).append(roadSectionAtDistrictHeadquarters, branch1.roadSectionAtDistrictHeadquarters).append(notes, branch1.notes).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(branch).append(region).append(section).append(roadNumber).append(city).append(addressZipCity).append(addressStreet).append(phone).append(managedNetworkLength).append(roadSectionAtDistrictHeadquarters).append(geoCoordinateX).append(geoCoordinateY).append(notes).toHashCode();
    }
}

