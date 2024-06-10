package pl.gddkia.common;

import pl.gddkia.branch.Branch;
import pl.gddkia.estimate.Estimate;

import java.io.InputStream;
import java.util.Set;

public interface WorkBookService {

    WorkbookCreationStatus addNewEstimateWorkbook(final InputStream inputStream, final Estimate estimate, Set<Branch> branchList);

    sealed interface WorkbookCreationStatus permits WorkBookService.WorkbookCreationStatus.Failed, WorkbookCreationStatus.Successfully {
        record Successfully() implements WorkbookCreationStatus {

        }

        record Failed(String reason) implements WorkbookCreationStatus {

        }
    }
}


