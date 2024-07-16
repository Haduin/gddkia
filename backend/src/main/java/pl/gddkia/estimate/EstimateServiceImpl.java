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
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Transactional
    @Override
    public MainResponse addNewEstimate(@NotNull final AddNewEstimateRest rest, final InputStream inputStream) {
        LOGGER.info("Start saving data from file");

        Set<Branch> branchList = findSectionFromArrayString(rest.sectionName());
        Estimate estimate = new Estimate(null, rest.contractName(), parseDate(rest.dateFrom()), parseDate(rest.dateTo()), rest.roadLength(), branchList, null);
        branchList.forEach(branch -> branch.addEstimate(estimate));

        LOGGER.info("End of data");
        return switch (workBookService.addNewEstimateWorkbook(inputStream, estimate, branchList)) {
            case WorkBookService.WorkbookCreationStatus.Successfully _ -> new MainResponse.EstimateSuccessful("OK");
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(dateString, formatter);
    }


    private Set<Branch> findSectionFromArrayString(final String[] sectionArray) {
        return Arrays.stream(sectionArray)
                .map(section -> branchRepository.findBySection(section)
                        .orElseThrow(() -> new RegionNotFoundException(section)))
                .collect(Collectors.toSet());
    }
}
