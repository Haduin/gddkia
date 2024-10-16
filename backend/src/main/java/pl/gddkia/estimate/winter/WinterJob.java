package pl.gddkia.estimate.winter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gddkia.estimate.Estimate;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WinterJob {
    @Id
    @Column(name = "winter_job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "road_number")
    private String roadNumber;
    @Column(name = "nr")
    private String nr;
    @Column(name = "description")
    private String description;
    @Column(name = "road_length")
    private Double roadLength;
    @Column(name = "standard_zud")
    private String zudStandard;

    @Column(name = "net_price_fence")
    private String netPriceFence;
    @Column(name = "km_fence")
    private String kmFence;

    @Column(name = "net_price_snow")
    private String netPriceSnow;
    @Column(name = "km_snow")
    private String kmSnow;
    @Column(name = "km_quantity_snow")
    private String snowQuantitySnow;

    @ManyToMany(mappedBy = "winterJobs")
    private Set<Estimate> estimates;

}
