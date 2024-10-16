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
import pl.gddkia.common.winter.WinterWorkbookService;
import pl.gddkia.estimate.Estimate;
import pl.gddkia.estimate.EstimateRepository;
import pl.gddkia.estimate.winter.WinterJob;
import pl.gddkia.job.GROUP_NAME;
import pl.gddkia.job.JobRepository;
import pl.gddkia.job.Jobs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WorkBookServiceImpl implements WorkBookService {
    private final JobRepository jobRepository;
    private final EstimateRepository estimateRepository;
    private final WinterWorkbookService winterWorkbookService;
    private final Logger LOGGER = LogManager.getLogger(WorkBookServiceImpl.class);
    private final Integer FIRST_INDEX_TO_READ = 8;
    private final Integer ROW_INDEX_OF_MERGED_SUBGROUP = 0;


    @Override
    public WorkbookCreationStatus addNewEstimateWorkbook(final InputStream inputStream, final Estimate estimate, Set<Branch> branchList) {
        LOGGER.info("Start saving data from file"); //todo extend information like, file name, user etc

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {

            final Set<Jobs> jobsList = IntStream.concat(
                            IntStream.range(0, Math.min(workbook.getNumberOfSheets(), 9)),
                            IntStream.range(12, Math.min(workbook.getNumberOfSheets(), 13))
                    )
                    .mapToObj(sheetIndex -> processSheet(workbook.getSheetAt(sheetIndex), sheetIndex))
                    .flatMap(Function.identity())
                    //some kind of validation, might be changed to some FI in the future
                    .filter(Jobs::validate)
                    .collect(Collectors.toSet());



            jobRepository.saveAll(jobsList);
            estimate.setJobs(jobsList);
            estimate.setBranches(branchList);
            estimateRepository.save(estimate);

            LOGGER.info("end saving data from file");
            workbook.close();
            return new WorkbookCreationStatus.Successfully();

        } catch (IOException e) {
            return new WorkbookCreationStatus.Failed("File not found");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new WorkbookCreationStatus.Failed("Invalid file format. Please check with specification");
        }
    }

    private Stream<Jobs> processSheet(Sheet sheet, int sheetIndex) {
        SheetState state = new SheetState();
        Stream<Jobs> jobsStream = IntStream.range(FIRST_INDEX_TO_READ, sheet.getLastRowNum())
                .mapToObj(sheet::getRow)
                .filter(row -> Objects.nonNull(row) && !isRowEmpty(row))
                .flatMap(row -> processRow(row, sheetIndex, state));
        System.out.println(state.count);
        return jobsStream;
    }


    private Stream<Jobs> processRow(Row row, int sheetIndex, SheetState state) {
        String checkIfSubtype = checkIfRowIsSubGroupOnlyOrEmpty(row);
        if (StringUtils.isNotEmpty(checkIfSubtype)) {
            state.localSubType = checkIfSubtype;
            return Stream.empty();
        }
        // we check if row contains specific text - RAZEM . Meaning we can stop reading more rows
        if (state.skipRows && Objects.nonNull(row.getCell(0)) && row.getCell(0).toString().contains("RAZEM")) {
            state.skipRows = false;
            return Stream.empty();
        }

        if (state.skipRows) {
            Cell cell2 = row.getCell(2);
            if (cell2 != null && StringUtils.isNotEmpty(cell2.toString())) {
                state.sst = cell2.toString();
            }

            return Stream.of(
                    new Jobs(
                            null,
                            state.sst,
                            Optional.ofNullable(row.getCell(3)).map(Cell::toString).orElse(""),
                            Optional.ofNullable(row.getCell(4)).map(Cell::toString).orElse(""),
                            validateUnitPrice(row.getCell(5)),
                            validateUnitPrice(row.getCell(6)),
                            GROUP_NAME.findByValue(sheetIndex + 1).name(),
                            state.localSubType,
                            null
                    )
            );

        }
        state.count++;
        return Stream.empty();
    }


    private Double validateUnitPrice(Cell cell) {
        return Objects.nonNull(cell) &&
                !cell.getCellType().name().isBlank() &&
                !cell.toString().isEmpty() ?
                parseStringCellValue(cell.toString()) : null;
    }

    @Nullable
    private Double parseStringCellValue(@NotNull String stringValue) {
        String numericValue = stringValue.substring(0, stringValue.length() - 3).trim().replace(" ", "");
        try {
            if (stringValue.endsWith(" zł")) {
                if (numericValue.contains(".") || numericValue.contains(",")) {
                    numericValue = numericValue.replace(',', '.');
                }
                return Double.parseDouble(numericValue);
            } else {
                return Double.parseDouble(stringValue);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Nullable
    private String checkIfRowIsSubGroupOnlyOrEmpty(@NotNull Row row) {
        short sizeOfEmptyCells = 0;

        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);

            if (Objects.nonNull(cell) && cell.getCellType() == CellType.BLANK) {
                sizeOfEmptyCells++;
            }
        }

        return row.getLastCellNum() == sizeOfEmptyCells + 1 ? row.getCell(ROW_INDEX_OF_MERGED_SUBGROUP).toString().trim() : "";
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        } else {
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                if (cell.getCellType() != CellType.BLANK) {
                    String cellValue = new DataFormatter().formatCellValue(cell).trim();
                    return cellValue.isEmpty();
                }
            }
        }
        return true;
    }

    static class SheetState {
        boolean skipRows = true;
        String localSubType = "";
        String sst = "";
        int count = 0;
    }
}
