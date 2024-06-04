package pl.gddkia.estimate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gddkia.group.Group;
import pl.gddkia.branch.Branch;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    @Column(name = "road_length")
    private Long roadLength;

    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL)
    private List<Group> groups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "branch_estimate",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    private List<Branch> branches;

    public void addEstimate(Branch branch) {
        this.branches.add(branch);
        branch.getEstimates().forEach(estimate -> estimate.branches.add(branch));
    }
}
