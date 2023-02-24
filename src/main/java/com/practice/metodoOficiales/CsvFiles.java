package com.practice.metodoOficiales;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

public class CsvFiles {

	//Upload a Csv document
	public String uploadAnCsvFile(MultipartFile document) throws IOException{
		String salida = "";
		
		try{
	    	  Tika tika = new Tika();
	    	  String mimeType = tika.detect(document.getOriginalFilename());
	    	  
	            if ("text/csv".equals(mimeType)) {
	            	File file = new File("c:/upload_java/"+document.getOriginalFilename());
	            	FileOutputStream convertir = new FileOutputStream(file);
	            	file.createNewFile();
	            	convertir.write(document.getBytes());
	            	salida = "Uploaded.-";
	            } else {
	                salida = "This is not a CSV File.-";
	            }
	      }catch (IOException e) {
	            e.printStackTrace(); 
	      }
		return salida;
	}
	
	//Upload (again - because I need to practice)and read the first column in a Csv file.-
	public String uploadAndReadtheFirstColumn (MultipartFile document) {
		String result = "";
		
		return result;
	}
	
}
