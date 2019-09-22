package javaApp.azureServices;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;


public class DownloadBlobApp 
{
	private String storageConnectionString = null;
	
	public DownloadBlobApp(String accountName, String accountKey) {
		storageConnectionString =
				"DefaultEndpointsProtocol=https;" +
				"AccountName=" + accountName +
				";AccountKey=" + accountKey + ";";
	}
	
	public void downloadBlob(String containerReference, String blobReference, String path) throws InvalidKeyException, URISyntaxException, StorageException, IOException {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container=null;
		
		storageAccount = CloudStorageAccount.parse(storageConnectionString);
		blobClient = storageAccount.createCloudBlobClient();
		container = blobClient.getContainerReference(containerReference);
		
		CloudBlockBlob blob = container.getBlockBlobReference(blobReference);
		blob.downloadToFile(path);
	}
}
