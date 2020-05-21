package org.apcffl.api.league.dto;

import java.util.List;

public class Team {

	private String userName;
	private Boolean activeFlag;
	private String leagueName;
	private String teamName;
	private String divisionName;
	private List<TeamRoster> roster;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public List<TeamRoster> getRoster() {
		return roster;
	}
	public void setRoster(List<TeamRoster> roster) {
		this.roster = roster;
	}
	
}
