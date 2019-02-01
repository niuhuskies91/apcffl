package org.apcffl.api.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apcffl.api.persistence.model.ConfigModel;
import org.apcffl.api.persistence.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiServiceConfig {
	
	// properties
	
	public static final String SECURITY_TOKEN_EXP       = "security.token.expiration";
	public static final String PASSWORD_RESET_TOKEN_EXP = "security.password.token.expiration";
	
	@Autowired
	private ConfigRepository repository;
	
	// config map
	private static Map<String, ConfigModel> configMap;
	
	@PostConstruct
	public void init() {
		reloadMap();
	}
	
	public void reloadMap() {
		List<ConfigModel> configs = repository.findAll();
		configMap = new HashMap<>();
		if (!configs.isEmpty()) {
			for (ConfigModel config : configs) {
				configMap.put(config.getConfigKey(), config);
			}
		}
	}
	
	public ConfigModel retrieveProperty(String key) {
		return configMap.get(key);
	}
	
	public Map<String, ConfigModel> retrieveAll() {
		return configMap;
	}
	
	public void updateProperty(String key, String value, String desc) {
		ConfigModel model = new ConfigModel();
		model.setConfigKey(key);
		model.setConfigValue(value);
		model.setConfigDesc(desc);
		
		repository.save(model);
	}
}
