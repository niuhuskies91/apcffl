package org.apcffl.api.persistence.repository;

import org.apcffl.api.persistence.model.UserGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserGroupRepository extends JpaRepository<UserGroupModel, Long> {

	@Query("select p from UserGroupModel p where p.userGroupName = :userGroupName")
	UserGroupModel findByUserGroupName(@Param("userGroupName") String userGroupName);

}
