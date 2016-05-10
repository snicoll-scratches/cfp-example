package com.example.cfp.submission;

import com.example.cfp.domain.User;
import com.example.cfp.domain.UserRepository;
import com.example.cfp.domain.Submission;
import com.example.cfp.domain.SubmissionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubmissionService {

	private final UserRepository userRepository;
	private final SubmissionRepository submissionRepository;

	public SubmissionService(UserRepository userRepository, SubmissionRepository submissionRepository) {
		this.userRepository = userRepository;
		this.submissionRepository = submissionRepository;
	}

	@Transactional
	public Submission create(SubmissionRequest request) {
		User user = initSpeaker(request);
		Submission submission = new Submission();
		submission.setSpeaker(user);
		submission.setTitle(request.getTitle());
		submission.setSummary(request.getSummary());
		submission.setNotes(request.getNotes());
		submission.setTrack(request.getTrack());
		return this.submissionRepository.save(submission);
	}

	protected UserRepository getUserRepository() {
		return this.userRepository;
	}

	protected SubmissionRepository getSubmissionRepository() {
		return this.submissionRepository;
	}

	private User initSpeaker(SubmissionRequest request) {
		User user = this.userRepository.findByGithub(request.getGithubId());
		if (user == null) {
			user = new User();
			user.setGithub(request.getGithubId());
			user.setName(request.getName());
			user = this.userRepository.save(user);
		}
		return user;
	}

}
