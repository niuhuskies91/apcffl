package org.apcffl.api.persistence.repository;

import org.apcffl.api.persistence.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserModel, Long> {

	@Query("select p from UserModel p where p.userName = :userName and p.password = :password")
	UserModel findByUserNamePassword(@Param("userName") String userName, @Param("password") String password);
}
