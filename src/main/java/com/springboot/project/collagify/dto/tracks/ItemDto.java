package com.springboot.project.collagify.dto.tracks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemDto {
	private AlbumDto album;

	public AlbumDto getAlbum() {
		return album;
	}

	public void setAlbum(AlbumDto album) {
		this.album = album;
	}

	@Override
	public String toString() {
		return "ItemDto [album=" + album + "]";
	}
}
