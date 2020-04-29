package org.apcffl.api.persistence.repository;

import org.apcffl.api.persistence.model.LeagueModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<LeagueModel, Long> {

}
