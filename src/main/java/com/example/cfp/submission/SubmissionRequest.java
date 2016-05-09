package com.example.cfp.submission;

import com.example.cfp.domain.Track;

public class SubmissionRequest {

	private String githubId;

	private String name;

	private String title;

	private String summary;

	private String notes;

	private Track track;

	public String getGithubId() {
		return githubId;
	}

	public void setGithubId(String githubId) {
		this.githubId = githubId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public void setSpeaker(String githubId, String name) {
		setGithubId(githubId);
		setName(name);
	}

	public void setTalk(String title, String summary, String notes) {
		setTitle(title);
		setSummary(summary);
		setNotes(notes);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SubmissionRequest that = (SubmissionRequest) o;

		if (this.githubId != null ? !this.githubId.equals(that.githubId) : that.githubId != null) return false;
		if (this.name != null ? !this.name.equals(that.name) : that.name != null) return false;
		if (this.title != null ? !this.title.equals(that.title) : that.title != null) return false;
		if (this.summary != null ? !this.summary.equals(that.summary) : that.summary != null) return false;
		return this.notes != null ? this.notes.equals(that.notes) : that.notes == null;

	}

	@Override
	public int hashCode() {
		int result = this.githubId != null ? this.githubId.hashCode() : 0;
		result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
		result = 31 * result + (this.title != null ? this.title.hashCode() : 0);
		result = 31 * result + (this.summary != null ? this.summary.hashCode() : 0);
		result = 31 * result + (this.notes != null ? this.notes.hashCode() : 0);
		return result;
	}

}
