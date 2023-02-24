package com.practice.metodoOficiales;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;
import com.practice.especialidades.ImagesDetails;

public class ImgFiles {

	//Upload and image if their dimensions are 512px wide and high; and also you should to add a waterMark
	public String uploadAnImage (MultipartFile image){
		String result = "";
		
		ImagesDetails images = new ImagesDetails();
		
		Tika tika = new Tika();    	
  	  	String mimeType = tika.detect(image.getOriginalFilename());
  	  	File file = new File("c:/upload_java/"+image.getOriginalFilename());
		
	      try{
	    	  FileOutputStream convert = new FileOutputStream(file);
	    	  
	          if ("image/gif".equals(mimeType) | "image/png".equals(mimeType) | "image/jpeg".equals(mimeType) | "image/png".equals(mimeType)) {
	            	     		  		
		            	file.createNewFile();
		            	convert.write(image.getBytes());
		            	convert.close();
		            		            	
		            	if(images.dimensionsImg(file)){
		            		images.waterMark(file);
		            		result = "Uploaded.";
		            	} else {
		            		if(file.delete()) {
			                	result = "Image dimensions are different than 512px in width and height, this program delete it.";
			                } else {
			                	result = "We have a problem, it file didn't delete";
			                }
		            	}
	            } else {
	                convert.close();
	                if(file.delete()) {
	                	result = "This file is not an image.";
	                }
	            }
	      }catch (IOException e) {
	            e.printStackTrace(); 
	      }
	return result;
	}
}

