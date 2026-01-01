package com.netflix.reporting;

import java.util.List;
import java.util.Map;

public class ExtentReportsUtils {

	public static String convertToHtmlTable(List<Map<String, Object>> rows) {
		if (rows == null || rows.isEmpty()) {
			return "<p>No data returned from query</p>";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("<table border='1' style='border-collapse:collapse'>");

		// Header row
		sb.append("<tr>");
		for (String col : rows.get(0).keySet()) {
			sb.append("<th>").append(col).append("</th>");
		}
		sb.append("</tr>");

		// Data rows
		for (Map<String, Object> row : rows) {
			sb.append("<tr>");
			for (Object value : row.values()) {
				sb.append("<td>").append(value == null ? "" : value.toString()).append("</td>");
			}
			sb.append("</tr>");
		}

		sb.append("</table>");
		return sb.toString();
	}
}
