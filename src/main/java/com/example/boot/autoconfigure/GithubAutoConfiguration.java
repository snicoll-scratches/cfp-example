package com.example.boot.autoconfigure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.example.cfp.integration.github.GithubClient;

import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnClass(GithubClient.class)
@ConditionalOnBean(CounterService.class)
@EnableConfigurationProperties(GithubProperties.class)
@AutoConfigureAfter(MetricRepositoryAutoConfiguration.class)
public class GithubAutoConfiguration {

	private final GithubProperties properties;

	public GithubAutoConfiguration(GithubProperties properties) {
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public GithubClient githubClient(CounterService counterService, RestTemplateBuilder restTemplateBuilder) {
		return new GithubClient(counterService, restTemplateBuilder.additionalCustomizers(rt -> {
			GithubAppTokenInterceptor requestInterceptor = new GithubAppTokenInterceptor(this.properties.getToken());
			rt.getInterceptors().add(requestInterceptor);
		}));
	}


	private static class GithubAppTokenInterceptor implements ClientHttpRequestInterceptor {

		private final String token;

		GithubAppTokenInterceptor(String token) {
			this.token = token;
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
				ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
			if (StringUtils.hasText(this.token)) {
				byte[] basicAuthValue = this.token.getBytes(StandardCharsets.UTF_8);
				httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION,
						"Basic " + Base64Utils.encodeToString(basicAuthValue));
			}
			return clientHttpRequestExecution.execute(httpRequest, bytes);
		}

	}

}
