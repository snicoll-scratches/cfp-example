package com.example.cfp.web;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.example.cfp.integration.github.Commit;
import com.example.cfp.integration.github.GithubClient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Navigation(Section.NEWS)
public class NewsController {

	private final GithubClient githubClient;

	public NewsController(GithubClient githubClient) {
		this.githubClient = githubClient;
	}

	@RequestMapping("/news")
	public String home(Model model) {
		model.addAttribute("latestFrameworkCommits",
				getRecentCommits("spring-projects", "spring-framework"));
		model.addAttribute("latestBootCommits",
				getRecentCommits("spring-projects", "spring-boot"));
		model.addAttribute("daysSinceLastPolish", daysSinceLastPolishCommit());
		return "news";
	}

	private List<Commit> getRecentCommits(String organization, String project) {
		return this.githubClient
				.getRecentCommits(organization, project)
				.stream().limit(5).collect(Collectors.toList());
	}

	private long daysSinceLastPolishCommit() {
		Commit polishCommit = this.githubClient.getRecentPolishCommit("spring-projects", "spring-framework");
		Duration duration = Duration.between(polishCommit.getDate(), Instant.now());
		return duration.toDays();
	}
}
