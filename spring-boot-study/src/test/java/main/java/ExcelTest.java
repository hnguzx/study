package main.java;

import org.apache.poi.ss.usermodel.*;

import java.io.*;

public class ExcelTest {

    void test1(){
        String inputFilePath = "path/to/input/file.xlsx";
        String outputFilePath = "path/to/output/file.xlsx";

        try (InputStream inp = new FileInputStream(inputFilePath);
             Workbook wb = WorkbookFactory.create(inp);
             OutputStream fileOut = new FileOutputStream(outputFilePath)) {

            Sheet sheet = wb.getSheetAt(0);

            // Process the data
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // Do something with the cell data
                }
            }

            // Write the output to a file
            wb.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
