package pl.gddkia.job;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record JobRest(
        String SST,
        String description,
        String unit,
        Double costEstimate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Double quantity,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String subType,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String groupType
) {
}
