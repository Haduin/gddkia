package pl.gddkia.region;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/region")
public class RegionController {
    private final RegionService regionService;
    @GetMapping
    public List<RegionRest> gelAll() {
        return regionService.getAll();
    }
}
