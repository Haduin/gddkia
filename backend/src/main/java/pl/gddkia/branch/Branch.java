package pl.gddkia.branch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gddkia.estimate.Estimate;

import java.util.List;

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
    private double managedNetworkLength;
    private String roadSectionAtDistrictHeadquarters;
    private double geoCoordinateX;
    private double geoCoordinateY;
    private String notes;


    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Estimate> estimates;
}

