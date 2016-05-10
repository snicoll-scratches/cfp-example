package com.example.cfp.web;

import javax.validation.constraints.NotNull;

import com.example.cfp.domain.Track;
import org.hibernate.validator.constraints.NotEmpty;

public class SubmissionForm {

	@NotEmpty
	private String email;

	@NotEmpty
	private String name;

	@NotEmpty
	private String title;

	@NotEmpty
	private String summary;

	private String notes;

	@NotNull
	private Track track;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Track getTrack() {
		return this.track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}
}
