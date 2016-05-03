package com.example.cfp.security;

import com.example.cfp.domain.Speaker;
import com.example.cfp.domain.SpeakerRepository;
import com.example.cfp.integration.github.GithubClient;
import com.example.cfp.integration.github.GithubUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GithubAuthenticationEventListenerTest {

	private SpeakerRepository speakerRepository;

	private GithubClient githubClient;

	private GithubAuthenticationEventListener eventListener;

	@Before
	public void configure() {
		this.speakerRepository = mock(SpeakerRepository.class);
		this.githubClient = mock(GithubClient.class);
		this.eventListener = new GithubAuthenticationEventListener(
				this.speakerRepository, this.githubClient);
	}

	@Test
	public void existingSpeakerOnLogin() {
		Speaker speaker = new Speaker("foo", "Foo Bar");
		given(this.speakerRepository.findByGithub("foo")).willReturn(speaker);

		this.eventListener.onSuccessfulLogin(new InteractiveAuthenticationSuccessEvent(
				new TestingAuthenticationToken("foo", null), this.getClass()));
		verify(this.speakerRepository).findByGithub("foo");
		verifyZeroInteractions(this.githubClient);
	}

	@Test
	public void createSpeakerOnLogin() {
		given(this.speakerRepository.findByGithub("foo")).willReturn(null);
		GithubUser user = new GithubUser();
		user.setName("Foo Bar");
		given(this.githubClient.getUser("foo")).willReturn(user);

		this.eventListener.onSuccessfulLogin(new InteractiveAuthenticationSuccessEvent(
				new TestingAuthenticationToken("foo", null), this.getClass()));
		verify(this.githubClient).getUser("foo");
		verify(this.speakerRepository).save(argThat(new SpeakerMatcher("foo", "Foo Bar")));
	}

	private static class SpeakerMatcher extends ArgumentMatcher<Speaker> {

		private final String github;

		private final String name;

		SpeakerMatcher(String github, String name) {
			this.github = github;
			this.name = name;
		}

		@Override
		public boolean matches(Object argument) {
			if (!(argument instanceof Speaker)) {
				return false;
			}
			Speaker speaker = (Speaker) argument;
			return this.github.equals(speaker.getGithub())
					&& this.name.equals(speaker.getName());
		}
	}

}
