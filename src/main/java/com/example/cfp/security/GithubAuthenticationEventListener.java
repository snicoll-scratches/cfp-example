package com.example.cfp.security;

import com.example.cfp.domain.User;
import com.example.cfp.domain.UserRepository;
import com.example.cfp.integration.github.GithubClient;
import com.example.cfp.integration.github.GithubUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
class GithubAuthenticationEventListener {

	private static final Logger logger = LoggerFactory.getLogger(GithubAuthenticationEventListener.class);

	private final UserRepository userRepository;

	private final GithubClient githubClient;

	public GithubAuthenticationEventListener(UserRepository userRepository, GithubClient githubClient) {
		this.userRepository = userRepository;
		this.githubClient = githubClient;
	}

	@EventListener
	public void onSuccessfulLogin(InteractiveAuthenticationSuccessEvent event) {
		String githubLogin = (String) event.getAuthentication().getPrincipal();
		if (this.userRepository.findByGithub(githubLogin) == null) {
			logger.info("Initialize user with githubId {}", githubLogin);
			GithubUser user = this.githubClient.getUser(githubLogin);
			User speaker = new User(githubLogin, user.getName());
			speaker.setAvatarUrl(user.getAvatar());
			this.userRepository.save(speaker);
		}
	}

}
