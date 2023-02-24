package com.practice.especialidades;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ImagesDetails {
	
	//Validating images dimensions
	public boolean dimensionsImg(File img) {
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
            
            isOK = (width == widthFile && height == heightFile) ? true : false;
            stream.close();
           
        } catch (IOException e) {
            System.out.println("Error reading image file: " + e);
            isOK = false;
        }
		return isOK;
	}
	
	
	//Creating the waterMark
	public void waterMark(File image) {
		
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
