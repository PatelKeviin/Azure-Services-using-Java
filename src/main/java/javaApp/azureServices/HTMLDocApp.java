package javaApp.azureServices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HTMLDocApp {
	String path = null;
	BufferedWriter bufferedWriter = null;
	
	public HTMLDocApp(String _path) throws IOException {
		path = _path;
	}
	
	public void createHTMLFile(ResultSet results) throws IOException, SQLException {
		//Write html doc
		bufferedWriter = new BufferedWriter(new FileWriter(new File(path)));
		
		bufferedWriter.write("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"	<head>\r\n" + 
				"		<meta charset=\"utf-8\">\r\n" + 
				"		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
				"		\r\n" + 
				"		<link rel=\"stylesheet\" href=\"main.css\" type=\"text/css\" media=\"screen, projection\"/>\r\n" + 
				"		\r\n" + 
				"		<title>Word Count</title>\r\n" + 
				"	</head>\r\n" + 
				"	<body>\r\n" + 
				"		<h2>Word Count (Map-Reduce Program Output)</h2>\r\n" + 
				"		<table>\r\n" + 
				"			<tr>\r\n" + 
				"				<th>Word</th>\r\n" + 
				"				<th>Count</th>\r\n" + 
				"			</tr>\r\n");
		
		while (results.next()) {
			bufferedWriter.write("			<tr>\r\n" + 
					"				<td>" + results.getString(2) + "</td>\r\n" + 
					"				<td>" + results.getString(3) + "</td>\r\n" + 
					"			</tr>\r\n");
		}
		
		bufferedWriter.write("		</table>\r\n" + 
				"	</body>\r\n" + 
				"</html>");
		
		bufferedWriter.close();
		
		//Write css doc
		bufferedWriter = new BufferedWriter(new FileWriter(new File("main.css")));
		
		bufferedWriter.write("table {\r\n" + 
				"	border-collapse: collapse;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"table th {\r\n" + 
				"	text-align: left;\r\n" + 
				"	background-color: #3a6070;\r\n" + 
				"	color: #FFF;\r\n" + 
				"	padding: 4px 30px 4px 8px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"table td {\r\n" + 
				"	border: 1px solid #e3e3e3;\r\n" + 
				"	padding: 4px 8px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"table tr:nth-child(odd) td {\r\n" + 
				"	background-color: #e7edf0;\r\n" + 
				"}");
		
		bufferedWriter.close();
	}
}
