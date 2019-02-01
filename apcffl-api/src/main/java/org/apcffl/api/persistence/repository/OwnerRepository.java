package org.apcffl.api.persistence.repository;

import org.apcffl.api.persistence.model.OwnerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OwnerRepository extends JpaRepository<OwnerModel, Long> {

	@Query("select o from OwnerModel o INNER JOIN o.userModel u where u.userName = :userName")
	OwnerModel findByUserName(@Param("userName") String userName);
}
