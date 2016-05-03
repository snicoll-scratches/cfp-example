package com.example.cfp.web;

import java.util.List;

import com.example.cfp.integration.github.Commit;
import com.example.cfp.integration.github.GithubClient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/public/github")
@RestController
public class GithubController {

	private final GithubClient githubClient;

	public GithubController(GithubClient githubClient) {
		this.githubClient = githubClient;
	}

	@RequestMapping("/{organization}/{project}/commits")
	public List<Commit> recentCommits(@PathVariable String organization,
			@PathVariable String project) {
		return this.githubClient.getRecentCommits(organization, project);
	}

	@RequestMapping("/{organization}/{project}/commits/latest")
	public Commit latestCommit(@PathVariable String organization,
			@PathVariable String project) {
		List<Commit> commits = this.githubClient.getRecentCommits(organization, project);
		return (commits.size() > 0 ? commits.get(0): null);
	}

}
