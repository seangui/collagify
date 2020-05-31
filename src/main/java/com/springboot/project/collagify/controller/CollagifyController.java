package com.springboot.project.collagify.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.project.collagify.dto.SelectionDto;
import com.springboot.project.collagify.dto.tracks.TracksDto;
import com.springboot.project.collagify.exceptions.NoTopTracksException;
import com.springboot.project.collagify.service.SpotifyService;

@Controller
public class CollagifyController {

	@Autowired
	private SpotifyService spotifyService;

	private Logger logger = Logger.getLogger(getClass().getName());

	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/")
	public String getHome() {
		return "home";
	}
	
	@GetMapping("/login-failed")
	public String getFailedLogin() {
		return "login-failed";
	}
	
	
	@GetMapping("/set-up")
	public String getSetUp(Model model) {

		SelectionDto selectionDto = new SelectionDto();

		model.addAttribute("selectionDto", selectionDto);

		return "set-up";
	}

	@PostMapping("/collage")
	public String getCollage(@ModelAttribute("selectionDto") @Valid SelectionDto selectionDto, Errors errors, Model model) {

		if (errors.hasErrors())
			return "set-up";

		logger.info("\nRows x Columns: " + selectionDto.getRows() + "x" + selectionDto.getColumns()
				+ "\nWidth x Height: " + selectionDto.getDisplayWidth() + "x" + selectionDto.getDisplayHeight()
				+ "\nHex: " + selectionDto.getHex());

		logger.info("Making API Call to get Top Tracks...");

		// Make API call to get current user top tracks
		TracksDto tracks = spotifyService.getTopTracks((selectionDto.getRows() * selectionDto.getColumns()));

		// Extract the Images from the TracksDto
		try {

			logger.info("Creating collage with ControllerHelper...");
			
			ControllerHelper.createCollage(tracks, selectionDto);

		} catch (IOException e) {
			
			return "redirect:/error-page";
			
		}catch(NoTopTracksException ie) {
			
			return "redirect:/no-tracks-error";
		}

		logger.info("Finished Creating Collage");
		

		return "collage";
	}
	
	@GetMapping("/error-page")
	public String error() {
		return "error-page";
	}
	
	@GetMapping("no-tracks-error")
	public String noTracksError() {
		return "no-tracks-error";
	}
	
	@GetMapping(
			value="/download",
			produces = MediaType.IMAGE_JPEG_VALUE
			)
	public @ResponseBody byte[] getDownload() throws IOException{
		File collageFile = new File("collages\\collage.jpeg");
		
		InputStream in = FileUtils.openInputStream(collageFile);
		
		collageFile.delete();
		
		return IOUtils.toByteArray(in);
	}
}
