package org.apcffl.api.persistence.repository;

import org.apcffl.api.persistence.model.GroupActivityMapModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupActivityMapRepository extends JpaRepository<GroupActivityMapModel, Long> {

}
