package pl.gddkia.branch;

public sealed interface UpdateBranchResult {

    record SuccessfullyUpdate(String response) implements UpdateBranchResult {
    }

    record FailToUpdate(String response) implements UpdateBranchResult {
    }
}
