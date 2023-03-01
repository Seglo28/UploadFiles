package com.practice.especialidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import com.practice.mapeos.Students;

public class CvsDetails {

	//If this is a cvs document, save it!
	public boolean saveUsingTika(MultipartFile document) throws IOException{
		boolean isOk = false;
		Tika tika = new Tika();
		File file = new File ("c:/upload_java/"+document.getOriginalFilename());
		
		try {
		String mimeType = tika.detect(document.getOriginalFilename());
		isOk = ("text/csv".equals(mimeType));
			FileOutputStream convert = new FileOutputStream(file);
			file.createNewFile();
			convert.write(document.getBytes());
			convert.close();
		}catch (IOException e) {
            e.printStackTrace(); 
            isOk = false;
            }
		return isOk;
		}
	
	//Read a column from a cvs document
	public String readingCvsColumn (MultipartFile document) throws IOException {
		String result = "", line = "";
		File file = new File ("c:/upload_java/"+document.getOriginalFilename());
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader (new FileReader(file));
			while((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
			}
			result = sb.toString();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			reader.close();
		}
		return result;
	}
	
	//Create a Json without use dependencies.
	public List<List<Students>> WithoutDependencies(MultipartFile document) throws IOException {
		String result = "", line = "";
		File file = new File ("c:/upload_java/"+document.getOriginalFilename());
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		
		List<List<Students>> columns = new ArrayList<List<Students>>();
		
		try {
			if(this.saveUsingTika(document)) {
				reader = new BufferedReader (new FileReader(file));
				while((line = reader.readLine()) != null) {
					String[] valores = line.split(";");
					
					columns.add(this.saveColumns(valores));
				}
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		reader.close();
		return columns;
	}
	
	private List<Students> saveColumns(String arg[]) {
		
		List<Students> valuesColumn = new ArrayList<Students>();
		
		if(arg.length > 0) {
			for(String val : arg) {
				
				valuesColumn.add(new Students(val));
			}
		}
		return valuesColumn;
	}
	
	
	public List<List<String>> printStuff(MultipartFile document){
		File csvFile = new File ("c:/upload_java/"+document.getOriginalFilename());
        String line = "";
        String cvsSplitBy = ";";

        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	if(this.removeBOM(csvFile) ) {
        		while ((line = br.readLine()) != null) {       			
                    String[] row = line.split(cvsSplitBy);
                    for (int i = 0; i < row.length; i++) {                  	
                        if (i >= data.size()) {                      	
                            data.add(new ArrayList<String>());
                        }
                        data.get(i).add(row[i]);
                    }
                }
        	}else{
        		while ((line = br.readLine()) != null) {       			
                    String[] row = line.split(cvsSplitBy);
                    for (int i = 0; i < row.length; i++) {                  	
                        if (i >= data.size()) {                      	
                            data.add(new ArrayList<String>());
                        }
                        data.get(i).add(row[i]);
                    }
                }
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
		return data;
    }
	
	 public boolean removeBOM(File inputFile) throws IOException {
			
		 boolean isOk = false;
		 
	        byte[] fileBytes = new byte[(int) inputFile.length()];

	        FileInputStream inputStream = new FileInputStream(inputFile);
	        inputStream.read(fileBytes);
	        inputStream.close();

	        isOk = (fileBytes.length > 2 && (fileBytes[0] & 0xFF) == 0xEF && (fileBytes[1] & 0xFF) == 0xBB && (fileBytes[2] & 0xFF) == 0xBF) ? true : false;
	            byte[] trimmedBytes = new byte[fileBytes.length - 3];
	            System.arraycopy(fileBytes, 3, trimmedBytes, 0, trimmedBytes.length);

	            FileOutputStream outputStream = new FileOutputStream(inputFile);
	            outputStream.write(trimmedBytes);
	            outputStream.close();
				return isOk;
	    }
}

