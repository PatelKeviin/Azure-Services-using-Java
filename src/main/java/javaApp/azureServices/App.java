package javaApp.azureServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.microsoft.azure.storage.StorageException;

public class App 
{
    public static void main( String[] args )
    {
    	//Storage Account variables
        String accountName = "<storage account name>";
        String accountKey = "<storage account key>";
        
        //Container and Blob specific variables
        String containerReference = "<container name>";
        String blobReference = "<blob path in container>";
        String pathToDownload = "temp.txt";
        
        //MySQL Database variables
        String host = "<server name>.mysql.database.azure.com";
        String database = "<database name>";
        String user = "<username>@<server name>";
        String password = "<password>";
        
        //HTML doc path
        String htmlDocPath = "<file name>.html";
        
        
        
        
        // Download blob from Azure Blob Storage and store locally
        DownloadBlobApp blobApp = new DownloadBlobApp(accountName, accountKey);
        
        try {
			blobApp.downloadBlob(containerReference, blobReference, pathToDownload);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (StorageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // Save the downloaded file to Azure MySQL database for persistence
        MySQLApp mysql = new MySQLApp(host, database, user, password);
        Connection connection = null;
        try {
			connection = mysql.initConnection();
			mysql.createTable(connection);
			
			//Opening the downloaded file
			File file = new File(pathToDownload);
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line;
			while((line = br.readLine()) != null) {
				//parsing the line 		**Please Note: the delimiter depends on the file**
				String[] values = line.split("\\t");
				mysql.insertRow(connection, values[0], values[1]);
			}
			br.close();
			
			// Read the data from Azure MySQL database and create a html file containing the data in a table 
			ResultSet results = mysql.readTable(connection);
			
			HTMLDocApp htmlDoc = new HTMLDocApp(htmlDocPath);
			htmlDoc.createHTMLFile(results);			
			
			System.out.println("Program finished sucessfully!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
}
