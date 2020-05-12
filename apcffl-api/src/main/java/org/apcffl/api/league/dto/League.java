package org.apcffl.api.league.dto;

import java.util.List;

public class League {

	private String leagueName;
	private Integer numTeams;
	private Integer numDivisions;
	private List<Division> divisions;
	
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public Integer getNumTeams() {
		return numTeams;
	}
	public void setNumTeams(Integer numTeams) {
		this.numTeams = numTeams;
	}
	public Integer getNumDivisions() {
		return numDivisions;
	}
	public void setNumDivisions(Integer numDivisions) {
		this.numDivisions = numDivisions;
	}
	public List<Division> getDivisions() {
		return divisions;
	}
	public void setDivisions(List<Division> divisions) {
		this.divisions = divisions;
	}
	
}
