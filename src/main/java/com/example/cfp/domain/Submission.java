package com.example.cfp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
@SuppressWarnings("serial")
public class Submission implements Serializable {

	@GeneratedValue
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "speaker_id")
	private Speaker speaker;

	@Enumerated
	private Track track;

	@Column
	private String title;

	@Column
	private SubmissionStatus status;

	@Column
	@Lob
	private String summary;

	@Column
	@Lob
	private String notes;

	public Long getId() {
		return id;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SubmissionStatus getStatus() {
		return status;
	}

	public void setStatus(SubmissionStatus status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "Submission{" + "id=" + id + ", title='" + title + '\'' + ", status='" + status + '\'' + '}';
	}

}
