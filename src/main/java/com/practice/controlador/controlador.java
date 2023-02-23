package com.practice.controlador;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.FilenameFilter;
import org.apache.commons.io.FilenameUtils;

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
		
	      try{
	    	  Tika tika = new Tika();
	    	
	    	  String mimeType = tika.detect(image.getOriginalFilename());
	            
	            if ("image/gif".equals(mimeType) | "image/png".equals(mimeType) | "image/jpeg".equals(mimeType) | "image/png".equals(mimeType)) {
	            	File archivo = new File("c:/upload_java/"+image.getOriginalFilename());
	            	FileOutputStream convertir = new FileOutputStream(archivo);
	            	
	            	archivo.createNewFile();
	            	convertir.write(image.getBytes());
	            	salida = "archivo cargado correctamente";
	            } else {
	                salida = "El archivo no es un archivo CSV";
	            }
	      }catch (IOException e) {
	            e.printStackTrace(); 
	      }
	return salida;
	}
	
	//@PostMapping("/imagenes")
	    
}













