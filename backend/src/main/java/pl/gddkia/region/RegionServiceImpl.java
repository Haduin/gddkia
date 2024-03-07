package pl.gddkia.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public List<RegionRest> getAll() {
        return regionRepository.findAll()
                .stream()
                .map(region -> new RegionRest(region.getId(), region.getRegionName()))
                .toList();
    }
}
