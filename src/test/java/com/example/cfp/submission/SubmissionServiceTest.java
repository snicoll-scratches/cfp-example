package com.example.cfp.submission;

import java.util.List;

import com.example.cfp.domain.Submission;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE, classes = SubmissionService.class))
public class SubmissionServiceTest {

	@Autowired
	private SubmissionService submissionService;

	@Test
	public void submitTalk() throws Exception {
		SubmissionRequest request = new SubmissionRequest();
		request.setSpeaker("john@example.com", "John Smith");
		request.setTalk("Alice in Wonderland", "my abstract", "this rocks");
		Submission submission = this.submissionService.create(request);
		assertThat(submission).isNotNull();
		assertThat(submission.getSpeakerEmail()).isEqualTo("john@example.com");
		assertThat(submission.getSpeakerName()).isEqualTo("John Smith");
		assertThat(submission.getTitle()).isEqualTo("Alice in Wonderland");
		assertThat(submission.getSummary()).isEqualTo("my abstract");
		assertThat(submission.getNotes()).isEqualTo("this rocks");

		List<Submission> submissions = this.submissionService.getSubmissionRepository()
				.findBySpeakerEmail("john@example.com");
		assertThat(submissions).hasSize(1);
	}

}
