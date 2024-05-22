package pl.gddkia.branch;

import java.util.List;

public sealed interface SaveBranchRoadsResult {
    record Successful(List<BranchRest> response) implements SaveBranchRoadsResult {
    }

    record ErrorOccurred(List<String> response) implements SaveBranchRoadsResult {
    }
}
