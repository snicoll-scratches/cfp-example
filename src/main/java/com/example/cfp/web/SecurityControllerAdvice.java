package com.example.cfp.web;

import java.security.Principal;

import com.example.cfp.CfpProperties;
import com.example.cfp.domain.Speaker;
import com.example.cfp.domain.SpeakerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityControllerAdvice {

	private final CfpProperties cfpProperties;

	private final SpeakerRepository speakerRepository;

	@Autowired
	public SecurityControllerAdvice(CfpProperties cfpProperties, SpeakerRepository speakerRepository) {
		this.cfpProperties = cfpProperties;
		this.speakerRepository = speakerRepository;
	}

	@ModelAttribute("currentUser")
	public Speaker currentUser(@AuthenticationPrincipal Principal principal) {
		if(principal != null) {
			return this.speakerRepository.findByGithub(principal.getName());
		}
		else {
			return null;
		}
	}

	@ModelAttribute("isAdmin")
	public boolean isAdmin(Principal principal) {
		return principal != null
				&& this.cfpProperties.getSecurity().getAdmins().contains(principal.getName());
	}
}
