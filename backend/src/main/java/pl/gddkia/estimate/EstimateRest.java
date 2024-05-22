package pl.gddkia.estimate;

import pl.gddkia.job.JobRest;

import java.util.List;
import java.util.Map;

public record EstimateRest(
        Long id,
        String contractName,
        String dateFrom,
        String dateTo,
        String branch,
        Map<String, List<JobRest>>jobsPerGroup
        ) {
}
