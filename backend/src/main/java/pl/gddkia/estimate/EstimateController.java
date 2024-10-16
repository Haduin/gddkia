package pl.gddkia.estimate;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.gddkia.exceptions.MainResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/estimate")
public class EstimateController {
    private final EstimateService estimateService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public MainResponse addNewEstimate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("companyName") String companyName,
            @RequestParam("contractName") String contractName,
            @RequestParam("dateFrom") String dateFrom,
            @RequestParam("dateTo") String dateTo,
            @RequestParam("roadLength") Long roadLength,
            @RequestParam("branchName") String branchName,
            @RequestParam("regionName") String[] regionName,
            @RequestParam("sectionName") String[] sectionName
    ) throws IOException {
        return estimateService.addNewEstimate(new AddNewEstimateRest(
                companyName,
                contractName,
                dateFrom,
                dateTo,
                branchName,
                regionName,
                sectionName,
                roadLength
        ), file.getInputStream());
    }


    @PostMapping(value = "/upload2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void addNewEstimate(@RequestParam("file") MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(9);
            boolean skipRow = false;
            for (int rowIndex = 10; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (Objects.nonNull(row.getCell(0)) && row.getCell(0).toString().contains("RAZEM")) {
                    skipRow = true;
                }
                if (!skipRow) {
                    Cell lpCell = row.getCell(0);      // kolumna a - Lp
                    Cell nrCell = row.getCell(1);      // kolumna b - Nr
                    Cell nrDrogiCell = row.getCell(2); // kolumna c - Nr drogi
                    Cell odcinekCell = row.getCell(3); // kolumna d - Odcinek charakterystyczny
                    Cell dlugoscCell = row.getCell(4); // kolumna e - Długość odcinka w km
                    Cell standardZUDCell = row.getCell(5); // kolumna f - Standard ZUD
                    Cell cenaJednNettoCell = row.getCell(7); // kolumna g - Cena jedn. netto (Ustawienie płotków)
                    Cell kmPllCell = row.getCell(8); // kolumna h - km (Ustawienie płotków)
                    Cell wartoscPllCell = row.getCell(9); // kolumna i - Wartość (Ustawienie płotków)
                    Cell cenaJednSnowCell = row.getCell(10); // kolumna j - Cena jedn. netto (Załadunek i wywóz śniegu)
                    Cell kmSnowCell = row.getCell(11); // kolumna k - km (Załadunek i wywóz śniegu)
                    Cell wartoscSnowCell = row.getCell(12); // kolumna l - Wartość (Załadunek i wywóz śniegu)
                    Cell wartoscSezonowaCell = row.getCell(13); // kolumna m - Wartość zadania za 1 sezon
                    Cell wartoscUmowaCell = row.getCell(14); // kolumna n - Wartość zadania za okres umowy

                    // Wyświetlanie odczytanych wartości
                    System.out.println("Lp: " + getCellValue(lpCell));
                    System.out.println("Nr: " + getCellValue(nrCell));
                    System.out.println("Nr drogi: " + getCellValue(nrDrogiCell));
                    System.out.println("Odcinek charakterystyczny: " + getCellValue(odcinekCell));
                    System.out.println("Długość odcinka w km: " + getCellValue(dlugoscCell));
                    System.out.println("Standard ZUD: " + getCellValue(standardZUDCell));
                    System.out.println("Cena jedn. netto (Płotki): " + getCellValue(cenaJednNettoCell));
                    System.out.println("km (Płotki): " + getCellValue(kmPllCell));
                    System.out.println("Wartość (Płotki): " + getCellValue(wartoscPllCell));
                    System.out.println("Cena jedn. netto (Śnieg): " + getCellValue(cenaJednSnowCell));
                    System.out.println("km (Śnieg): " + getCellValue(kmSnowCell));
                    System.out.println("Wartość (Śnieg): " + getCellValue(wartoscSnowCell));
                    System.out.println("Wartość zadania za 1 sezon: " + getCellValue(wartoscSezonowaCell));
                    System.out.println("Wartość zadania za okres umowy: " + getCellValue(wartoscUmowaCell));
                    System.out.println("---------------------------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getDateCellValue().toString();
                } else {
                    yield Double.toString(cell.getNumericCellValue());
                }
            }
            case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            case BLANK -> "";
            default -> "UNKNOWN";
        };
    }

    @GetMapping()
    public List<EstimateRest> getAllEstimate() {
        return estimateService.getEstimate();
    }
}
