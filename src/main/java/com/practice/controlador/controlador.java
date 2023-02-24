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

import com.practice.especialidades.ImagesDetails;
import com.practice.metodoOficiales.CsvFiles;
import com.practice.metodoOficiales.ImgFiles;

@RestController
@RequestMapping("/controlador")
public class controlador {
	
	//creating classes objects
	CsvFiles csvFile = new CsvFiles();
	ImgFiles imgFile = new ImgFiles();
	
	//Upload a document csv
	@PostMapping("/archivoCSV")
	public String archivoCSV (@RequestParam("document") MultipartFile document) throws IOException {
		return csvFile.uploadAnCsvFile(document);
	}
	
	//Upload and image if their dimensions are 512px wide and high; and also you should to add a waterMark
	@PostMapping("/imagenes")
	public String imagenes(@RequestParam("imagen") MultipartFile image){
		return imgFile.uploadAnImage(image);
	}
	
	//Crear un metodo que solo salve CSV documentos y que me traiga todo lo de la primera fila.
	
	@PostMapping
	public String savingAndReaddingAnCsvFile() {
		String salida = "";
		
		
		
		return salida;
	}
}













