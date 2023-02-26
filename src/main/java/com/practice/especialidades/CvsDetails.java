package com.practice.especialidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

public class CvsDetails {

	public String saveUsingTika(MultipartFile document) throws IOException{
		String result = "";
		Tika tika = new Tika();
		File file = new File ("c:/upload_java/"+document.getOriginalFilename());
		
		try {
		String mimeType = tika.detect(document.getOriginalFilename());
		if("text/csv".equals(mimeType)) {
			FileOutputStream convert = new FileOutputStream(file);
			file.createNewFile();
			convert.write(document.getBytes());
			convert.close();
			result = "Uploaded.-";
			}else{
                result = "This is not a CSV File.-";
            }
		}catch (IOException e) {
            e.printStackTrace(); 
            }
		return result;
		}
	}
