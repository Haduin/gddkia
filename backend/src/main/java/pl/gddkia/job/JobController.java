package pl.gddkia.job;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @PostMapping()
    public List<AvgJobsDetails> getAllEstimate(@RequestBody FilteredJobsDetails details) {
        return jobService.getAllJobs(details);
    }
}
