package pl.gddkia.branch;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.gddkia.region.Region;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Branch {
    @Id
    @Column(name = "branch_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "branch_name")
    private String branchName;
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Region> regions;

}
