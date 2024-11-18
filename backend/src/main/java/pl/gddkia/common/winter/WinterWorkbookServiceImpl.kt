package pl.gddkia.common.winter;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WinterWorkbookServiceImpl implements WinterWorkbookService {
    private final WinterJobRepository winterJobRepository;
    private final Logger LOGGER = LogManager.getLogger(WinterWorkbookServiceImpl.class);

    @Override
    public void parseWinterASheet(Sheet sheet) {

    }

    @Override
    public void parseWinterBSheet(Sheet sheet) {

    }
}
