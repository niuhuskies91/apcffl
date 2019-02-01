package org.apcffl.api.persistence.repository;

import org.apcffl.api.persistence.model.ConfigModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<ConfigModel, String> {

}
