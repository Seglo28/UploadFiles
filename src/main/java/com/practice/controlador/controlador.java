package com.practice.controlador;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Permission;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/controlador")
public class controlador {
	
	//17/02/23
	@GetMapping("/cuadro")
	public String cuadro (@RequestParam String parametro) {
		String salida = "";
		
		
		
		for(int filas = 0; filas < 5; filas++) {
			
			for(int cols= 0; cols < 5; cols++) {
				
				if(cols == 0 || cols == 4) {
					salida += "x";
				} else if(cols == 0 && (filas >= 0 && filas < 5)){
					salida += "x";
				}
				else {
					salida += parametro;	
				}
			}
			salida += "\n";	
		}
		return salida;
	}
	
	@GetMapping("/velocidad")
	public String velocidad (double desplazamiento, double tiempo) {
		String velocidad;
		if(desplazamiento>0 && tiempo>0) {
			velocidad = "La velocidad es: "+ Double.toString(desplazamiento/tiempo)+" m/s.";
		}else {
			velocidad = "Los datos deben ser mayores a 0";
		}
		return velocidad;
	}
	
	@PostMapping("/archivoCSV")
	public String archivoCSV (@RequestParam("documento") MultipartFile documento) throws IOException {
		String salida = "";
		      try{
		    	  Tika tika = new Tika();
		    	// Detección del tipo MIME del archivo
		    	  String mimeType = tika.detect(documento.getOriginalFilename());
		            // Comparación con el tipo MIME esperado (en este ejemplo, "text/csv")
		            if ("text/csv".equals(mimeType)) {
		            	File archivo = new File("c:/upload_java/"+documento.getOriginalFilename());
		            	FileOutputStream convertir = new FileOutputStream(archivo);
		            	//Valido que si el archivo ya existe no puedo crear uno nuevo
		            	archivo.createNewFile();
		            	convertir.write(documento.getBytes());
		            	salida = "archivo cargado correctamente";
		            } else {
		                salida = "El archivo no es un archivo CSV";
		            }
		      }catch (IOException e) {
		            e.printStackTrace(); 
		      }
		return salida;
	}
	
	@PostMapping("/imagenes")
	//Determinar tipo de imagenes, sus dimensiones deben ser igual a 512x512. Guardar casa imagen n cantidad de veces.
	public String imagenes(@RequestParam("imagen") MultipartFile image){
		String salida = "";
		Tika tika = new Tika();    	
  	  	String mimeType = tika.detect(image.getOriginalFilename());
  	  	File archivo = new File("c:/upload_java/"+image.getOriginalFilename());
		
	      try{
	    	  FileOutputStream convertir = new FileOutputStream(archivo);
	    	  
	          if ("image/gif".equals(mimeType) | "image/png".equals(mimeType) | "image/jpeg".equals(mimeType) | "image/png".equals(mimeType)) {
	            	
	            		  		
		            	archivo.createNewFile();
		            	convertir.write(image.getBytes());
		            	convertir.close();
		            	salida = "archivo cargado correctamente";
		            	
		            	
		            	if(this.dimensionesImagen(archivo)) {
		            		this.waterMark(archivo);
		            		salida = "El archivo cumple";
		            	} else {
		            		salida = "No cumple con los requisitos.";
		            		if(archivo.delete()) {
			                	salida = "Se elimino";
			                } else {
			                	salida = "No se elimino";
			                }
		            	}
	            } else {
	                salida = "No es un archivo de tipo IMAGEN.-";
	                convertir.close();
	                if(archivo.delete()) {
	                	salida = "Se elimino";
	                } else {
	                	salida = "No se elimino";
	                }
	            }
	            // this.deleteAnImage(archivo);
	      }catch (IOException e) {
	            e.printStackTrace(); 
	      }
	return salida;
	}
	
	
	private boolean dimensionesImagen(File img) {
		boolean isOK = false;
		int width = 0, height = 0;
		final int widthFile = 512, heightFile = 512;
		
		InputStream stream = null;
		
		try {
            // Read the image file
			
			stream = new FileInputStream(img);
            BufferedImage image = ImageIO.read(stream);

            // Get the image dimensions
            width = image.getWidth();
            height = image.getHeight();
            
            System.out.println("w:" + width +" h:" + height);
            
            isOK = (width == widthFile && height == heightFile) ? true : false;
            
            stream.close();
           
            
        } catch (IOException e) {
            System.out.println("Error reading image file: " + e);
            isOK = false;
        }
		
		return isOK;
	}
	
	private void waterMark(File image) {
		
	    String watermarkText = "Gloria's WATERMARK";

	    try {
	      // Read the input image
	      BufferedImage inputImage = ImageIO.read(image);

	      // Create a new BufferedImage with the same dimensions as the input image
	      BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

	      // Draw the input image onto the output image
	      Graphics2D g2d = outputImage.createGraphics();
	      g2d.drawImage(inputImage, 0, 0, null);

	      // Add the watermark text to the output image
	      AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	      g2d.setComposite(alphaChannel);
	      g2d.setColor(Color.WHITE);
	      g2d.setFont(new Font("Arial", Font.BOLD, 30));
	      g2d.drawString(watermarkText, inputImage.getWidth()/2 - 100, inputImage.getHeight());
	      g2d.dispose();

	      // Write the output image to a file
	      ImageIO.write(outputImage, "jpg", image);
	    } catch (IOException e) {
	      System.out.println("Error: " + e.getMessage());
	    }
	  }
}













