package com.practice.metodoOficiales;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.practice.especialidades.CvsDetails;

public class CsvFiles {

	//Creating a CvsDetails object
	CvsDetails inCvsDetails = new CvsDetails();
	
	//Upload a Csv document
	public String uploadAnCsvFile(MultipartFile document) throws IOException{
		String salida = "";
		
		try{
	    	  Tika tika = new Tika();
	    	  String mimeType = tika.detect(document.getOriginalFilename());
	    	  
	            if ("text/csv".equals(mimeType)) {
	            	File file = new File("c:/upload_java/"+document.getOriginalFilename());
	            	FileOutputStream convert = new FileOutputStream(file);
	            	file.createNewFile();
	            	convert.write(document.getBytes());
	            	convert.close();
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
	public String savingAndReaddingACsvFile(MultipartFile document) {
		String result = "";
		File file = new File ("c:/upload_java/"+document.getOriginalFilename());
		
		try {
			result = inCvsDetails.saveUsingTika(document);
			
		}catch (IOException e) {
            e.printStackTrace(); 
      }
		return result;
	}
}
