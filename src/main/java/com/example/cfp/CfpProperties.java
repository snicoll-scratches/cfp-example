package com.example.cfp;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cfp")
public class CfpProperties {

	private final Security security = new Security();

	public Security getSecurity() {
		return security;
	}


	public static class Security {

		/**
		 * Github users that have admin rights.
		 */
		private List<String> admins = Arrays.asList("snicoll", "bclozel");

		public List<String> getAdmins() {
			return admins;
		}

		public void setAdmins(List<String> admins) {
			this.admins = admins;
		}

	}

}
