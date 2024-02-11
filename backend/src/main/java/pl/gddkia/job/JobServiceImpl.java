package pl.gddkia.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository repository;

    @Override
    public List<JobRest> getAllJobs() {
        return repository.findAll()
                .stream()
                //TODO change it to Mapper
                .map(job -> new JobRest(job.getSST(), job.getDescription(), job.getUnit(), job.getCostEstimate(), job.getQuantity(), ""))
                .toList();
    }
}
