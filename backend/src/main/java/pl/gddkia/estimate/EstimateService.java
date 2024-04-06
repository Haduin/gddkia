package pl.gddkia.estimate;

import pl.gddkia.exceptions.MainError;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface EstimateService {
    MainError addNewEstimate(final AddNewEstimateRest addNewEstimateRest, final InputStream inputStream) throws IOException;

    List<EstimateRest> getEstimate();

    EstimateRest getEstimate(String estimateName);
}
