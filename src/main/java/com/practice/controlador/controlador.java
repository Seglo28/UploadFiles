package com.practice.controlador;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
	
	//Save Cvs a document, then show it first column content.
	@PostMapping("/readingCsv")
	public String savingAndReaddingCsvFile(@RequestParam("document") MultipartFile document) throws IOException{
		return csvFile.savingAndReaddingACsvFile(document);
	}
}













