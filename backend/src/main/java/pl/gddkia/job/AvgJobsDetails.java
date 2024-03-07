package pl.gddkia.job;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvgJobsDetails{
    private String description;
    private String sst;
    private String unit;
    private Double costEstimate;
}

