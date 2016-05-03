package com.example.cfp.web;

import java.io.IOException;

import com.example.cfp.submission.SubmissionRequest;
import com.example.cfp.submission.SubmissionService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CfpController.class, secure = false)
public class CfpControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SubmissionService submissionService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void submitNewSpeaker() throws Exception {
		SubmissionRequest form = new SubmissionRequest();
		form.setSpeaker("jsmith", "John", "Smith");
		form.setTalk("Alice in Wonderland", "my abstract", "this rocks");
		given(this.submissionService.create(form)).willReturn(null);
		this.mvc.perform(post("/cfp/submissions")
				.content(jsonRequest(form)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		verify(this.submissionService).create(form);
	}

	private String jsonRequest(SubmissionRequest form) throws IOException {
		return this.objectMapper.writer().writeValueAsString(form);
	}

}
