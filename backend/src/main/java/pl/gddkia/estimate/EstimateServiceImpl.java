package pl.gddkia.estimate;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import pl.gddkia.branch.BranchRepository;
import pl.gddkia.exceptions.MainResponse;
import pl.gddkia.exceptions.RegionNotFoundException;
import pl.gddkia.group.GROUP_NAME;
import pl.gddkia.group.Group;
import pl.gddkia.group.GroupRepository;
import pl.gddkia.job.Job;
import pl.gddkia.job.JobRepository;
import pl.gddkia.job.JobRest;
import pl.gddkia.region.Region;
import pl.gddkia.region.RegionRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {
    private final JobRepository jobRepository;
    private final GroupRepository groupRepository;
    private final EstimateRepository estimateRepository;
    private final BranchRepository branchRepository;
    private final RegionRepository regionRepository;


    private final Logger LOGGER = LogManager.getLogger(EstimateServiceImpl.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Transactional
    @Override
    public MainResponse addNewEstimate(final AddNewEstimateRest rest, final InputStream inputStream) throws IOException {
        LOGGER.info("Start saving data from file");
        /*
            TODO add inputStream file validation
            TODO repository objects finding saving
         */
        Workbook workbook = WorkbookFactory.create(inputStream);
        Region regionOptional = regionRepository.findByRegionNameEquals(rest.regionName())
                .orElseThrow(() -> new RegionNotFoundException(rest.regionName()));

        Estimate estimate = new Estimate(null, rest.contractName(), convertStringToOffsetDateTime(rest.dateFrom()), convertStringToOffsetDateTime(rest.dateTo()), null, regionOptional);
        regionOptional.getEstimates().add(estimate);
        estimateRepository.save(estimate);

        regionRepository.save(regionOptional);

        String sst = "";

        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets() && sheetIndex <= 8; sheetIndex++) {
            Sheet sheetAt = workbook.getSheetAt(sheetIndex);
            List<Job> jobList = new ArrayList<>();
            Group group = new Group(null, GROUP_NAME.findByValue(sheetIndex + 1).name(), null, estimate);
            groupRepository.save(group);
            boolean skipRows = true;

            String localSubType = "";
            for (int i = 8; i < sheetAt.getLastRowNum(); i++) {
                Row row = sheetAt.getRow(i);

                //TODO add step validation
                if (isRowEmpty(row)) {
                    continue;
                }

                //Determines the subtype
                String checkIfSubtype = getContentIfNotAllCellsEmpty(row);
                if (StringUtils.isNotEmpty(checkIfSubtype)) {
                    localSubType = checkIfSubtype;
                    continue;
                }
                //Determines when to stop reading Cells in file
                if (skipRows && row.getCell(0) != null && row.getCell(0).toString().contains("RAZEM")) {
                    skipRows = false;
                    continue;
                }

                if (!isRowEmpty(row) && skipRows) {
                    if (!row.getCell(2).toString().isEmpty()) {
                        sst = row.getCell(2).toString();
                    }

                    jobList.add(
                            new Job(
                                    null,
                                    sst,
                                    row.getCell(3).toString(),
                                    row.getCell(4).toString(),
                                    validateUnitPrice(row.getCell(5)),
                                    validateUnitPrice(row.getCell(6)),
                                    localSubType,
                                    group
                            )
                    );
                }
            }
            jobRepository.saveAll(jobList);
        }
        workbook.close();
        LOGGER.info("End of data");
        return new MainResponse.EstimateSuccessful("OK");
    }

    //TODO change to predicate validator or sth better
    private Double validateUnitPrice(Cell cell) {
        return cell != null &&
                !cell.getCellType().name().isBlank() &&
                !cell.toString().isEmpty() ?
                parseStringCellValue(cell.toString()) : null;
    }

    private Double parseStringCellValue(String stringValue) {
        String numericValue = stringValue.substring(0, stringValue.length() - 3).trim();
        if (stringValue.endsWith(" zł")) {
            if (numericValue.contains(".") || numericValue.contains(",")) {
                numericValue = numericValue.replace(',', '.');
            }
        }
        try {
            return Double.parseDouble(numericValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<EstimateRest> getEstimate() {
        //TODO add Mapper objects for this below
        return estimateRepository.findAll()
                .stream()
                .map(estimate -> new EstimateRest(
                        estimate.getId(),
                        estimate.getContractName(),
                        estimate.getDateFrom().toString(),
                        estimate.getDateTo().toString(),
                        estimate.getRegion().getRegionName(),
                        estimate.getRegion().getBranch().getBranchName(),
                        groupRepository.findAllByEstimateId(estimate.getId())
                                .stream()
                                .collect(
                                        Collectors.toMap(
                                                Group::getGroupName,
                                                group -> group.getJobs().stream().map(job -> new JobRest(
                                                        job.getSST(),
                                                        job.getDescription(),
                                                        job.getUnit(),
                                                        job.getCostEstimate(),
                                                        job.getQuantity(),
                                                        job.getSubType()
                                                )).toList()
                                        )
                                )

                )).toList();
    }

    @Override
    public EstimateRest getEstimate(String estimateName) {
        return null;
    }

    public OffsetDateTime convertStringToOffsetDateTime(String dateString) {
        try {
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return OffsetDateTime.of(localDate.atStartOfDay(), OffsetDateTime.now().getOffset());
        } catch (Exception e) {
            return null;
        }
    }

    private String getContentIfNotAllCellsEmpty(Row row) {
        boolean hasNonEmptyCell = false;

        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);

            //checks if something is under index of cell
            if (i == 1 || i == 3) {
                continue;
            }

            if (cell != null && cell.getCellType() != CellType.BLANK) {
                hasNonEmptyCell = true;
                break;
            }
        }

        return !hasNonEmptyCell ? row.getCell(3).toString() : null;
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
}
