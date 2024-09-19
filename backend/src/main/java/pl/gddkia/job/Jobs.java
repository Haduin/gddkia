package pl.gddkia.job;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.gddkia.estimate.Estimate;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "jobs")
public class Jobs {

    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String SST;
    private String description;
    private String unit;
    @Column(columnDefinition = "double precision default 1")
    private Double costEstimate;
    @Column(columnDefinition = "double precision default 1")
    private Double quantity;
    private String groupType;
    private String subType;

    @ManyToMany(mappedBy = "jobs")
    private Set<Estimate> estimates;

    public void addEstimate(Estimate estimate) {
        this.estimates.add(estimate);
        estimate.getJobs().forEach(e -> e.estimates.add(estimate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Jobs jobs = (Jobs) o;

        return new EqualsBuilder().append(id, jobs.id).append(SST, jobs.SST).append(description, jobs.description).append(unit, jobs.unit).append(costEstimate, jobs.costEstimate).append(quantity, jobs.quantity).append(groupType, jobs.groupType).append(subType, jobs.subType).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(SST).append(description).append(unit).append(costEstimate).append(quantity).append(groupType).append(subType).toHashCode();
    }
}
