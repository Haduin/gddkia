package pl.gddkia.estimate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gddkia.branch.Branch;
import pl.gddkia.job.Jobs;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "estimate_branch",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    private List<Branch> branches;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "estimate_jobs",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "jobs_id")
    )
    private List<Jobs> jobs;

}
