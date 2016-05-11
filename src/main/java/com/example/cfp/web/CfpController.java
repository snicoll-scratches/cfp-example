package com.example.cfp.web;

import javax.validation.Valid;

import com.example.cfp.domain.Track;
import com.example.cfp.submission.SubmissionRequest;
import com.example.cfp.submission.SubmissionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Navigation(Section.SUBMIT)
public class CfpController {

	private final SubmissionService submissionService;

	public CfpController(SubmissionService submissionService) {
		this.submissionService = submissionService;
	}

	@RequestMapping(path = "/submit", method = RequestMethod.GET)
	public String submitForm(Model model) {
		model.addAttribute("tracks", Track.values());
		model.addAttribute("submissionForm", new SubmissionForm());
		return "submit";
	}

	@RequestMapping(path = "/submit", method = RequestMethod.POST)
	public String submit(@Valid SubmissionForm submissionForm, BindingResult bindingResult,
			RedirectAttributes attributes, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("tracks", Track.values());
			model.addAttribute("submissionForm", submissionForm);
			return "submit";
		}
		else {
			this.submissionService.create(createRequest(submissionForm));
			attributes.addFlashAttribute("successMessage", "Thanks! Your talk proposal has been submitted.");
			return "redirect:/submit";
		}
	}

	private SubmissionRequest createRequest(SubmissionForm form) {
		SubmissionRequest request = new SubmissionRequest();
		request.setSpeaker(form.getEmail(), form.getName());
		request.setTitle(form.getTitle());
		request.setSummary(form.getSummary());
		request.setNotes(form.getNotes());
		request.setTrack(form.getTrack());
		return request;
	}

}
