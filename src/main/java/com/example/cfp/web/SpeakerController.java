package com.example.cfp.web;

import java.util.List;

import com.example.cfp.integration.github.GithubClient;
import com.example.cfp.integration.github.GithubUser;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeakerController {

	private final GithubClient githubClient;

	public SpeakerController(GithubClient githubClient) {
		this.githubClient = githubClient;
	}

	@RequestMapping("/api/speakers/invited")
	public List<GithubUser> getInvitedSpeakers() {
		return this.githubClient.getInvitedSpeakers();
	}

}
