package com.example.cfp.submission;

import com.example.cfp.domain.Submission;
import com.example.cfp.domain.SubmissionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubmissionService {

	private final SubmissionRepository submissionRepository;

	public SubmissionService(SubmissionRepository submissionRepository) {
		this.submissionRepository = submissionRepository;
	}

	@Transactional
	public Submission create(SubmissionRequest request) {
		Submission submission = new Submission();
		submission.setSpeakerEmail(request.getEmail());
		submission.setSpeakerName(request.getName());
		submission.setTitle(request.getTitle());
		submission.setSummary(request.getSummary());
		submission.setNotes(request.getNotes());
		submission.setTrack(request.getTrack());
		return this.submissionRepository.save(submission);
	}

	protected SubmissionRepository getSubmissionRepository() {
		return this.submissionRepository;
	}

}
