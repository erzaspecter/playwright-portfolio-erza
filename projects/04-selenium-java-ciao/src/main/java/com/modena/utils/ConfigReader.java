package com.modena.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    
    private static ConfigReader instance;
    private Properties properties;
    
    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }
    
    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }
    
    private void loadProperties() {
        // Coba load dari beberapa lokasi
        String[] configPaths = {
            "config/config.properties",
            "src/main/resources/config.properties",
            "src/test/resources/config.properties"
        };
        
        boolean loaded = false;
        for (String path : configPaths) {
            try (FileInputStream fis = new FileInputStream(path)) {
                properties.load(fis);
                System.out.println("Config loaded from: " + path);
                loaded = true;
                break;
            } catch (IOException e) {
                // Continue to next path
            }
        }
        
        if (!loaded) {
            System.out.println("Config file not found, using defaults");
            setDefaultProperties();
        }
    }
    
    private void setDefaultProperties() {
        properties.setProperty("base.url", "https://the-internet.herokuapp.com/login");
        properties.setProperty("default.browser", "chrome");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "15");
        properties.setProperty("screenshot.path", "reports/screenshots/");
        properties.setProperty("report.path", "reports/extent-reports/");
    }
    
    // Method getProperty dengan 1 parameter
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    // Method getProperty dengan default value (overload)
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public String getBaseUrl() {
        return getProperty("base.url", "https://the-internet.herokuapp.com/login");
    }
    
    public String getDefaultBrowser() {
        return getProperty("default.browser", "chrome");
    }
    
    public int getImplicitWait() {
        String value = getProperty("implicit.wait", "10");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 10;
        }
    }
    
    public int getExplicitWait() {
        String value = getProperty("explicit.wait", "15");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 15;
        }
    }
    
    // ✅ Tambahkan method getScreenshotPath
    public String getScreenshotPath() {
        return getProperty("screenshot.path", "reports/screenshots/");
    }
    
    // ✅ Tambahkan method getReportPath
    public String getReportPath() {
        return getProperty("report.path", "reports/extent-reports/");
    }
}