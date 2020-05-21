package org.apcffl.api.persistence.repository;

import java.util.List;

import org.apcffl.api.persistence.model.ConferenceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<ConferenceModel, Long> {

	@Query("select c from ConferenceModel c where c.ncaaDivisionType = :ncaaDivisionType order by c.conferenceName")
	List<ConferenceModel> findByConferenceType(@Param("ncaaDivisionType") String ncaaDivisionType);
}
