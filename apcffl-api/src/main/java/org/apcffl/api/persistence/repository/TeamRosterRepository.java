package org.apcffl.api.persistence.repository;

import java.util.List;

import org.apcffl.api.persistence.model.TeamRosterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRosterRepository extends JpaRepository<TeamRosterModel, Long> {
	
	@Query("select r from TeamRosterModel r where r.teamModel.teamName = :teamName and r.teamModel.ownerModel.userModel.userName = :userName")
	List<TeamRosterModel> findByUserNameAndTeamName(@Param("userName")String userName, @Param("teamName") String teamName);
}
