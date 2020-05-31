package com.springboot.project.collagify.service;

import com.springboot.project.collagify.dto.tracks.TracksDto;

public interface SpotifyService {
	
	public TracksDto getTopTracks(int limit);

}
