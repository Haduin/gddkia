package pl.gddkia.job;

import java.util.List;

public interface JobService {
    List<AvgJobsDetails> getAllJobsWithMeanEstimate();
    List<JobRest> getAllJobs();
}
