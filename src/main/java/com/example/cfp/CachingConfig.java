package com.example.cfp;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
class CachingConfig {

	@Bean
	public JCacheManagerCustomizer cacheManagerCustomizer() {
		return c -> {
			c.createCache("github.commits", createConfiguration(Duration.ONE_MINUTE));
			c.createCache("github.polishCommit", createConfiguration(Duration.FIVE_MINUTES));
			c.createCache("github.users", createConfiguration(Duration.ONE_HOUR));
		};
	}

	private MutableConfiguration<Object, Object> createConfiguration(Duration expiration) {
		return new MutableConfiguration<>()
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(expiration));
	}

}
