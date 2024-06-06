package pl.gddkia.branch;

import kotlin.Pair;

import java.util.List;

public record RoadDataError(
        List<Pair<String,String>> details
) {
}
