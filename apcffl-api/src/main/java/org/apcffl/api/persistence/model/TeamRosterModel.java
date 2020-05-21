package org.apcffl.api.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "TEAM_ROSTER")
@IdClass(TeamRosterId.class)
@EntityListeners(AuditingEntityListener.class)
public class TeamRosterModel implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -8021115498207682135L;

	@Id
	private TeamModel teamModel;

	@Id
	private SchoolModel schoolModel;
	
	@Column(name = "SCHOLARSHIP_POINTS")
	private Integer scholarshipPoints;

	public TeamModel getTeamModel() {
		return teamModel;
	}

	public void setTeamModel(TeamModel teamModel) {
		this.teamModel = teamModel;
	}

	public SchoolModel getSchoolModel() {
		return schoolModel;
	}

	public void setSchoolModel(SchoolModel schoolModel) {
		this.schoolModel = schoolModel;
	}

	public Integer getScholarshipPoints() {
		return scholarshipPoints;
	}

	public void setScholarshipPoints(Integer scholarshipPoints) {
		this.scholarshipPoints = scholarshipPoints;
	}

}
