package com.example.boot.autoconfigure;

public class InvalidGithubCounterException extends RuntimeException {

	private final int counter;

	public InvalidGithubCounterException(int counter) {
		super(String.format("Invalid github counter %d", counter));
		this.counter = counter;
	}

	public int getCounter() {
		return this.counter;
	}

}
