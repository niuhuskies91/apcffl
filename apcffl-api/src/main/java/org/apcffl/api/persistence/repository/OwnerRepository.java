package org.apcffl.api.persistence.repository;

import org.apcffl.api.persistence.model.OwnerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerModel, Long> {

	@Query("select o from OwnerModel o INNER JOIN o.userModel u where u.userName = :userName")
	OwnerModel findByUserName(@Param("userName") String userName);

	@Query("select o from OwnerModel o where o.email1 = :email")
	OwnerModel findByEmail(@Param("email") String email);
}
