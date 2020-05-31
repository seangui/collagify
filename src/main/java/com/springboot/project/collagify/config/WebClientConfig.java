package com.springboot.project.collagify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	// ClientManager will handle the refresh tokens and access tokens in the AuthorizationCodeFlow 
	// we will use this to be integrated with our FilterExchangeFunction which will be integrated with 
	// our WebClient so we can use our webclient to consume REST API requests 
	// as we would normally dedicating the OAuth2 logic to this part of the code. 
	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository authorizedClientRepository) {
		
		OAuth2AuthorizedClientProvider authorizedClientProvider = 
				OAuth2AuthorizedClientProviderBuilder.builder()
				.authorizationCode()
				.refreshToken()
				.build();
		
		DefaultOAuth2AuthorizedClientManager authorizedClientManager =
	            new DefaultOAuth2AuthorizedClientManager(
	                    clientRegistrationRepository, authorizedClientRepository);
	   
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

	    return authorizedClientManager;
	}
	
	
	@Bean
	public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = 
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
		
		oauth2Client.setDefaultClientRegistrationId("spotify");
		
		return WebClient.builder()
				.apply(oauth2Client.oauth2Configuration())
				.baseUrl("https://api.spotify.com/v1")
				.build();
	}
}
