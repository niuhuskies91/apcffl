package org.apcffl.api.config;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
public class GeneralPropertiesConfig {
	
	@Value("${security.token-expiration}")
	@NotNull private Long securityTokenExp;
	
	@Value("${security.password-reset-token-expiration}")
	@NotNull private Long securityPassResetTokenExp;

	public Long getSecurityTokenExp() {
		return securityTokenExp;
	}

	public void setSecurityTokenExp(Long securityTokenExp) {
		this.securityTokenExp = securityTokenExp;
	}

	public Long getSecurityPassResetTokenExp() {
		return securityPassResetTokenExp;
	}

	public void setSecurityPassResetTokenExp(Long securityPassResetTokenExp) {
		this.securityPassResetTokenExp = securityPassResetTokenExp;
	}

}
