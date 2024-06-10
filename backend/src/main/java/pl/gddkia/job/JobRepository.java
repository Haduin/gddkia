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

    @Query(value = """
                SELECT s.description, s.sst, s.unit, s.sub_type, s.group_type, AVG(s.cost_estimate * s.quantity) AS cost_estimate
                FROM jobs s,
                 estimate e,
                 estimate_jobs es,
                 estimate_branch eb,
                 branch b
                 where e.estimate_id = es.estimate_id
                  and es.jobs_id = s.job_id
                  and e.estimate_id = eb.estimate_id
                  and eb.branch_id = b.branch_id
                  and b.branch = ?1
                  and b.region in (?2)
                  and b.section in (?3)
                  and e.date_from >= to_date(?4,'YYYY-MM-DD')
                  and e.date_to <= to_date(?5,'YYYY-MM-DD')
                GROUP BY e.estimate_id, description, sst, unit, sub_type, group_type;
            """, nativeQuery = true)
    List<Object[]> getExample(String branch, String[] regions, String[] sections, String dateFrom, String dateTo);
}
