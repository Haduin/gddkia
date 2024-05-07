package pl.gddkia.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository repository;

    @Override
    public List<AvgJobsDetails> getAllJobsWithMeanEstimate() {
        return repository.getAvgQuality2()
                .stream()
                .map(this::mapToAvgJobsDetails)
                .toList();

    }

    @Override
    public List<JobRest> getAllJobs() {
        return repository.findAll()
                .stream()
                //TODO change it to Mapper
                .map(job -> new JobRest(job.getSST(), job.getDescription(), job.getUnit(), job.getCostEstimate(), job.getQuantity(), job.getSubType()))
                .toList();
    }

    private AvgJobsDetails mapToAvgJobsDetails(Object[] result) {
        return new AvgJobsDetails(
                (String) result[0],
                (String) result[1],
                (String) result[2],
                (Double) result[3]
        );
    }


}
