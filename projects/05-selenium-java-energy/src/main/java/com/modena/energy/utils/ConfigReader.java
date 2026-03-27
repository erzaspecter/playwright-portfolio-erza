package com.modena.energy.utils;

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
            instance = new ConfigReader();
        }
        return instance;
    }
    
    private void loadProperties() {
        try {
            FileInputStream fis = new FileInputStream("config/config.properties");
            properties.load(fis);
            fis.close();
            System.out.println("Config loaded from config/config.properties");
        } catch (IOException e) {
            System.out.println("Config file not found, using defaults");
            setDefaultProperties();
        }
    }
    
    private void setDefaultProperties() {
        properties.setProperty("base.url", "https://energy.modena.com/id_en");
        properties.setProperty("default.browser", "chrome");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "15");
        properties.setProperty("screenshot.path", "reports/screenshots/");
    }
    
    // Method getProperty dengan 1 parameter
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    // Method getProperty dengan default value (overload) - TAMBAHKAN INI
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public String getBaseUrl() {
        return getProperty("base.url", "https://energy.modena.com/id_en");
    }
    
    public String getDefaultBrowser() {
        return getProperty("default.browser", "chrome");
    }
    
    public int getImplicitWait() {
        try {
            return Integer.parseInt(getProperty("implicit.wait", "10"));
        } catch (NumberFormatException e) {
            return 10;
        }
    }
    
    public int getExplicitWait() {
        try {
            return Integer.parseInt(getProperty("explicit.wait", "15"));
        } catch (NumberFormatException e) {
            return 15;
        }
    }
    
    public String getScreenshotPath() {
        return getProperty("screenshot.path", "reports/screenshots/");
    }
}
