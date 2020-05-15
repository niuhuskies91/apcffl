package org.apcffl.api.persistence.repository;

import java.util.List;

import org.apcffl.api.persistence.model.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamModel, Long>{

	@Query("select t from TeamModel t where t.leagueModel.leagueName = :leagueName")
	List<TeamModel> findByLeagueName(@Param("leagueName") String leagueName);
}
