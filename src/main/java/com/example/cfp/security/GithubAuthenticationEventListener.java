package com.example.cfp.security;

import com.example.cfp.domain.Speaker;
import com.example.cfp.domain.SpeakerRepository;
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

	private final SpeakerRepository speakerRepository;

	private final GithubClient githubClient;

	public GithubAuthenticationEventListener(SpeakerRepository speakerRepository, GithubClient githubClient) {
		this.speakerRepository = speakerRepository;
		this.githubClient = githubClient;
	}

	@EventListener
	public void onSuccessfulLogin(InteractiveAuthenticationSuccessEvent event) {
		String githubLogin = (String) event.getAuthentication().getPrincipal();
		if (this.speakerRepository.findByGithub(githubLogin) == null) {
			logger.info("Creating initial speaker entry for {}", githubLogin);
			GithubUser user = this.githubClient.getUser(githubLogin);
			Speaker speaker = new Speaker(githubLogin, user.getName());
			this.speakerRepository.save(speaker);
		}
	}

}
