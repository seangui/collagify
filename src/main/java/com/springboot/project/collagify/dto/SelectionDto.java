package com.springboot.project.collagify.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SelectionDto {
			
	@Min(value=1, message="Rows must be 1 - 5")
	@Max(value=5, message="Rows must be 1 - 5")
	private int rows;
	
	@Min(value=1, message="Columns must be 1 - 5")
	@Max(value=5, message="Columns must be 1 - 5")
	private int columns; 
	
	private int displayWidth;
	
	private int displayHeight; 
	
	private String hex;
	
	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public int getDisplayWidth() {
		return displayWidth;
	}

	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	public void setDisplayHeight(int displayHeight) {
		this.displayHeight = displayHeight;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
}
