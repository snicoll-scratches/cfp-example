package com.example.cfp.integration.github;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@CacheConfig(cacheNames = "github")
public class GithubClient {

	private final CounterService counterService;

	private final RestTemplate restTemplate;

	public GithubClient(CounterService counterService, RestTemplate restTemplate) {
		this.counterService = counterService;
		this.restTemplate = restTemplate;
	}

	@Cacheable(key = "'invited-speakers'")
	public List<GithubUser> getInvitedSpeakers() {
		String publicMembersJson = invoke(createRequestEntity(
				"https://api.github.com/orgs/snicoll-scratches/public_members"), String.class).getBody();

		JsonPath login = JsonPath.compile("$[*].login");
		JSONArray usernames = login.read(publicMembersJson);
		List<GithubUser> users = new ArrayList<>();
		for (Object username : usernames) {
			users.add(invoke(createRequestEntity(
					String.format("https://api.github.com/users/%s", username)), GithubUser.class).getBody());
		}
		return users;
	}

	private <T> ResponseEntity<T> invoke(RequestEntity<?> request, Class<T> type) {
		counterService.increment("cfp.github.requests");
		try {
			return this.restTemplate.exchange(request, type);
		}
		catch (RestClientException ex) {
			counterService.increment("cfp.github.failures");
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
