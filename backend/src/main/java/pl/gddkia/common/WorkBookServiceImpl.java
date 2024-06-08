package pl.gddkia.common;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import pl.gddkia.branch.Branch;
import pl.gddkia.estimate.Estimate;
import pl.gddkia.estimate.EstimateRepository;
import pl.gddkia.job.GROUP_NAME;
import pl.gddkia.job.JobRepository;
import pl.gddkia.job.Jobs;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkBookServiceImpl implements WorkBookService {
    private final JobRepository jobRepository;
    private final EstimateRepository estimateRepository;
    private final Logger LOGGER = LogManager.getLogger(WorkBookServiceImpl.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @Override
    public WorkbookCreationStatus addNewEstimateWorkbook(final InputStream inputStream, final Estimate estimate, List<Branch> branchList ) {
        try {
            LOGGER.info("Start saving data from file"); //todo extend information like, file name, user etc
            String sst = "";
            List<Jobs> jobsList = new ArrayList<>();
            Workbook workbook = WorkbookFactory.create(inputStream);
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets() && sheetIndex <= 8; sheetIndex++) {
                Sheet sheetAt = workbook.getSheetAt(sheetIndex);
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

                        jobsList.add(
                                new Jobs(
                                        null,
                                        sst,
                                        row.getCell(3).toString(),
                                        row.getCell(4).toString(),
                                        validateUnitPrice(row.getCell(5)),
                                        validateUnitPrice(row.getCell(6)),
                                        GROUP_NAME.findByValue(sheetIndex + 1).name(),
                                        localSubType,
                                        null
                                )
                        );
                    }
                }
                jobRepository.saveAll(jobsList);
            }

            estimate.setJobs(jobsList);
            estimate.setBranches(branchList);
            estimateRepository.save(estimate);

            LOGGER.info("end saving data from file");
            workbook.close();
            return new WorkbookCreationStatus.Successfully();
        } catch (IOException e) {
            return new WorkbookCreationStatus.Failed("File not found");
        }
        catch (Exception e) {
            return new WorkbookCreationStatus.Failed("Something went wrong");
        }
    }

    private Double validateUnitPrice(Cell cell) {
        return cell != null &&
                !cell.getCellType().name().isBlank() &&
                !cell.toString().isEmpty() ?
                parseStringCellValue(cell.toString()) : null;
    }

    @Nullable
    private Double parseStringCellValue(@NotNull String stringValue) {
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

    @Nullable
    private String getContentIfNotAllCellsEmpty(@NotNull Row row) {
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
