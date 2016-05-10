package com.example.cfp.security;

import com.example.cfp.domain.User;
import com.example.cfp.domain.UserRepository;
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

	private UserRepository userRepository;

	private GithubClient githubClient;

	private GithubAuthenticationEventListener eventListener;

	@Before
	public void configure() {
		this.userRepository = mock(UserRepository.class);
		this.githubClient = mock(GithubClient.class);
		this.eventListener = new GithubAuthenticationEventListener(
				this.userRepository, this.githubClient);
	}

	@Test
	public void existingUserOnLogin() {
		User user = new User("foo", "Foo Bar");
		given(this.userRepository.findByGithub("foo")).willReturn(user);

		this.eventListener.onSuccessfulLogin(new InteractiveAuthenticationSuccessEvent(
				new TestingAuthenticationToken("foo", null), this.getClass()));
		verify(this.userRepository).findByGithub("foo");
		verifyZeroInteractions(this.githubClient);
	}

	@Test
	public void createUserOnLogin() {
		given(this.userRepository.findByGithub("foo")).willReturn(null);
		GithubUser githubUser = new GithubUser();
		githubUser.setName("Foo Bar");
		given(this.githubClient.getUser("foo")).willReturn(githubUser);

		this.eventListener.onSuccessfulLogin(new InteractiveAuthenticationSuccessEvent(
				new TestingAuthenticationToken("foo", null), this.getClass()));
		verify(this.githubClient).getUser("foo");
		verify(this.userRepository).save(argThat(new UserMatcher("foo", "Foo Bar")));
	}

	private static class UserMatcher extends ArgumentMatcher<User> {

		private final String github;

		private final String name;

		UserMatcher(String github, String name) {
			this.github = github;
			this.name = name;
		}

		@Override
		public boolean matches(Object argument) {
			if (!(argument instanceof User)) {
				return false;
			}
			User user = (User) argument;
			return this.github.equals(user.getGithub())
					&& this.name.equals(user.getName());
		}
	}

}
