package com.example.cfp.web;

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
		List<Commit> latestFrameworkCommits = githubClient
				.getRecentCommits("spring-projects", "spring-framework")
				.stream().limit(5).collect(Collectors.toList());
		List<Commit> latestBootCommits = githubClient
				.getRecentCommits("spring-projects", "spring-boot")
				.stream().limit(5).collect(Collectors.toList());
		model.addAttribute("latestFrameworkCommits", latestFrameworkCommits);
		model.addAttribute("latestBootCommits", latestBootCommits);
		return "news";
	}
}
