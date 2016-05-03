package com.example.cfp.domain;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(FlywayAutoConfiguration.class)
public class SpeakerTest {

	@Autowired
	private SpeakerRepository speakerRepository;

	@Test
	public void findByGithub() {
		Speaker speaker = new Speaker("bclozel", "Brian", "Clozel");
		this.speakerRepository.save(speaker);

		Speaker brian = speakerRepository.findByGithub("bclozel");
		assertThat(brian).isNotNull();
		assertThat(brian.getFirstName()).isEqualTo("Brian");
		assertThat(brian.getLastName()).isEqualTo("Clozel");
	}

	@Test
	public void findByTwitter() {
		Speaker speaker = new Speaker("jhoeller", "Jürgen", "Höller");
		speaker.setTwitter("springjuergen");
		this.speakerRepository.save(speaker);

		Speaker juergen = speakerRepository.findByTwitter("springjuergen");
		assertThat(juergen).isNotNull();
		assertThat(juergen.getFirstName()).isEqualTo("Jürgen");
		assertThat(juergen.getLastName()).isEqualTo("Höller");
	}

}
