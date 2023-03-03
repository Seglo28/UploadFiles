package com.practice.especialidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
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
	
	
	public List<List<String>> printStuff(MultipartFile document) throws IOException{
		File csvFile = new File ("c:/upload_java/"+document.getOriginalFilename());
        String line = "";
        String cvsSplitBy = ";";

        List<List<String>> data = new ArrayList<>();

        if(this.removeBOM(document)) {
        }

        System.out.println("PRINT-STUFF: Estoy dentro del WHILE.");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        		while ((line = br.readLine()) != null) {
                    String[] row = line.split(cvsSplitBy);
                    for (int i = 0; i < row.length; i++) {   
                        if (i >= data.size()) {                      	
                            data.add(new ArrayList<String>());
                        }
                        data.get(i).add(row[i]);
                    }
                }
        	System.out.println("--------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
		return data;
    }
	
	
	private static final String UTF8_BOM = "\uFEFF";
	 public boolean BOMinside(MultipartFile document) throws IOException {
			
		 boolean isOk = false;
		 File inputFile = new File ("c:/upload_java/"+document.getOriginalFilename());
		 
		 this.isBoom(inputFile);
				return this.isBoom(inputFile);
	    }
	 
	 private boolean isBoom(File file) {
		 boolean isOk = false;
		 if(!file.exists()) {
			 System.out.println("METODO IS BOOM? " + "El archivo no existe");
			 return false;
		 }
		 
		 byte[] BOM = new byte[3];
		 
		 try (FileInputStream input = new FileInputStream(file)) {
			 input.read(BOM);
			 input.close();
			 
			 
			 String content = new String(Hex.encodeHex(BOM));
			 System.out.println(file.getName()+ " hex "+content);
	          if ("efbbbf".equalsIgnoreCase(content)) {
	              isOk = true;
	          } else {
	        	  isOk = false;
	          }
	          System.out.println("METODO IS BOOM? " +"isOK "+ isOk);
			 System.out.println("METODO IS BOOM? " +"el valor de bom es: " + BOM.toString());
			 
			 
		 } catch (Exception e) {
			 System.out.println("METODO IS BOOM? " +"a ocurrido un error " + BOM);
			 isOk = false;
		}
		 return isOk;
	 }
	 
	 private boolean removeBOM (MultipartFile document) throws IOException {
		 Path path = Paths.get("c:/upload_java/"+document.getOriginalFilename());
		 
		 if(this.BOMinside(document)) {
			 byte[] bytes = Files.readAllBytes(path);
			 
			 ByteBuffer bb = ByteBuffer.wrap(bytes);
			 System.out.println("ELIMINAR EL BOM " +"Encontramos el BOM en el metodo eliminar");
			 
			 byte[] bom = new byte[3];
			 
			 bb.get(bom, 0, bom.length);
			 
			 byte[] contentAfterFirst3Bytes = new byte[bytes.length - 3];
	          bb.get(contentAfterFirst3Bytes, 0, contentAfterFirst3Bytes.length);
	          System.out.println("ELIMINAR EL BOM " +"Removimos el DOM!");
	          
	          Files.write(path, contentAfterFirst3Bytes);
		 } else {
	          System.out.println("ELIMINAR EL BOM " +"This file doesn't contains UTF-8 BOM!");
	      }
		return false;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}