package pl.gddkia.job;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.gddkia.group.Group;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job")
public class Job {

    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String SST;
    private String description;
    private String unit;
    private Double costEstimate;
    private Double quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", SST='" + SST + '\'' +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", costEstimate=" + costEstimate +
                ", quantity=" + quantity +
                ", group=" + group +
                '}';
    }
}
