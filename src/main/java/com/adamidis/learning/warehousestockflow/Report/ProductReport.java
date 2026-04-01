package com.adamidis.learning.warehousestockflow.Report;

import com.adamidis.learning.warehousestockflow.Exception.ApiException;
import com.adamidis.learning.warehousestockflow.Model.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class ProductReport {
    public static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Product> products;
    private static String[] HEADERS = { "SKU", "Category", "Product", "Quantity", "Price/Unit", "Imported At", "Expiration Date" };

    public ProductReport(List<Product> products) {
        this.products = products;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Products");
        setHeaders();
    }

    private void setHeaders() {
        Row headerRow = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        IntStream.range(0, HEADERS.length).forEach(index -> {
            Cell cell = headerRow.createCell(index);
            cell.setCellValue(HEADERS[index]);
            cell.setCellStyle(style);
        });
    }

    public InputStreamResource export() {
        return generateReport();
    }

    private InputStreamResource generateReport() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(10);
            style.setFont(font);
            int rowIndex = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(product.getSku());
                row.createCell(1).setCellValue(product.getCategory().getName());
                row.createCell(2).setCellValue(product.getName());
                row.createCell(3).setCellValue(product.getQuantityStock());
                row.createCell(4).setCellValue(product.getUnitPrice().doubleValue());
                row.createCell(5).setCellValue(DateFormatUtils.format(product.getImportedAt(), DATE_FORMATTER));
                row.createCell(6).setCellValue(DateFormatUtils.format(product.getExpirationDate(), DATE_FORMATTER));
            }
            workbook.write(out);
            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("Unable to export report file");
        }
    }
}
