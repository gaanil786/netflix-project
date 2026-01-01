package com.netflix.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	 private Workbook workbook;

	    public ExcelUtils(String excelPath) throws IOException {
	        FileInputStream fis = new FileInputStream(excelPath);
	        workbook = new XSSFWorkbook(fis);
	    }

	    public String getCellData(String sheetName, int rowNum, int colNum) {
	        Sheet sheet = workbook.getSheet(sheetName);
	        Row row = sheet.getRow(rowNum);
	        Cell cell = row.getCell(colNum);
	        return cell == null ? "" : cell.toString();
	    }

	    public int getRowCount(String sheetName) {
	        return workbook.getSheet(sheetName).getLastRowNum() + 1;
	    }
}
