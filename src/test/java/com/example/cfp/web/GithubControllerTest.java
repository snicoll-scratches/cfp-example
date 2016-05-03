package com.example.cfp.web;

import java.util.Collections;

import com.example.cfp.integration.github.Commit;
import com.example.cfp.integration.github.Commit.Committer;
import com.example.cfp.integration.github.GithubClient;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GithubController.class, secure = false)
public class GithubControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private GithubClient githubClient;

	@Test
	public void getLatestCommit() throws Exception {
		given(this.githubClient.getRecentCommits("spring-projects", "spring-framework"))
				.willReturn(Collections.singletonList(new Commit("abcdefg", "Polish",
						new Committer("jsmith", "John Smith", null))));

		this.mvc.perform(post("/public/github/spring-projects/spring-framework/commits/latest")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sha", is("abcdefg")))
				.andExpect(jsonPath("$.message", is("Polish")))
				.andExpect(jsonPath("$.committer.id", is("jsmith")))
				.andExpect(jsonPath("$.committer.name", is("John Smith")));
		verify(this.githubClient).getRecentCommits("spring-projects", "spring-framework");
	}

}
