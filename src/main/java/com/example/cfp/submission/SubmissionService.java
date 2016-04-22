package com.example.cfp.submission;

import com.example.cfp.domain.Speaker;
import com.example.cfp.domain.SpeakerRepository;
import com.example.cfp.domain.Submission;
import com.example.cfp.domain.SubmissionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubmissionService {

	private final SpeakerRepository speakerRepository;
	private final SubmissionRepository submissionRepository;

	public SubmissionService(SpeakerRepository speakerRepository, SubmissionRepository submissionRepository) {
		this.speakerRepository = speakerRepository;
		this.submissionRepository = submissionRepository;
	}

	public SpeakerRepository getSpeakerRepository() {
		return speakerRepository;
	}

	public SubmissionRepository getSubmissionRepository() {
		return submissionRepository;
	}

	@Transactional
	public Submission create(SubmissionRequest request) {
		Speaker speaker = initSpeaker(request);
		Submission submission = new Submission();
		submission.setSpeaker(speaker);
		submission.setTitle(request.getTitle());
		submission.setSummary(request.getSummary());
		submission.setNotes(request.getNotes());
		return this.submissionRepository.save(submission);
	}

	private Speaker initSpeaker(SubmissionRequest request) {
		Speaker speaker = this.speakerRepository.findByGithub(request.getGithubId());
		if (speaker == null) {
			speaker = new Speaker();
			speaker.setGithub(request.getGithubId());
			speaker.setFirstName(request.getFirstName());
			speaker.setLastName(request.getLastName());
			speaker = this.speakerRepository.save(speaker);
		}
		return speaker;
	}

}
