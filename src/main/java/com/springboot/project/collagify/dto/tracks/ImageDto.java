package com.springboot.project.collagify.dto.tracks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ImageDto {
	private int height; 
	
	private int width;
	
	private String url;
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "ImageDto [height=" + height + ", url=" + url + ", width=" + width + "]";
	}
	
}
