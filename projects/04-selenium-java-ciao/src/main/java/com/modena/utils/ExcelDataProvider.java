package com.modena.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class untuk membaca data dari Excel file
 * Mendukung .xlsx dan .xls format
 */
public class ExcelDataProvider {
    
    private Workbook workbook;
    private Sheet sheet;
    
    /**
     * Constructor dengan file path dan sheet name
     */
    public ExcelDataProvider(String filePath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            String fileExtension = filePath.substring(filePath.lastIndexOf("."));
            
            if (fileExtension.equals(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileExtension.equals(".xls")) {
                workbook = new HSSFWorkbook(fis);
            }
            
            sheet = workbook.getSheet(sheetName);
            fis.close();
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + filePath, e);
        }
    }
    
    /**
     * Mendapatkan semua data sebagai Object[][]
     * Cocok untuk TestNG DataProvider
     */
    public Object[][] getAllData() {
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        
        Object[][] data = new Object[rowCount - 1][colCount];
        
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = getCellValue(cell);
            }
        }
        
        return data;
    }
    
    /**
     * Mendapatkan data berdasarkan header
     */
    public List<Map<String, Object>> getDataAsMap() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        
        Row headerRow = sheet.getRow(0);
        int colCount = headerRow.getPhysicalNumberOfCells();
        
        // Get header names
        String[] headers = new String[colCount];
        for (int i = 0; i < colCount; i++) {
            headers[i] = getCellValue(headerRow.getCell(i)).toString();
        }
        
        // Get data rows
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            Map<String, Object> rowData = new HashMap<>();
            
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                rowData.put(headers[j], getCellValue(cell));
            }
            dataList.add(rowData);
        }
        
        return dataList;
    }
    
    /**
     * Mendapatkan data dari column tertentu
     */
    public List<Object> getColumnData(int columnIndex) {
        List<Object> columnData = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();
        
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(columnIndex);
            columnData.add(getCellValue(cell));
        }
        
        return columnData;
    }
    
    /**
     * Mendapatkan data spesifik berdasarkan row dan column
     */
    public Object getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum);
        return getCellValue(cell);
    }
    
    /**
     * Mendapatkan nilai cell dengan tipe yang tepat
     */
    private Object getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    // Handle numeric values
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return (long) numericValue;
                    }
                    return numericValue;
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    /**
     * Mendapatkan jumlah row
     */
    public int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }
    
    /**
     * Mendapatkan jumlah column
     */
    public int getColumnCount() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }
    
    /**
     * Close workbook
     */
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Static method untuk mendapatkan data langsung (convenience method)
     */
    public static Object[][] getData(String filePath, String sheetName) {
        ExcelDataProvider provider = new ExcelDataProvider(filePath, sheetName);
        Object[][] data = provider.getAllData();
        provider.close();
        return data;
    }
}