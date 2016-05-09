package com.example.cfp.web;

import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.cfp.domain.Speaker;
import com.example.cfp.domain.SpeakerRepository;
import com.example.cfp.domain.Track;
import com.example.cfp.submission.SubmissionService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CfpController.class)
public class CfpControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SubmissionService submissionService;

	@MockBean
	private SpeakerRepository speakerRepository;


	@Test
	@WithMockUser("jsmith")
	public void submitNewSpeaker() throws Exception {
		given(this.speakerRepository.findByGithub("jsmith")).willReturn(new Speaker("jsmith", "John Smith"));

		SubmissionForm form = new SubmissionForm();
		form.setTitle("Alice in Wonderland");
		form.setSummary("my abstract");
		form.setNotes("this rocks");
		given(this.submissionService.create(any())).willReturn(null);
		this.mvc.perform(post("/cfp/submit")
				.param("title", "Alice in Wonderland")
				.param("summary", "my abstract")
				.param("notes", "this rocks")
				.param("track", Track.ALTERNATE_LANGUAGES.getId())
				.with(csrf()))
				.andExpect(status().isOk());
		verify(this.submissionService).create(any());
		verify(this.speakerRepository).findByGithub(eq("jsmith"));
	}

}
