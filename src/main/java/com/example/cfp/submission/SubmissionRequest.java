package com.example.cfp.submission;

public class SubmissionRequest {

	private String githubId;

	private String firstName;

	private String lastName;

	private String title;

	private String summary;

	private String notes;

	public String getGithubId() {
		return githubId;
	}

	public void setGithubId(String githubId) {
		this.githubId = githubId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public void setSpeaker(String githubId, String firstName, String lastName) {
		setGithubId(githubId);
		setFirstName(firstName);
		setLastName(lastName);
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

		if (githubId != null ? !githubId.equals(that.githubId) : that.githubId != null) return false;
		if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
		if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;
		if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
		return notes != null ? notes.equals(that.notes) : that.notes == null;

	}

	@Override
	public int hashCode() {
		int result = githubId != null ? githubId.hashCode() : 0;
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (summary != null ? summary.hashCode() : 0);
		result = 31 * result + (notes != null ? notes.hashCode() : 0);
		return result;
	}

}
