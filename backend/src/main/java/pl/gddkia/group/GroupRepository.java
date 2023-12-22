package pl.gddkia.group;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gddkia.job.JobRest;

import java.util.List;
import java.util.Map;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByEstimateId(Long id);
}
