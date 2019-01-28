package org.apcffl.api.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="apcffl.api")
public class ApiServiceConfig {

	@NotNull private String securityTokenExpiration;
	@NotNull private String url;

	protected String getSecurityTokenExpiration() {
		return securityTokenExpiration;
	}

	protected void setSecurityTokenExpiration(String securityTokenExpiration) {
		this.securityTokenExpiration = securityTokenExpiration;
		System.out.println("");
	}

	protected String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}
	
}
