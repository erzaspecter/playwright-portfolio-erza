package com.modena.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Utility class untuk membaca data dari JSON file
 * Menggunakan Jackson library untuk parsing
 */
public class JsonDataProvider {
    
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        // Konfigurasi ObjectMapper
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    /**
     * Membaca JSON file dan mengembalikan sebagai Map
     */
    public static Map<String, Object> readJsonAsMap(String filePath) {
        try {
            return objectMapper.readValue(new File(filePath), 
                    new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
    
    /**
     * Membaca JSON file dan mengembalikan sebagai List of Maps
     */
    public static List<Map<String, Object>> readJsonAsList(String filePath) {
        try {
            return objectMapper.readValue(new File(filePath),
                    new TypeReference<List<Map<String, Object>>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
    
    /**
     * Membaca JSON file dan mengembalikan sebagai Object (POJO)
     */
    public static <T> T readJsonAsObject(String filePath, Class<T> valueType) {
        try {
            return objectMapper.readValue(new File(filePath), valueType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
    
    /**
     * Mendapatkan test data untuk TestNG DataProvider
     * Format JSON: array of objects
     */
    public static Object[][] getTestData(String filePath) {
        List<Map<String, Object>> dataList = readJsonAsList(filePath);
        Object[][] testData = new Object[dataList.size()][1];
        
        for (int i = 0; i < dataList.size(); i++) {
            testData[i][0] = dataList.get(i);
        }
        
        return testData;
    }
    
    /**
     * Mendapatkan test data dengan key tertentu
     */
    public static Object[][] getTestDataByKey(String filePath, String key) {
        Map<String, Object> data = readJsonAsMap(filePath);
        List<?> values = (List<?>) data.get(key);
        
        Object[][] testData = new Object[values.size()][1];
        for (int i = 0; i < values.size(); i++) {
            testData[i][0] = values.get(i);
        }
        
        return testData;
    }
    
    /**
     * Menulis Map ke JSON file
     */
    public static void writeToJson(String filePath, Object data) {
        try {
            objectMapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON file: " + filePath, e);
        }
    }
}