package com.example.cfp.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

@Entity
@SuppressWarnings("serial")
public class Speaker implements Serializable {

	@GeneratedValue
	@Id
	private Long id;

	private String name;

	private String twitter;

	private String github;

	@Column
	@Lob
	private String bio;

	@OneToMany(mappedBy = "speaker", cascade = CascadeType.ALL)
	private Set<Submission> submissions;

	public Speaker() {
	}

	public Speaker(String github, String name) {
		this.github = github;
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTwitter() {
		return this.twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getGithub() {
		return this.github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getBio() {
		return this.bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public Set<Submission> getSubmissions() {
		return this.submissions;
	}

	public void setSubmissions(Set<Submission> submissions) {
		this.submissions = submissions;
	}

	@Override
	public String toString() {
		return "Speaker{" + "id=" + this.id + ", name='" + this.name + '\'' + '}';
	}

}
