package pl.gddkia.estimate.winter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String description;
    private Double latitude;
    @Column(name = "standard_zud")
    private String zudStandard;
}
