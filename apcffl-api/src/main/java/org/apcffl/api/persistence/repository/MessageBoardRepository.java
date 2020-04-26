package org.apcffl.api.persistence.repository;

import java.sql.Date;
import java.util.List;

import org.apcffl.api.persistence.model.MessageBoardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageBoardRepository extends JpaRepository<MessageBoardModel, Long> {

	@Query("select o from MessageBoardModel o where o.leagueModel.leagueName = :leagueName and o.createDate >= :startDate and o.createDate <= :endDate and o.archiveDate is null order by o.createDate desc")
	List<MessageBoardModel> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("leagueName") String leagueName);
}
