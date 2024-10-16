package pl.gddkia.common.winter;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gddkia.estimate.winter.WinterJob;

public interface WinterJobRepository extends JpaRepository<WinterJob, Long> {
}
