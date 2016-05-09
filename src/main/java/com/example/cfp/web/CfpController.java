package com.example.cfp.web;

import com.example.cfp.domain.SpeakerRepository;
import com.example.cfp.domain.Track;
import com.example.cfp.submission.SubmissionRequest;
import com.example.cfp.submission.SubmissionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/cfp")
@Navigation(Section.SUBMIT)
public class CfpController {

	private final SubmissionService submissionService;

	private final SpeakerRepository speakerRepository;

	public CfpController(SubmissionService submissionService, SpeakerRepository speakerRepository) {
		this.submissionService = submissionService;
		this.speakerRepository = speakerRepository;
	}


	@RequestMapping("/submit")
	public String submitForm(Model model) {
		model.addAttribute("tracks", Track.values());
		return "submit";
	}

	@ResponseBody
	@RequestMapping(path = "/cfp/submissions", method = RequestMethod.POST)
	public void submit(@RequestBody SubmissionRequest request) {
		this.submissionService.create(request);
	}

}
