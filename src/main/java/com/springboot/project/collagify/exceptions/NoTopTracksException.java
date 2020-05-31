package com.springboot.project.collagify.exceptions;

public class NoTopTracksException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoTopTracksException(String errorMessage) {
		super(errorMessage);
	}
}
