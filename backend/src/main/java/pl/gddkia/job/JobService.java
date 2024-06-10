package pl.gddkia.job;

import java.util.List;

public interface JobService {
    List<AvgJobsDetails> getAllJobsWithMeanEstimate();
    List<AvgJobsDetails> getAllJobs(FilteredJobsDetails details);
}
