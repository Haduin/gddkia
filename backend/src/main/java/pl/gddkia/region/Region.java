package pl.gddkia.region;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gddkia.branch.Branch;
import pl.gddkia.estimate.Estimate;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Region {
    @Id
    @Column(name = "region_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String regionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "region",cascade = CascadeType.ALL)
    private List<Estimate> estimates;
}
