package pl.gddkia.branch;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Override
    public List<BranchRest> getAll() {
        return branchRepository.findAll()
                .stream()
                .map(BranchMapper::mapToRest)
                .toList();
    }

    @Override
    public Optional<BranchRest> getById(Long id) {
        return branchRepository.findById(id)
                //todo fix null when Optional
                .map(BranchMapper::mapToRest);
    }

    @Override
    public UpdateBranchResult updateBranchDetails(Long branchId, UpdateBranchDto updateRequest) {

        Optional<Branch> optionalBranch = branchRepository.findByBranchAndRegionAndSection(updateRequest.branch(), updateRequest.region(), updateRequest.section())
                .map(old -> branchRepository.save(BranchMapper.mapToEntity(old, updateRequest)));

        return optionalBranch.isPresent() ?
                new UpdateBranchResult.SuccessfullyUpdate(String.format("Branch [%s] was updated", branchId)) :
                new UpdateBranchResult.FailToUpdate(String.format("Branch [%s] was not updated", branchId));

    }

    /**
     * Saves road details from the provided input stream.
     *
     * @param file The input stream containing road data in an unspecified format.
     * @return A {@link SaveBranchRoadsResult} object indicating success or failure.
     * - On success, the object contains a list of saved {@link RoadData} objects.
     * - On failure, the object contains the error message.
     * @throws IOException If an I/O error occurs while reading from the input stream.
     */
    @Override
    public SaveBranchRoadsResult createRoadsDetails(InputStream file) {
        return switch (readDataFromFile(file)) {
            case RoadDataProcess.Successful successful -> {
                List<RoadData> roadDataList = successful.response();
                yield new SaveBranchRoadsResult.Successful(roadDataList.stream()
                        .map(roadData -> {
                            var branchOpt = branchRepository.findByBranchAndRegionAndSection(roadData.branch(), roadData.region(), roadData.section());
                            return branchOpt
                                    .map(branch -> branchRepository.save(BranchMapper.mapToEntity(roadData, branch.getId())))
                                    .orElseGet(() -> branchRepository.save(BranchMapper.mapToEntity(roadData)));
                        })
                        .map(BranchMapper::mapToRest)
                        .toList()
                );
            }
            case RoadDataProcess.ErrorOccurred errorOccurred ->
                    new SaveBranchRoadsResult.ErrorOccurred(errorOccurred.response());
        };
    }

    @NotNull
    private RoadDataProcess readDataFromFile(final InputStream file) {
        List<RoadData> roadDataList = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                // skip header
                if (row.getRowNum() == 0) {
                    continue;
                }
                RoadData roadData = new RoadData(
                        getStringCellValue(row, 1, errorMessages),
                        getStringCellValue(row, 2, errorMessages),
                        getStringCellValue(row, 3, errorMessages),
                        getStringCellValue(row, 4, errorMessages),
                        getStringCellValue(row, 5, errorMessages),
                        getStringCellValue(row, 6, errorMessages),
                        getStringCellValue(row, 7, errorMessages),
                        getStringCellValue(row, 8, errorMessages),
                        getNumericCellValue(row, 9, errorMessages),
                        getStringCellValue(row, 10, errorMessages),
                        getNumericCellValue(row, 11, errorMessages),
                        getNumericCellValue(row, 12, errorMessages),
                        getStringCellValue(row, 13, errorMessages)
                );
                roadDataList.add(roadData);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return errorMessages.isEmpty() ?
                new RoadDataProcess.Successful(roadDataList) :
                new RoadDataProcess.ErrorOccurred(errorMessages);

    }

    @Nullable
    private String getStringCellValue(Row row, int cellIndex, List<String> errorMessages) {
        try {
            Cell cell = row.getCell(cellIndex);
            return cell.getStringCellValue();
        } catch (Exception e) {
            errorMessages.add("Error in row " + row.getRowNum() + ", column " + cellIndex + ": " + e.getMessage());
            return null;
        }
    }

    @Nullable
    private Double getNumericCellValue(Row row, int cellIndex, List<String> errorMessages) {
        try {
            Cell cell = row.getCell(cellIndex);
            return cell.getNumericCellValue();
        } catch (Exception e) {
            errorMessages.add("Error in row " + row.getRowNum() + ", column " + cellIndex + ": " + e.getMessage());
            return null;
        }
    }

}


