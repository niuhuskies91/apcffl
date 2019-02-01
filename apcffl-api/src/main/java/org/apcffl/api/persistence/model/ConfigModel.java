package org.apcffl.api.persistence.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "CONFIG")
@EntityListeners(AuditingEntityListener.class)
public class ConfigModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -7177485185741555384L;

	@Id
	@Column(name = "CONFIG_KEY")
	private String configKey;
	
	@Column(name = "CONFIG_VALUE")
	private String configValue;
	
	@Column(name = "CONFIG_DESC")
	private String configDesc;

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(configKey, configValue, configDesc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigModel other = (ConfigModel) obj;
		if (configDesc == null) {
			if (other.configDesc != null)
				return false;
		} else if (!configDesc.equals(other.configDesc))
			return false;
		if (configKey == null) {
			if (other.configKey != null)
				return false;
		} else if (!configKey.equals(other.configKey))
			return false;
		if (configValue == null) {
			if (other.configValue != null)
				return false;
		} else if (!configValue.equals(other.configValue))
			return false;
		return true;
	}
	
}
