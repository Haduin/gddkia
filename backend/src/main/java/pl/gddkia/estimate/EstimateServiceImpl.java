package pl.gddkia.estimate;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import pl.gddkia.branch.BranchRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {
    private final JobRepository jobRepository;
    private final GroupRepository groupRepository;
    private final EstimateRepository estimateRepository;
    private final BranchRepository branchRepository;
    private final RegionRepository regionRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Transactional
    @Override       //TODO rewrite this code to be more clear
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
            boolean skipRows = true;

            String localSubType = "";
            for (int i = 8; i < sheetAt.getLastRowNum(); i++) {
                Row row = sheetAt.getRow(i);

                //TODO add step validation
                if (isRowEmpty(row)) {
                    continue;
                }

                String checkIfSubtype = getContentIfNotAllCellsEmpty(row);
                if (StringUtils.isNotEmpty(checkIfSubtype)) {
                    localSubType = checkIfSubtype;
                    System.out.println(localSubType);
                    continue;
                }

                if (skipRows && row.getCell(0) != null && row.getCell(0).toString().contains("RAZEM")) {
                    skipRows = false;
                    continue;
                }

                if (!isRowEmpty(row) && skipRows) {
                    if (row.getCell(2).toString() != "") {
                        sst = row.getCell(2).toString();
                    }

                    jobList.add(
                            new Job(
                                    null,
                                    sst,
                                    row.getCell(3).toString(),
                                    row.getCell(4).toString(),
                                    row.getCell(5).toString() != null && !row.getCell(5).getCellType().name().isBlank() && !row.getCell(5).toString().isEmpty() ? Double.parseDouble(row.getCell(5).toString()) : null,
                                    row.getCell(6).toString() != null && !row.getCell(6).getCellType().name().isBlank() && !row.getCell(6).toString().isEmpty() ? Double.parseDouble(row.getCell(6).toString()) : null,
                                    localSubType,
                                    group
                            )
                    );


                }
            }
            jobRepository.saveAll(jobList);
        }



        workbook.close();
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

    private static String getContentIfNotAllCellsEmpty(Row row) {
        boolean hasNonEmptyCell = false;

        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);

            if (i == 1 || i == 3) {
                continue;
            }

            if (cell.getCellType() != CellType.BLANK) {
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

    public OffsetDateTime convertStringToOffsetDateTime(String dateString) {
        try {
            LocalDate localDate = LocalDate.parse(dateString, formatter);
            return OffsetDateTime.of(localDate.atStartOfDay(), OffsetDateTime.now().getOffset());
        } catch (Exception e) {
            return null;
        }
    }
}
