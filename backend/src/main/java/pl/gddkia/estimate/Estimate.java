package pl.gddkia.estimate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.gddkia.group.Group;
import pl.gddkia.region.Region;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Estimate {
    @Id
    @Column(name = "estimate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "contract_name")
    private String contractName;
    @Column(name = "date_from")
    private OffsetDateTime dateFrom;
    @Column(name = "date_to")
    private OffsetDateTime dateTo;

    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL)
    private List<Group> groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;
}
