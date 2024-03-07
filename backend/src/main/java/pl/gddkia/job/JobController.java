package pl.gddkia.job;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping()
    public List<AvgJobsDetails> getAllEstimate(){
        return jobService.getAllJobsWithMeanEstimate();
    }
}
