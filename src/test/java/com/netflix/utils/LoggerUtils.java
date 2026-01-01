package com.netflix.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtils {
	 private static final String LOG_FILE = "logs/framework.log"; // adjust path


	  public static Logger getLogger(Class<?> clazz) {
	        return LogManager.getLogger(clazz);
	    }
	  
	  public static String getLastLogSnippet(int lines) {
	        List<String> allLines = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                allLines.add(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        int start = Math.max(allLines.size() - lines, 0);
	        return String.join("\n", allLines.subList(start, allLines.size()));
	    }

}
