package pl.gddkia.common.winter;

import org.apache.poi.ss.usermodel.Sheet;

public interface WinterWorkbookService {

    void parseWinterASheet(final Sheet sheet);
    void parseWinterBSheet(final Sheet sheet);
}
