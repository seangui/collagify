package com.springboot.project.collagify.dto.tracks;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TracksDto {
	private ItemDto[] items;

	public ItemDto[] getItems() {
		return items;
	}

	public void setItems(ItemDto[] items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "TracksDto [items=" + Arrays.toString(items) + "]";
	}
}
