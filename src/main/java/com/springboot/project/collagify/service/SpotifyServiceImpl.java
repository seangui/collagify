package com.springboot.project.collagify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.springboot.project.collagify.dto.tracks.TracksDto;

@Service
public class SpotifyServiceImpl implements SpotifyService {

	@Autowired
	private WebClient webClient;

	@Override
	public TracksDto getTopTracks(int limit) {

		String path = "/me/top/tracks" + "?limit=" + Integer.toString(limit);

		TracksDto tracks = webClient.get().uri(path).retrieve().bodyToMono(TracksDto.class).block();

		return tracks;
	}
}
