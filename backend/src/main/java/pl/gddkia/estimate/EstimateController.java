package pl.gddkia.estimate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.gddkia.exceptions.MainResponse;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/estimate")
public class EstimateController {
    private final EstimateService estimateService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public MainResponse addNewEstimate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("companyName") String companyName,
            @RequestParam("contractName") String contractName,
//            @RequestParam("dateFrom") String dateFrom,
//            @RequestParam("dateTo") String dateTo,
            @RequestParam("branchName") String branchName,
            @RequestParam("regionName") String regionName,
            @RequestParam("sectionName") String sectionName
    ) throws IOException {
        return estimateService.addNewEstimate(new AddNewEstimateRest(
                companyName,
                contractName,
                null,
                null,
                branchName,
                regionName,
                sectionName
        ), file.getInputStream());
    }


    @GetMapping()
    public List<EstimateRest> getAllEstimate() {
        return estimateService.getEstimate();
    }
}
