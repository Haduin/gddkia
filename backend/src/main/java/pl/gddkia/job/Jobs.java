package pl.gddkia.job;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gddkia.group.Group;

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
    private Double quantity;
    private String subType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
}
