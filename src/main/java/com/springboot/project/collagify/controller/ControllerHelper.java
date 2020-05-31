package com.springboot.project.collagify.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.springboot.project.collagify.dto.SelectionDto;
import com.springboot.project.collagify.dto.tracks.TracksDto;
import com.springboot.project.collagify.exceptions.NoTopTracksException;


public final class ControllerHelper {
	
	private static final Logger logger = Logger.getLogger(ControllerHelper.class.getName());
	
	public static String getPlaylistId(String playlistLink) {
		
		logger.info("Obtaining ID from playlist link...");
	
		String playlistId = "";

		try {
			URI uri = new URI(playlistLink);
			
			String path = uri.getPath();

			playlistId = path.substring(path.lastIndexOf('/') + 1);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return playlistId;
	}

	public static void createCollage(TracksDto tracks, SelectionDto selectionDto) throws IOException, NoTopTracksException {
		
		// Get necessary variables from selectionDto
		int displayWidth = selectionDto.getDisplayWidth();
		
		int displayHeight = selectionDto.getDisplayHeight();
		
		int rows = selectionDto.getRows();
		
		int columns = selectionDto.getColumns(); 
		
		// Check if there exists any tracks 
		if(tracks.getItems().length == 0)
			throw new NoTopTracksException("Your account does not have enough information to obtain a list of top tracks");
		
		// Create ArrayList to hold the 640 x 640 images URLs
		String[] urls = new String[tracks.getItems().length];
		
		
		// Extract urls and add them to list 
		for (int i = 0; i < urls.length; i++) {
			
			// Error Handling - if 640x640 DNE go to 300x300 etc.
			try {				
				if (tracks.getItems()[i].getAlbum().getImages()[0] != null)
					
					urls[i] = (tracks.getItems()[i].getAlbum().getImages()[0].getUrl());
				
				else if (tracks.getItems()[i].getAlbum().getImages()[1] != null) 
					
					urls[i] = (tracks.getItems()[i].getAlbum().getImages()[1].getUrl());
				
				else 
					urls[i] = (tracks.getItems()[i].getAlbum().getImages()[2].getUrl());
				
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
			
			// Create buffered images from URLs 
			logger.info("Creating Images from URLs");
			
			BufferedImage images[] = new BufferedImage[urls.length];
			
			for(int j = 0; j < images.length; j++)
			{
				images[j] = ImageIO.read(new URL(urls[j]));
			}
			
			// Create the collage with a buffered image, use Graphics2D to draw each image
			// onto the collage
			BufferedImage collage = new BufferedImage(displayWidth, displayHeight, 
					BufferedImage.TYPE_INT_RGB);
			
			Graphics2D graphics = collage.createGraphics();
			
			
			// Calculate image width by distributing 90% of the respective display to the
			// number of column or row evenly.
			int imageWidth = 0;

			int imageHeight = 0;
			
			float percentage = 100.0f;

			if (displayHeight > displayWidth) {
				
				do {
					
					percentage = percentage - 10.0f;

					imageWidth = (int) ((displayWidth * (percentage / 100.0f))) / columns;
					
				} while ((imageWidth * rows) > displayHeight);

				imageHeight = imageWidth;
			
			} else {
				
				do {
					
					percentage = percentage - 10.0f;
					
					imageHeight = (int) ((displayHeight * (percentage / 100.0f))) / rows;
					
				} while((imageHeight * columns) > displayWidth);
				
				imageWidth = imageHeight;
			}
			
			// Get the remaining 10% of the display and distribute to the offset, then
			// distribute each offset evenly between the respective column/row
			int offsetX = 0;

			int offsetY = 0;

			if (displayHeight > displayWidth) {
				
				offsetX = (int) (displayWidth * ((100.0f - percentage) / 100.0f)) / (columns + 1);

				offsetY = (int) (displayHeight - (imageHeight * rows)) / (rows + 1);
			
			} else {
				offsetX = (int) (displayWidth - (imageWidth * columns)) / (columns + 1);

				offsetY = (int) (displayHeight * ((100.0f - percentage) / 100.0f)) / (rows + 1);
			}
			
			// Get background color from selectionDto
			Color backgroundColor = getBackgroundColor(selectionDto);
			
			// Set a color for the background
			graphics.setColor(backgroundColor);

			graphics.fillRect(0, 0, displayWidth, displayHeight);
			
			// Set init position
			int posX = offsetX;

			int posY = offsetY;
			
			// Place images in a new buffered image using the draw function from graphics2d
			// in a grid like fashion
			int index = 0;

			for (int k = 0; k < rows; k++) {
				for (int j = 0; j < columns; j++) {
					graphics.drawImage(images[index], posX, posY, imageWidth, imageHeight, null);

					posX = posX + imageWidth + offsetX;

					if (index < images.length - 1)
						index = index + 1;
					else
						index = 0;
				}

				// Reset X
				posX = offsetX;

				// Add the offset to Y
				posY = posY + imageHeight + offsetY;
			}

			graphics.dispose();
			
			logger.info("Writing Collage to the desired file...");
			
			File file = new File("collages\\collage.jpeg");
						
			// Write the collage to a the desired file.
			ImageIO.write(collage, "jpeg", file);
	}
	
	private static Color getBackgroundColor(SelectionDto selectionDto)
	{
		String hexColor = selectionDto.getHex();
		
		if(hexColor == null)
			return new Color(255, 255, 255);
		
		if(hexColor.matches("/#([a-f0-9]{3}){1,2}\\b/i"))
		{
			return new Color(
					 Integer.valueOf(hexColor.substring( 1, 3 ), 16 ),
			            Integer.valueOf(hexColor.substring( 3, 5 ), 16 ),
			            Integer.valueOf(hexColor.substring( 5, 7 ), 16 ));
		}
		else 
			return new Color(255, 255, 255);
	}
}
