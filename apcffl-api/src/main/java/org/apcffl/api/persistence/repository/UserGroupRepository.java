package org.apcffl.api.persistence.repository;

import org.apcffl.api.persistence.model.UserGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroupModel, Long> {

}
