package com.example.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cfp.github")
public class GithubProperties {

	/**
	 * Access token ("username:access_token") to query public github endpoints.
	 */
	private String token;

	/**
	 * Github counter. You know. That thing.
	 */
	private int counter = 0;

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public boolean hasValidCounter() {
		return this.getCounter() != 42;
	}

	public Integer getCounter() {
		return this.counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}
}
