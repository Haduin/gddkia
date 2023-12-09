package pl.gddkia.estimate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/estimate")
public class EstimateController {
    private final EstimateService estimateService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void addNewEstimate(
            @RequestPart MultipartFile file,
            @RequestParam("companyName") String companyName,
            @RequestParam("contractName") String contractName,
            @RequestParam("dateFrom") String dateFrom,
            @RequestParam("dateTo") String dateTo,
            @RequestParam("regionName") String regionName,
            @RequestParam("branchName") String branchName
    ) throws IOException {
        estimateService.addNewEstimate(new AddNewEstimateRest(
                companyName,
                contractName,
                dateFrom,
                dateTo,
                regionName,
                branchName
        ), file.getInputStream());
    }


    @GetMapping
    public List<EstimateRest> getAllEstimate(){
        return estimateService.getEstimate();
    }
    @GetMapping
    public EstimateRest getAllEstimateByName(@RequestParam String estimateName){
        return estimateService.getEstimate(estimateName);
    }
}
