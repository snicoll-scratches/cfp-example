package com.example.cfp.integration.github;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CommitDeserializer.class)
@SuppressWarnings("serial")
public class Commit implements Serializable {

	private final String sha;

	private final String message;

	private final Committer committer;

	public Commit(String sha, String message, Committer committer) {
		this.sha = sha;
		this.message = message;
		this.committer = committer;
	}

	public String getSha() {
		return this.sha;
	}

	public String getMessage() {
		return this.message;
	}

	public Committer getCommitter() {
		return this.committer;
	}

	public static class Committer implements Serializable {

		private final String id;

		private final String name;

		private final String avatarUrl;

		public Committer(String id, String name, String avatarUrl) {
			this.id = id;
			this.name = name;
			this.avatarUrl = avatarUrl;
		}

		public String getId() {
			return this.id;
		}

		public String getName() {
			return this.name;
		}

		public String getAvatarUrl() {
			return this.avatarUrl;
		}
	}

}
