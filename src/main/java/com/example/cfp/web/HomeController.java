package com.example.cfp.web;

import com.example.cfp.integration.github.Commit;
import com.example.cfp.integration.github.GithubClient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Navigation(Section.HOME)
public class HomeController {

	private final GithubClient githubClient;

	public HomeController(GithubClient githubClient) {
		this.githubClient = githubClient;
	}

	@RequestMapping("/")
	public String home(Model model) {
		Commit latestFrameworkCommit = githubClient
				.getRecentCommits("spring-projects", "spring-framework")
				.stream().findFirst().get();
		Commit latestBootCommit = githubClient
				.getRecentCommits("spring-projects", "spring-boot")
				.stream().findFirst().get();
		model.addAttribute("latestFrameworkCommit", latestFrameworkCommit);
		model.addAttribute("latestBootCommit", latestBootCommit);
		return "index";
	}
}
