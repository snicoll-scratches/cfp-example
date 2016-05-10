package com.example.cfp.web;

import java.security.Principal;

import com.example.cfp.CfpProperties;
import com.example.cfp.domain.User;
import com.example.cfp.domain.UserRepository;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityControllerAdvice {

	private final CfpProperties cfpProperties;

	private final UserRepository userRepository;

	public SecurityControllerAdvice(CfpProperties cfpProperties, UserRepository userRepository) {
		this.cfpProperties = cfpProperties;
		this.userRepository = userRepository;
	}

	@ModelAttribute("currentUser")
	public User currentUser(@AuthenticationPrincipal Principal principal) {
		if (principal != null) {
			return this.userRepository.findByGithub(principal.getName());
		}
		else {
			return null;
		}
	}

}
