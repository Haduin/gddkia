package pl.gddkia.branch;

import java.util.List;

public sealed interface RoadDataProcess permits RoadDataProcess.ErrorOccurred, RoadDataProcess.Successful {
    record Successful(List<RoadData> response) implements RoadDataProcess {

    }

    record ErrorOccurred(List<String> response) implements RoadDataProcess {

    }
}
