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
		this.githubClient = new GithubClient(this.counterService, restTemplate);
		this.mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void getRecentCommits() {
		expectJson("https://api.github.com/repos/spring-projects/spring-framework/commits",
				"github/spring-framework-commits.json");
		List<Commit> recentCommits = this.githubClient.getRecentCommits(
				"spring-projects", "spring-framework");
		this.mockServer.verify();
		assertThat(recentCommits).hasSize(5);
		assertCommit(recentCommits.get(0), "7737c3c",
				"Warn about non-static BeanDefinitionRegistryPostProcessor declarations on @Configuration classes",
				"2016-05-02T13:19:05Z",
				"jhoeller", "Juergen Hoeller", "https://avatars.githubusercontent.com/u/1263688?v=3");
		assertCommit(recentCommits.get(3), "09b45d2",
				"Validate callback is always invoked in DMLC#stop",
				"2016-05-02T11:33:05Z",
				"snicoll", "Stephane Nicoll", "https://avatars.githubusercontent.com/u/490484?v=3");
		verify(this.counterService, times(1)).increment("cfp.github.requests");
	}

	@Test
	public void getRecentCommitsNoCommit() {
		expectJson("https://api.github.com/repos/spring-projects/spring-boot/commits",
				"github/no-commit.json");
		List<Commit> latestCommit = this.githubClient.getRecentCommits("spring-projects", "spring-boot");
		assertThat(latestCommit).hasSize(0);
		verify(this.counterService, times(1)).increment("cfp.github.requests");
	}

	@Test
	public void getUser() {
		expectJson("https://api.github.com/users/jsmith", "github/jsmith.json");
		GithubUser user = this.githubClient.getUser("jsmith");
		assertGithubUser(user, "John Smith", "Acme Inc.",
				"https://acme.org/blog", "https://acme.org/team/jsmith/avatar");
	}

	private void expectJson(String url, String bodyPath) {
		this.mockServer.expect(requestTo(url))
				.andExpect(method(HttpMethod.GET))
				.andExpect(header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
				.andRespond(withStatus(HttpStatus.OK)
						.body(new ClassPathResource(bodyPath))
						.contentType(MediaType.APPLICATION_JSON));
	}

	private void assertCommit(Commit commit, String sha, String message, String date,
			String committerId, String committerName, String committerAvatar) {
		assertThat(commit).isNotNull();
		assertThat(commit.getSha()).isEqualTo(sha);
		assertThat(commit.getMessage()).isEqualTo(message);
		assertThat(commit.getDate().toString()).isEqualTo(date);
		Commit.Committer committer = commit.getCommitter();
		assertThat(committer).isNotNull();
		assertThat(committer.getId()).isEqualTo(committerId);
		assertThat(committer.getName()).isEqualTo(committerName);
		assertThat(committer.getAvatarUrl()).isEqualTo(committerAvatar);
	}

	private void assertGithubUser(GithubUser actual, String name, String company,
			String blog, String avatar) {
		assertThat(actual.getName()).isEqualTo(name);
		assertThat(actual.getCompany()).isEqualTo(company);
		assertThat(actual.getBlog()).isEqualTo(blog);
		assertThat(actual.getAvatar()).isEqualTo(avatar);
	}

}
