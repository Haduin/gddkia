package pl.gddkia.common.winter;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gddkia.estimate.winter.WinterJobA;

public interface WinterJobRepository extends JpaRepository<WinterJobA, Long> {
}
