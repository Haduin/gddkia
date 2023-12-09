package pl.gddkia.estimate;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import pl.gddkia.branch.BranchRepository;
import pl.gddkia.group.GROUP_NAME;
import pl.gddkia.group.Group;
import pl.gddkia.group.GroupRepository;
import pl.gddkia.job.Job;
import pl.gddkia.job.JobRepository;
import pl.gddkia.region.Region;
import pl.gddkia.region.RegionRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {
    private final JobRepository jobRepository;
    private final GroupRepository groupRepository;
    private final EstimateRepository estimateRepository;
    private final BranchRepository branchRepository;
    private final RegionRepository regionRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void addNewEstimate(final AddNewEstimateRest rest, final InputStream inputStream) throws IOException {

        Workbook workbook = WorkbookFactory.create(inputStream);
        Optional<Region> regionOptional = regionRepository.findByRegionNameEquals(rest.regionName());

        Estimate estimate = new Estimate(null, rest.contractName(), convertStringToOffsetDateTime(rest.dateFrom()), convertStringToOffsetDateTime(rest.dateTo()), null, regionOptional.get());
        regionOptional.get().getEstimates().add(estimate);
        estimateRepository.save(estimate);

        regionOptional.ifPresent(regionRepository::save);

        String sst = "";


        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets() && sheetIndex <= 8; sheetIndex++) {
            Sheet sheetAt = workbook.getSheetAt(sheetIndex);
            List<Job> jobList = new ArrayList<>();
            Group group = new Group(null, GROUP_NAME.findByValue(sheetIndex + 1).name(), null, estimate);
            groupRepository.save(group);

            for (int i = 0; i < sheetAt.getLastRowNum(); i++) {
                Row row = sheetAt.getRow(i);
                if (!isRowEmpty(row)) {
                    if (row.getCell(2).toString() != "") {
                        sst = row.getCell(2).toString();
                    }

                    jobList.add(
                            new Job(null,
                                    sst,
                                    row.getCell(3).toString(),
                                    row.getCell(4).toString(),
                                    row.getCell(5).toString() != null && !row.getCell(5).getCellType().name().isBlank() && !row.getCell(5).toString().isEmpty() ? Double.parseDouble(row.getCell(5).toString()) : null,
                                    row.getCell(6).toString() != null && !row.getCell(6).getCellType().name().isBlank() && !row.getCell(6).toString().isEmpty() ? Double.parseDouble(row.getCell(6).toString()) : null,
                                    group
                            ));


                }
            }
            jobRepository.saveAll(jobList);
        }


        workbook.close();
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        } else {
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                if (cell.getCellType() != CellType.BLANK) {
                    String cellValue = new DataFormatter().formatCellValue(cell).trim();
                    if (!cellValue.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public OffsetDateTime convertStringToOffsetDateTime(String dateString) {
        try {
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return OffsetDateTime.of(localDate.atStartOfDay(), OffsetDateTime.now().getOffset());
        } catch (Exception e) {
            return null;
        }
    }
}
