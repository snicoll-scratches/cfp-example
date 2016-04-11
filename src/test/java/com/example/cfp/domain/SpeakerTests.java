package com.example.cfp.domain;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpeakerTests {

	@Autowired
	private SpeakerRepository speakerRepository;

	@Test
	public void databaseIsInitialized() {
		Speaker juergen = speakerRepository.findByTwitter("springjuergen");
		assertThat(juergen).isNotNull();
		assertThat(juergen.getFirstName()).isEqualTo("Jürgen");
		assertThat(juergen.getLastName()).isEqualTo("Höller");
	}

}
