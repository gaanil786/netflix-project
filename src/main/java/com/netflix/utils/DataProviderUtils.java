package com.netflix.utils;

import org.testng.annotations.DataProvider;

public class DataProviderUtils {
	 @DataProvider(name = "loginData")
	    public Object[][] getLoginData() throws Exception {
	        String excelPath = "src/main/resources/testdata.xlsx";
	        ExcelUtils excel = new ExcelUtils(excelPath);

	        int rowCount = excel.getRowCount("Login");
	        Object[][] data = new Object[rowCount-1][3]; // email + password

	        for (int i = 1; i < rowCount; i++) {
	            data[i-1][0] = excel.getCellData("Login", i, 0); // email
	            data[i-1][1] = excel.getCellData("Login", i, 1); // password
	            data[i-1][2] = excel.getCellData("Login", i, 2); // expectedResult
	        }
	        return data;
	    }
	}
