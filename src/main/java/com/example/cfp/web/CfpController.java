package com.example.cfp.web;

import com.example.cfp.submission.SubmissionRequest;
import com.example.cfp.submission.SubmissionService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cfp")
public class CfpController {

	private final SubmissionService submissionService;

	public CfpController(SubmissionService submissionService) {
		this.submissionService = submissionService;
	}

	@RequestMapping(path = "/submissions", method = RequestMethod.POST)
	public void submit(@RequestBody SubmissionRequest request) {
		this.submissionService.create(request);
	}

}
