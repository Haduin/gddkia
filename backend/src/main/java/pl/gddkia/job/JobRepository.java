package pl.gddkia.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Jobs, Long> {

    //TODO find why this is not working
    @Query(value = "SELECT description, sst, unit, AVG(cost_estimate * quantity) AS cost_estimate FROM jobs GROUP BY description,sst,unit;", nativeQuery = true)
    List<AvgJobsDetails> getAvgQuality();

    //TODO but this is working
    @Query(value = "SELECT description, sst, unit, AVG(cost_estimate * quantity) AS cost_estimate FROM jobs GROUP BY description,sst,unit;", nativeQuery = true)
    List<Object[]> getAvgQuality2();
}
