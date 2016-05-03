package com.example.cfp.security;

import com.example.cfp.CfpProperties;
import com.example.cfp.domain.Speaker;
import com.example.cfp.domain.SpeakerRepository;
import com.example.cfp.integration.github.GithubClient;
import com.example.cfp.integration.github.GithubUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Configuration
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CfpProperties cfpProperties;

	public SecurityConfig(CfpProperties cfpProperties) {
		this.cfpProperties = cfpProperties;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**")
				.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/", "/login**", "/webjars/**", "/public/**").permitAll()
				.anyRequest().authenticated()
				.and().logout().logoutSuccessUrl("/").permitAll();
	}

	@Bean
	public AuthoritiesExtractor authoritiesExtractor() {
		return map -> {
			String username = (String) map.get("login");
			if (this.cfpProperties.getSecurity().getAdmins().contains(username)) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
			}
			else {
				return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
			}
		};
	}

}
