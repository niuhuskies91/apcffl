package org.apcffl.api.persistence.model;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class TeamRosterId implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = -2890988077277399077L;

	@OneToOne
	@JoinColumn(name = "TEAM_ID")
	private TeamModel teamModel;

	@OneToOne
	@JoinColumn(name = "SCHOOL_ID")
	private SchoolModel schoolModel;
	
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
}
