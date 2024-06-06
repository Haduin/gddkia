package pl.gddkia.branch;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface BranchService {
    List<BranchRest> getAll();

    Optional<BranchRest> getById(Long id);

    SaveBranchRoadsResult createRoadsDetails(final InputStream file);

    UpdateBranchResult updateBranchDetails(Long branchId, final UpdateBranchDto updateRequest);
}
