package pl.gddkia.estimate;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.gddkia.common.winter.WinterWorkbookService;
import pl.gddkia.exceptions.MainResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/estimate")
public class EstimateController {
    private final EstimateService estimateService;
    private final WinterWorkbookService winterWorkbookService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public MainResponse addNewEstimate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("companyName") String companyName,
            @RequestParam("contractName") String contractName,
            @RequestParam("dateFrom") String dateFrom,
            @RequestParam("dateTo") String dateTo,
            @RequestParam("roadLength") Long roadLength,
            @RequestParam("branchName") String branchName,
            @RequestParam("regionName") String[] regionName,
            @RequestParam("sectionName") String[] sectionName
    ) throws IOException {
        return estimateService.addNewEstimate(new AddNewEstimateRest(
                companyName,
                contractName,
                dateFrom,
                dateTo,
                branchName,
                regionName,
                sectionName,
                roadLength
        ), file.getInputStream());
    }


    @PostMapping(value = "/upload2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void addNewEstimate(@RequestParam("file") MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(9);
            winterWorkbookService.parseWinterASheet(sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping()
    public List<EstimateRest> getAllEstimate() {
        return estimateService.getEstimate();
    }
}
