package com.example.cfp.submission;

import java.util.List;

import com.example.cfp.domain.Speaker;
import com.example.cfp.domain.Submission;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE, classes = SubmissionService.class))
@ImportAutoConfiguration({CacheAutoConfiguration.class, FlywayAutoConfiguration.class})
@TestPropertySource(properties = "spring.cache.type=none")
public class SubmissionServiceTest {

	@Autowired
	private SubmissionService submissionService;

	@Test
	public void submitNewSpeaker() throws Exception {
		SubmissionRequest request = new SubmissionRequest();
		request.setSpeaker("jsmith", "John", "Smith");
		request.setTalk("Alice in Wonderland", "my abstract", "this rocks");
		Submission submission = submissionService.create(request);
		assertThat(submission).isNotNull();
		assertThat(submission.getSpeaker()).isNotNull();
		assertThat(submission.getSpeaker().getGithub()).isEqualTo("jsmith");
		assertThat(submission.getSpeaker().getFirstName()).isEqualTo("John");
		assertThat(submission.getSpeaker().getLastName()).isEqualTo("Smith");
		assertThat(submission.getTitle()).isEqualTo("Alice in Wonderland");
		assertThat(submission.getSummary()).isEqualTo("my abstract");
		assertThat(submission.getNotes()).isEqualTo("this rocks");

		Speaker jsmith = this.submissionService.getSpeakerRepository()
				.findByGithub("jsmith");
		assertThat(jsmith).isNotNull();
		List<Submission> submissions = this.submissionService.getSubmissionRepository()
				.findBySpeaker(jsmith);
		assertThat(submissions).hasSize(1);
	}

	@Test
	public void submitExistingSpeaker() throws Exception {
		Speaker existing = this.submissionService.getSpeakerRepository()
				.save(new Speaker("fbar", "Foo", "Bar"));
		SubmissionRequest request = new SubmissionRequest();
		request.setSpeaker("fbar", "Foo", "Bar");
		request.setTalk("Alice in Wonderland", "my abstract", "this rocks");
		Submission submission = submissionService.create(request);
		assertThat(submission).isNotNull();
		assertThat(submission.getSpeaker()).isSameAs(existing);

		assertThat(this.submissionService.getSpeakerRepository()
				.findByGithub("fbar")).isSameAs(existing);
	}

}
