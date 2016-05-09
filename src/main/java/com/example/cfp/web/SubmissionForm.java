package com.example.cfp.web;

import javax.validation.constraints.NotNull;

import com.example.cfp.domain.Track;
import org.hibernate.validator.constraints.NotEmpty;

public class SubmissionForm {

	@NotEmpty
	private String title;

	@NotEmpty
	private String summary;

	private String notes;

	@NotNull
	private Track track;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}
}
