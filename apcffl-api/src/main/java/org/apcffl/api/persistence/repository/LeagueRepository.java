package org.apcffl.api.persistence.repository;

import java.util.Set;

import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.LeagueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<LeagueModel, Long> {

	@Query("select l from LeagueModel l where l.leagueName = :leagueName")
	LeagueModel findByLeagueName(@Param("leagueName") String leagueName);
	
	@Query("select l.divisions from LeagueModel l where l.leagueName = :leagueName")
	Set<DivisionModel> findDivisionsForLeague(@Param("leagueName") String leagueName);
}
