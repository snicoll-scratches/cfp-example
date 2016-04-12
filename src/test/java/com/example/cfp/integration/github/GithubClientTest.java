package com.example.cfp.integration.github;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class GithubClientTest {

	private CounterService counterService;

	private GithubClient githubClient;

	private MockRestServiceServer mockServer;

	@Before
	public void setUp() {
		this.counterService = mock(CounterService.class);
		RestTemplate restTemplate = new RestTemplate();
		this.githubClient = new GithubClient(counterService, restTemplate);
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void publishSimpleDocument() {
		expectJson("https://api.github.com/orgs/snicoll-scratches/public_members", "github/public-members.json");
		expectJson("https://api.github.com/users/jsmith", "github/jsmith.json");
		expectJson("https://api.github.com/users/bdupont", "github/bdupont.json");

		List<GithubUser> speakers = this.githubClient.getInvitedSpeakers();
		mockServer.verify();
		assertThat(speakers).hasSize(2);
		assertGithubUser(speakers.get(0), "John Smith", "Acme Inc.",
				"https://acme.org/blog", "https://acme.org/team/jsmith/avatar");
		assertGithubUser(speakers.get(1), "Bernard Dupont", "Acme Inc.",
				"https://acme.org/blog", null);
	}

	@Test
	public void publishDocumentIncreaseCounter() {
		expectJson("https://api.github.com/orgs/snicoll-scratches/public_members", "github/public-members.json");
		expectJson("https://api.github.com/users/jsmith", "github/jsmith.json");
		expectJson("https://api.github.com/users/bdupont", "github/bdupont.json");

		this.githubClient.getInvitedSpeakers();
		verify(this.counterService, times(3)).increment("cfp.github.requests");
	}

	private void expectJson(String url, String bodyPath) {
		mockServer.expect(requestTo(url))
				.andExpect(method(HttpMethod.GET))
				.andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
				.andRespond(withStatus(HttpStatus.OK)
						.body(new ClassPathResource(bodyPath))
						.contentType(MediaType.APPLICATION_JSON));
	}

	private void assertGithubUser(GithubUser actual, String name, String company,
			String blog, String avatar) {
		assertThat(actual.getName()).isEqualTo(name);
		assertThat(actual.getCompany()).isEqualTo(company);
		assertThat(actual.getBlog()).isEqualTo(blog);
		assertThat(actual.getAvatar()).isEqualTo(avatar);
	}

}
