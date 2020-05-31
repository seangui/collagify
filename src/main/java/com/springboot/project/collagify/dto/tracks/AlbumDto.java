package com.springboot.project.collagify.dto.tracks;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AlbumDto {
	private ImageDto[] images;
	
	private String name; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImageDto[] getImages() {
		return images;
	}

	public void setImages(ImageDto[] images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "AlbumDto [images=" + Arrays.toString(images) + "]";
	}
}
