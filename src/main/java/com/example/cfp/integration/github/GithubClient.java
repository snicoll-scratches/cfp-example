package com.example.cfp.integration.github;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubClient {

	private final CounterService counterService;

	private final RestTemplate restTemplate;

	public GithubClient(CounterService counterService, RestTemplate restTemplate) {
		this.counterService = counterService;
		this.restTemplate = restTemplate;
	}

	@Cacheable("commits")
	public List<Commit> getRecentCommits(String organization, String project) {
		String url = String.format(
				"https://api.github.com/repos/%s/%s/commits", organization, project);
		ResponseEntity<Commit[]> response = invoke(createRequestEntity(url), Commit[].class);
		return Arrays.asList(response.getBody());
	}

	private <T> ResponseEntity<T> invoke(RequestEntity<?> request, Class<T> type) {
		this.counterService.increment("cfp.github.requests");
		try {
			return this.restTemplate.exchange(request, type);
		}
		catch (RestClientException ex) {
			this.counterService.increment("cfp.github.failures");
			throw ex;
		}
	}

	private RequestEntity<?> createRequestEntity(String url) {
		try {
			return RequestEntity.get(new URI(url))
					.accept(MediaType.APPLICATION_JSON).build();
		}
		catch (URISyntaxException ex) {
			throw new IllegalStateException("Invalid URL " + url, ex);
		}
	}

}
