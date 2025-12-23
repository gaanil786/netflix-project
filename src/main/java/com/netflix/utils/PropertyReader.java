package com.netflix.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {
	 private static Properties prop = new Properties();

	    static {
	        try {
	            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
	            prop.load(fis);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static String get(String key) {
	        return prop.getProperty(key);
	    }
}
