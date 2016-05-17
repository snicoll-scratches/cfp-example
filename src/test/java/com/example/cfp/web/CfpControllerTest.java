package com.example.cfp.web;

import com.example.cfp.domain.Submission;
import com.example.cfp.domain.Track;
import com.example.cfp.domain.User;
import com.example.cfp.submission.SubmissionService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CfpController.class)
public class CfpControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SubmissionService submissionService;

	@WithMockUser("jsmith")
	@Test
	public void submitTalk() throws Exception {
		Authentication authentication = new TestingAuthenticationToken(
				new User("jsmith", "John Smith"), "secret", "ROLE_USER");

		given(this.submissionService.create(any())).willReturn(new Submission());
		this.mvc.perform(post("/submit")
				.param("title", "Alice in Wonderland")
				.param("summary", "my abstract")
				.param("track", Track.ALTERNATE_LANGUAGES.getId())
				.param("notes", "this rocks")
				.with(authentication(authentication))
				.with(csrf()))
				.andExpect(status().isFound())
				.andExpect(header().string(HttpHeaders.LOCATION, "/submit?navSection=submit"));
		verify(this.submissionService).create(any());
	}

}
