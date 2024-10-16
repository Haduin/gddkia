package pl.gddkia.estimate;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.gddkia.branch.Branch;
import pl.gddkia.branch.BranchRepository;
import pl.gddkia.common.WorkBookService;
import pl.gddkia.exceptions.MainResponse;
import pl.gddkia.exceptions.RegionNotFoundException;
import pl.gddkia.job.JobMapper;
import pl.gddkia.job.JobRest;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {
    private final EstimateRepository estimateRepository;
    private final BranchRepository branchRepository;
    private final WorkBookService workBookService;


    private final Logger LOGGER = LogManager.getLogger(EstimateServiceImpl.class);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Transactional
    @Override
    public MainResponse addNewEstimate(@NotNull final AddNewEstimateRest rest, final InputStream inputStream) {
        LOGGER.info("Start saving data from file");

        Set<Branch> branchList = findSectionFromArrayString(rest.sectionName());
        Estimate estimate = new Estimate(null, rest.contractName(), parseDate(rest.dateFrom()), parseDate(rest.dateTo()), rest.roadLength(), branchList, null, null);
        branchList.forEach(branch -> branch.addEstimate(estimate));

        return switch (workBookService.addNewEstimateWorkbook(inputStream, estimate, branchList)) {
            case WorkBookService.WorkbookCreationStatus.Successfully successfully -> new MainResponse.EstimateSuccessful("OK");
            case WorkBookService.WorkbookCreationStatus.Failed failed ->
                    new MainResponse.EstimateError(failed.reason());
        };
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
                                estimate.getRoadLength(),
                                null, //todo for now
                                estimate.getJobs()
                                        .stream()
                                        .map(JobMapper::mapToRest)
                                        .collect(Collectors.groupingBy(JobRest::groupType))

                        )
                ).toList();
    }

    @Override
    public EstimateRest getEstimate(String estimateName) {
        return null;
    }

    public LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }


    private Set<Branch> findSectionFromArrayString(final String[] sectionArray) {
        return Arrays.stream(sectionArray)
                .map(section -> branchRepository.findBySection(section)
                        .orElseThrow(() -> new RegionNotFoundException(section)))
                .collect(Collectors.toSet());
    }
}
