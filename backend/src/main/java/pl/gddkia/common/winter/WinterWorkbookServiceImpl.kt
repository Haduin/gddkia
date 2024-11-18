package pl.gddkia.common.winter

import lombok.RequiredArgsConstructor
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.Sheet
import org.springframework.stereotype.Service
import pl.gddkia.estimate.winter.WinterJobA
import java.util.*

@Service
@RequiredArgsConstructor
class WinterWorkbookServiceImpl(
    private val winterJobRepository: WinterJobRepository
) : WinterWorkbookService {

    private val LOGGER: Logger = LogManager.getLogger(
        WinterWorkbookServiceImpl::class.java
    )

    override fun parseWinterASheet(sheet: Sheet) {
        var skipRow = false
        for (rowIndex in 10..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)
            if (Objects.nonNull(row.getCell(0)) && row.getCell(0).toString().contains("RAZEM")) {
                skipRow = true
            }
            if (!skipRow) {
                val nrCell = row.getCell(1)
                val nrDrogiCell = row.getCell(2)
                val desciption = row.getCell(3)
                val length = row.getCell(4)
                val zud = row.getCell(5)
                val fencePrice = row.getCell(6)
                val fenceKm = row.getCell(7)
                val snowPrice = row.getCell(9)
                val snowKm = row.getCell(10)
                val snowQuantity = row.getCell(11)

                val winterjob = WinterJobA(
                    null,
                    getCellValue(nrCell),
                    getCellValue(nrDrogiCell),
                    getCellValue(desciption),
                    getCellValue(length).toDouble(),
                    getCellValue(zud),
                    getCellValue(fencePrice),
                    getCellValue(fenceKm),
                    getCellValue(snowPrice),
                    getCellValue(snowKm),
                    getCellValue(snowQuantity),
                    null
                )

            }
        }
    }

    private fun getCellValue(cell: Cell?): String {
        return cell?.let {
            when (cell.cellType) {
                CellType.STRING -> cell.stringCellValue
                CellType.NUMERIC -> {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cell.dateCellValue.toString()
                    } else {
                        cell.numericCellValue.toString()
                    }
                }

                CellType.BOOLEAN -> cell.booleanCellValue.toString()
                CellType.FORMULA -> cell.cellFormula
                CellType.BLANK -> ""
                else -> "UNKNOWN"
            }
        } ?: ""

    }

    override fun parseWinterBSheet(sheet: Sheet) {
    }

    override fun parseWinterCSheet(sheet: Sheet) {

    }
}
