package com.example.boot.diagnostic;

import com.example.boot.autoconfigure.InvalidGithubCounterException;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class InvalidGithubCounterFailureAnalyzer extends AbstractFailureAnalyzer<InvalidGithubCounterException> {

	@Override
	protected FailureAnalysis analyze(Throwable throwable, InvalidGithubCounterException cause) {
		// Some very smart analysis of the cause here
		return new FailureAnalysis("It looks like you dared to use 42 as a value. Seriously?",
				"Please reflect on this choice and chose a more sensible value.", cause);
	}

}
