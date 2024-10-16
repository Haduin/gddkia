package pl.gddkia.estimate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.gddkia.branch.Branch;
import pl.gddkia.estimate.winter.WinterJob;
import pl.gddkia.job.Jobs;

import java.time.LocalDate;
import java.util.Set;

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
    @Column(name = "date_from", columnDefinition = "date")
    private LocalDate dateFrom;
    @Column(name = "date_to", columnDefinition = "date")
    private LocalDate dateTo;
    @Column(name = "road_length")
    private Long roadLength;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "estimate_branch",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    private Set<Branch> branches;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "estimate_jobs",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "jobs_id")
    )
    private Set<Jobs> jobs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "estimate_winter_jobs",
            joinColumns = @JoinColumn(name = "estimate_id"),
            inverseJoinColumns = @JoinColumn(name = "winter_jobs_id")
    )
    private Set<WinterJob> winterJobs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Estimate estimate = (Estimate) o;

        return new EqualsBuilder().append(id, estimate.id).append(contractName, estimate.contractName).append(dateFrom, estimate.dateFrom).append(dateTo, estimate.dateTo).append(roadLength, estimate.roadLength).append(branches, estimate.branches).append(jobs, estimate.jobs).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(contractName).append(dateFrom).append(dateTo).append(roadLength).append(branches).append(jobs).toHashCode();
    }
}
