package com.modena.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class untuk membaca konfigurasi dari file properties
 * Mendukung multiple environment (dev, staging, production)
 */
public class ConfigReader {
    
    private Properties properties;
    private static ConfigReader instance;
    private String environment;
    
    /**
     * Private constructor untuk Singleton pattern
     */
    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }
    
    /**
     * Mendapatkan instance ConfigReader (Singleton)
     */
    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }
    
    /**
     * Memuat semua file properties
     */
    private void loadProperties() {
        // Baca environment dari system property atau default
        environment = System.getProperty("env", "config");
        
        try {
            // Load main config file
            String configFile = String.format("config/%s.properties", environment);
            InputStream input = getClass().getClassLoader().getResourceAsStream(configFile);
            
            if (input == null) {
                // Fallback ke file di folder config
                input = new FileInputStream(configFile);
            }
            
            properties.load(input);
            input.close();
            
            System.out.println("Loaded configuration from: " + configFile);
            
        } catch (IOException e) {
            System.err.println("Failed to load configuration file: " + e.getMessage());
            // Load default properties jika file tidak ditemukan
            loadDefaultProperties();
        }
    }
    
    /**
     * Load default properties jika file tidak ditemukan
     */
    private void loadDefaultProperties() {
        properties.setProperty("base.url", "https://ciao.modena.com/id");
        properties.setProperty("default.browser", "chrome");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "15");
        properties.setProperty("screenshot.path", "reports/screenshots/");
        properties.setProperty("report.path", "reports/extent-reports/");
    }
    
    /**
     * Mendapatkan value berdasarkan key
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            System.err.println("Property not found: " + key);
            return "";
        }
        return value;
    }
    
    /**
     * Mendapatkan value dengan default jika key tidak ditemukan
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Mendapatkan integer value
     */
    public int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse int for key: " + key);
            return 0;
        }
    }
    
    /**
     * Mendapatkan boolean value
     */
    public boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }
    
    /**
     * Mendapatkan base URL
     */
    public String getBaseUrl() {
        return getProperty("base.url");
    }
    
    /**
     * Mendapatkan browser default
     */
    public String getDefaultBrowser() {
        return getProperty("default.browser", "chrome");
    }
    
    /**
     * Mendapatkan implicit wait timeout
     */
    public int getImplicitWait() {
        return getIntProperty("implicit.wait");
    }
    
    /**
     * Mendapatkan explicit wait timeout
     */
    public int getExplicitWait() {
        return getIntProperty("explicit.wait");
    }
    
    /**
     * Mendapatkan path untuk screenshot
     */
    public String getScreenshotPath() {
        return getProperty("screenshot.path", "reports/screenshots/");
    }
    
    /**
     * Reload configuration (useful setelah environment change)
     */
    public void reload() {
        loadProperties();
    }
}